# 功能

- 俱乐部创建、加入，俱乐部群聊个人私聊，俱乐部活动举办、参加、签到、评价、查看完成情况等
- 运动知识库发表点赞、评论
- 每日目标设置推送
- 根据华为手环进行运动检测
- 个人信息维护、头像上传

# 重新运行

## 后端代码修改

1. **根据后端代码中sport_community.sql创建数据库并修改application.properties文件的数据库地址**

2. **部署Redis的server并在application.properties修改Redis地址**

3. **修改HwUtil中为真正服务器地址（可内网穿透）**

   ```
   redirectUri = "http://im7tif.natappfree.cc/detection/getCode";
   ```

4. **利用容器部署fastdfs并修改application.properties中的fdfs地址**

5. **在application.properties修改Pusher组件的相应配置**

## 前端代码修改

1. **在app.js中修改nettyServerUrl、serverUrl、imgServerUrl、sportCodeUrl的部分回调地址**
2. **在index.html中修改pusher的相关参数进行监听**

## 其他

1. **打开natapp、cpolar内网穿透cpolar http 8888**
2. **hw运动联盟注册回调地址natappurl + /detection/getCode**（要使用健康检测功能）

## Docker安装fastdfs  

<details><summary>安装过程</summary>  

* 拉取镜像

```
docker pull morunchang/fastdfs
```

* 运行tracker

```
docker run -d --name tracker --net=host morunchang/fastdfs sh tracker.sh
```

* 运行storage

```
docker run -d --name storage --net=host -e TRACKER_IP=119.96.167.114:22122 -e GROUP_NAME=group1 morunchang/fastdfs sh storage.sh

```

> `Nginx`端口应该是默认映射为`8080`吧  
>
> 1. 使用的网络模式是–net=host,  `<your tracker server address>` 替换为你机器的Ip即可  
> 2. `<group name>` 是组名，即storage的组  
> 3. 如果想要增加新的storage服务器，再次运行该命令，注意更换 新组名
>    运行完后会自动进入容器内部，输入【exit】退出容器内部   
> 4. docker ps 查看容器信息 
>
> ```
> [root@iZh3cshm0xz7wjZ ~]# docker ps 
> CONTAINER ID        IMAGE                COMMAND             CREATED              STATUS              PORTS               NAMES
> ccdf6bbeab48        morunchang/fastdfs   "sh storage.sh"     5 seconds ago        Up 4 seconds                            storage
> a7253c93bce1        morunchang/fastdfs   "sh tracker.sh"     About a minute ago   Up About a minute                       tracker
> ```

```
* 修改nginx的配置，不拦截上传内容
```

//1.进入容器内部
docker exec -it storage  /bin/bash

 // storage 是 docker ps 中的NAMES
 // exit 退出

root@iZh3cshm0xz7wjZ:/# cd data
root@iZh3cshm0xz7wjZ:/data# ls
fast_data  fastdfs  fastdfs-nginx-module  libfastcommon  nginx  nginx-1.9.11.tar.gz

//2.修改nginx配置文件
root@iZh3cshm0xz7wjZ:/# vi /data/nginx/conf/nginx.conf

//3. 添加修改内容
location /lwc/M00 {
   proxy_next_upstream http_502 http_504 error timeout invalid_header;
     proxy_cache http-cache;
     proxy_cache_valid  200 304 12h;
     proxy_cache_key $uri$is_args$args;
     proxy_pass http://fdfs_group1;
     expires 30d;
 }

//4.退出
root@iZh3cshm0xz7wjZ:/data/nginx/conf# exit
exit

//5. 重启storage服务
[root@iZh3cshm0xz7wjZ ~]# docker restart storage
storage

```
通过spring-boot-fastDFS可以来测试是否安装好fastdfs，我参考的是这篇[博客](https://blog.csdn.net/lizhihaooo/article/details/79261388#commentsedit)  
能上传文件，并通过网址访问就表示成功了
```
