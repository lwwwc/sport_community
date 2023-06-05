package com.lwc.sportcommunity.netty;

import com.lwc.sportcommunity.SpringUtil;
import com.lwc.sportcommunity.service.ChatService;
import com.lwc.sportcommunity.service.PersonMessageService;
import com.lwc.sportcommunity.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by LWC on 2023/4/24 14:49
 */

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Map<Integer, ChannelGroup> groups = new ConcurrentHashMap<>();
    //私聊
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        ChannelGroup group = getGroup(0);
        group.add(channel);
        clients.add(channel);
//        System.out.println(channel.id()+" add-default，size:"+group.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        clients.remove(ctx.channel());
//        System.out.println("长id" + ctx.channel().id());
//        System.out.println("短id"+ctx.channel().id().asShortText());
        for (ChannelGroup group : groups.values()){
            group.remove(ctx.channel());
        }
//        System.out.println(ctx.channel().id()+"remove all");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();
//        System.out.println("接受到的数据:" + content);
        Channel currentChannel = ctx.channel();
        //1.获取客户端数据
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();
        if (action.equals(EmMsgAction.CONNECT.getType())){
            ChatMsg chatMsg = dataContent.getChatMsg();
            Integer clubId = chatMsg.getClubId();
            ChannelGroup group = getGroup(clubId);
            group.add(currentChannel);
//            System.out.println(chatMsg.getUserId()+"加入群聊："+chatMsg.getClubId()+"成功; group-size:" +  group.size());
        }else if(action.equals(EmMsgAction.CHAT.getType())){
            ChatMsg chatMsg = dataContent.getChatMsg();
            Integer clubId = chatMsg.getClubId();

            //落库
            ApplicationContext applicationContext = SpringUtil.getApplicationContext();
            ChatService chatService = (ChatService) SpringUtil.getBean("chatServiceImpl");
            Integer chatId = chatService.saveMsg(chatMsg);
//            System.out.println("chatId:"+chatId);
            chatMsg.setMsgId(chatId);

            DataContent datacontentMsg = new DataContent();
            datacontentMsg.setChatMsg(chatMsg);

            //返回签收消息
            ChannelGroup group = getGroup(clubId);
            for (Channel channel : group){
                if (channel == currentChannel){
                    datacontentMsg.setAction(EmMsgAction.SIGN.getType());
                    channel.writeAndFlush(
                            new TextWebSocketFrame(JsonUtils.objectToJson(datacontentMsg)));
                }else{
                    datacontentMsg.setAction(EmMsgAction.CHAT.getType());
                    channel.writeAndFlush(
                            new TextWebSocketFrame(JsonUtils.objectToJson(datacontentMsg)));
                }
            }

        }else if (action.equals(EmMsgAction.KEEPALIVE.getType())){
//            System.out.println("收到来自channel为[" + currentChannel + "]的心跳包...");
        }else if (action.equals(EmMsgAction.PCONNECT.getType())){
            ChatMsg chatMsg = dataContent.getChatMsg();
            Integer myId = chatMsg.getUserId();
            UserChannelRef.put(myId, currentChannel);
//            System.out.println("PConnect");
        }else if (action.equals(EmMsgAction.PCHAT.getType())){
            ChatMsg chatMsg = dataContent.getChatMsg();
            Integer receiverId = chatMsg.getClubId();
            PersonMessageService personMessageService = (PersonMessageService) SpringUtil.getBean("personMessageServiceImpl");
            Integer msgId = personMessageService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);

            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);
            Channel channel = UserChannelRef.get(receiverId);
            //给自己发
//                    System.out.println("sending...");
            dataContentMsg.setAction(EmMsgAction.SIGN.getType());
            currentChannel.writeAndFlush(
                    new TextWebSocketFrame(JsonUtils.objectToJson(dataContentMsg)));
//            System.out.println("send before");
            if (channel != null){
                Channel findChannel = clients.find(channel.id());
                if (findChannel != null){
                    //给对方发
                    dataContentMsg.setAction(EmMsgAction.CHAT.getType());
                    channel.writeAndFlush(
                            new TextWebSocketFrame(JsonUtils.objectToJson(dataContentMsg)));
                }
            }
//            System.out.println("send after");
        }



//        for (Channel channel : clients){
//            channel.writeAndFlush(new TextWebSocketFrame(
//                    "[服务器在]" + LocalDateTime.now()
//                            + "接受到消息, 消息为： " + content));
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        for (ChannelGroup group : groups.values()){
            group.remove(ctx.channel());
        }
        clients.remove(ctx.channel());
    }

    private ChannelGroup getGroup(Integer clubId){
        return groups.computeIfAbsent(clubId, k-> new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
    }
}
