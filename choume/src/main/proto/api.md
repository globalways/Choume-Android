# 筹么API详解

## 服务器构成
客户端需要连接2个服务器:

 1. 环途用户中心服务器
 2. 筹么APP服务器
 
## API版本
 分为http版本，和rpc版本（grpc）
 grpc版本的api，在调用之后必须主动关闭，否则服务器会爆
 http版本的api是rpc版本的一种http实现，非标准版本的http服务，未遵循http常用规则：简单来说，将rpc的参数列表json化后放到httpbody，然后服务端返回json化的httpbody。
 
## 用户构成
  用户有两个，一个是User，一个是CfUser。User是环途用户中心用户，CfUser是筹么App用户，两者之间用hongID关联。其中基础用户功能绑定在User上，比如头像，电话等等；跟筹么App有关的用户操作绑定在CfUser上，比如认证，收货地址等。
  
### RongCloudService.SendRCSystemMessage方法
其中参数中的objectName代表消息类型，内置类型请参照融云文档，非自定义类型，需要客户端与服务端同步，现用到的这个需求为【筹么】的消息，暂定objectName为【CF:message】,其中content为proto中的【CfMessage】对象的json，客户端接收到后需自己进行解析

### QiniuService.MakeUpToken方法
具体参数详情，参考七牛文档，【筹么】app的bucket为【choume】，需要注意的是返回值中的domain，需与key拼接后再上传app服务器

### 返回对象【Response】值列表

* 1 ok
* 100 app错误
* 101 服务器错误
* 102 登陆token错误
* 103 用户未被授权
* 104 用户资源不够
* 105 注册用户错误
* 106 用户名密码错误
* 107 请求参数错误
* 108 用户已经存在
* 109 不支持的短信类型
* 110 短信验证码错误
* 111 钱包余额不足
* 112 未找到该记录
* 113 第三方钱包未验证
* 114 用户被锁定
* 115 众筹项目未发布
* 116 众筹项目的某一回报方式已经投资满额
* 117 众筹项目过期了