window.app = {
	/**
	 * netty服务后端发布的url地址
	 */
	//todo 修改
	nettyServerUrl: 'ws://5dc42d68.r8.cpolar.top/ws',
	
	/**
	 * 后端服务发布的url地址
	 */
	//todo 修改
	serverUrl: 'http://c7s9m9.natappfree.cc',
	
	/**
	 * 图片服务器的url地址
	 */
	imgServerUrl: 'http://119.96.167.114:8070/group1/',
	
	/**
	 * 获取授权码Code Url
	 */
	//todo 修改
	sportCodeUrl: "https://oauth-login.cloud.huawei.com/oauth2/v3/authorize?" + 
					"response_type=code&client_id=108131433&redirect_uri="+
					"http://c7s9m9.natappfree.cc/detection/getCode&scope=openid+https%3A%2F%2Fwww.huawei.com%2Fhealthkit%2Fstep.read+"+
					"https%3A%2F%2Fwww.huawei.com%2Fhealthkit%2Fdistance.read+"+
					"https%3A%2F%2Fwww.huawei.com%2Fhealthkit%2Fdistance.read+"+
					"https%3A%2F%2Fwww.huawei.com%2Fhealthkit%2Fcalories.read&"+
					"access_type=offline&display=touch",
	
	isNotNull: function(str) {
		if (str != null && str != "" && str != undefined) {
			return true;
		}
		return false;
	},
	
	/**
	* 封装消息提示框，默认mui的不支持居中和自定义icon，所以使用h5+
	* @param {Object} msg
	* @param {Object} type
	*/
	showToast: function(msg, type) {
		plus.nativeUI.toast(msg,
			{icon: "images/" + type + ".png", verticalAlign: "center"})
	},
	/**
	* 保存用户的全局对象
	* @param {Object} user
	*/
	setUserGlobalInfo: function(user) {
		var userInfoStr = JSON.stringify(user);
		plus.storage.setItem("userInfo", userInfoStr);
	},
	
	/**
	* 获取用户的全局对象
	*/
	getUserGlobalInfo: function() {
		var userInfoStr = plus.storage.getItem("userInfo");
		return JSON.parse(userInfoStr);
	},
	/**
	* 登出后，移除用户全局对象
	*/
	userLogout: function() {
		plus.storage.removeItem("userInfo");
	},
	/**
	 * 和后端的枚举对应
	 */
	CONNECT: 1, 	// 第一次(或重连)初始化连接
	CHAT: 2, 		// 聊天消息
	SIGNED: 3, 		// 消息签收	
	KEEPALIVE: 4,   //保持心跳
	PCONNECT: 5,  //私聊连接
	PCHAT: 6,    // 私聊聊天
	/**
	 * 和后端的 ChatMsg 聊天模型对象保持一致
	 * @param {Object} userId
	 * @param {Object} clubId
	 * @param {Object} msg
	 * @param {Object} msgId
	 */
	ChatMsg: function(userId, clubId, msg, msgId){
		this.userId = userId;
		this.clubId = clubId;
		this.msg = msg;
		this.msgId = msgId;
	},
	
	/**
	 * 构建消息 DataContent 模型对象
	 * @param {Object} action
	 * @param {Object} chatMsg
	 */
	DataContent: function(action, chatMsg){
		this.action = action;
		this.chatMsg = chatMsg;
	},
	
	/**
	 * 单个聊天记录的对象
	 * @param {Object} userId 发送者
	 * @param {Object} clubId
	 * @param {Object} msg
	 * @param {Object} flag
	 */
	ChatHistory: function(userId, clubId, msg, flag){
		this.userId = userId;
		this.clubId = clubId;
		this.msg = msg;
		this.flag = flag;
	},
	
	/**
	 * 获取用户聊天记录
	 * @param {Object} myId
	 * @param {Object} friendId
	 */
	getUserChatHistory: function(myId, clubId) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + clubId;
		// console.log("get id :" + chatKey);
		// plus.storage.removeItem(chatKey);
		var clubChatHistoryStr = plus.storage.getItem(chatKey);
		var clubChatHistory;
		if (me.isNotNull(clubChatHistoryStr)) {
			// 如果不为空
			clubChatHistory = JSON.parse(clubChatHistoryStr);
		} else {
			// 如果为空，赋一个空的list
			clubChatHistory = new Object();
			clubChatHistory.msgId = 0;
			clubChatHistory.historyList = [];
			plus.storage.setItem(chatKey, JSON.stringify(clubChatHistory));
		}
		return clubChatHistory;
	},
	
	/**
	 * 获取用户聊天记录
	 * @param {Object} myId
	 * @param {Object} friendId
	 */
	getPersonHistory: function(myId, clubId) {
		var me = this;
		var chatKey = "chatU-" + myId + "-" + clubId;
		// console.log("get id :" + chatKey);
		// plus.storage.removeItem(chatKey);
		var clubChatHistoryStr = plus.storage.getItem(chatKey);
		var clubChatHistory;
		if (me.isNotNull(clubChatHistoryStr)) {
			// 如果不为空
			clubChatHistory = JSON.parse(clubChatHistoryStr);
		} else {
			// 如果为空，赋一个空的list
			clubChatHistory = new Object();
			clubChatHistory.msgId = 0;
			clubChatHistory.historyList = [];
			plus.storage.setItem(chatKey, JSON.stringify(clubChatHistory));
		}
		// console.log(JSON.stringify(clubChatHistory));
		return clubChatHistory;
	},
	
	getUserFace: function(userId){
		var me = this;
		var key = "face-" + userId;
		// var user = this.getUserGlobalInfo();
		var myFace = sessionStorage.getItem(key);
		if(!me.isNotNull(myFace)){
			// 与后端联调
			mui.ajax(app.serverUrl + "/user/getFace",{
				data:{
					userId:userId
				},
				dataType:'json',//服务器返回json格式数据
				type:'post',//HTTP请求类型
				timeout:10000,//超时时间设置为10秒；
				async: false,
				headers:{'Content-Type': 'application/x-www-form-urlencoded'},	              
				success:function(data){
					// console.log(JSON.stringify(data));
					if (data.status == "success") {
						sessionStorage.setItem(key,JSON.stringify(data.data));
						myFace =  data.data;
						// return myFace;
					} else {
						app.showToast(data.msg, "error");
					}
				}
			});
		}else{
			myFace = JSON.parse(myFace);
		}		
		// console.log(myFace);
		return myFace;
	},
	
	/**
	 * 保存用户的聊天记录
	 * @param {Object} myId
	 * @param {Object} clubId
	 * @param {Object} msg
	 * @param {Object} flag	判断本条消息是我发送的，还是朋友发送的，1:我  2:else
	 */
	saveUserChatHistory: function(userId, clubId, msg, msgId, flag) {
		var me = this;
		var user = me.getUserGlobalInfo();
		var chatKey = "chat-" + user.id + "-" + clubId;
		// console.log("save id:"+chatKey);
		
		// plus.storage.removeItem(chatKey);
		// 从本地缓存获取聊天记录是否存在
		var clubChatHistoryStr = plus.storage.getItem(chatKey);
		var clubChatHistory;
		if (me.isNotNull(clubChatHistoryStr)) {
			// 如果不为空
			clubChatHistory = JSON.parse(clubChatHistoryStr);
		} else {
			// 如果为空，赋一个空的list
			clubChatHistory = new Object();
			clubChatHistory.historyList = [];
			clubChatHistory.msgId = 0;
		}
		// 构建聊天记录对象
		var singleMsg = new me.ChatHistory(userId, clubId, msg, flag);
		// 向list中追加msg对象
		clubChatHistory.historyList.push(singleMsg);
		clubChatHistory.msgId = msgId;
		// console.log(JSON.stringify(clubChatHistory));
		
		plus.storage.setItem(chatKey, JSON.stringify(clubChatHistory));
	},
	
	/**
	 * 保存私聊用户的聊天记录
	 * @param {Object} userId
	 * @param {Object} friendId
	 * @param {Object} msg
	 * @param {Object} flag	判断本条消息是我发送的，还是朋友发送的，1:我  2:else
	 */
	savePersonHistory: function(userId, friendId, msg, msgId, flag, suffixKey) {
		var me = this;
		var user = me.getUserGlobalInfo();
		var chatKey = "chatU-" + user.id + "-" + suffixKey;
		// console.log("save id:"+chatKey);
		
		// plus.storage.removeItem(chatKey);
		// 从本地缓存获取聊天记录是否存在
		var clubChatHistoryStr = plus.storage.getItem(chatKey);
		var clubChatHistory;
		if (me.isNotNull(clubChatHistoryStr)) {
			// 如果不为空
			clubChatHistory = JSON.parse(clubChatHistoryStr);
		} else {
			// 如果为空，赋一个空的list
			clubChatHistory = new Object();
			clubChatHistory.historyList = [];
			clubChatHistory.msgId = 0;
		}
		// 构建聊天记录对象
		var singleMsg = new me.ChatHistory(userId, friendId, msg, flag);
		// 向list中追加msg对象
		clubChatHistory.historyList.push(singleMsg);
		clubChatHistory.msgId = msgId;
		// console.log(JSON.stringify(clubChatHistory));
		 
		plus.storage.setItem(chatKey, JSON.stringify(clubChatHistory));
	},
	
	
	
}
