# 华工信使第三方调用API
"华工信使"是由华南理工大学建设的统一消息推送平台，面向校内用户提供消息推送服务。校内第三方应用，可通过该平台向校内用户进行包括华工信使、社交网络（微信企业号、手机QQ公众号）、短信、电子邮件等形式的消息推送。

## 第三方集成身份认证接口
接入华南理工大学统一身份认证的第三WebAPP，可通过添加"华工信使第三方放认证接口"实现与华工信使APP、华南理工大学微信企业号及华南理工大学手机QQ公众号进行集成。

****
>  字符编码:UTF-8
****

### 手机QQ公众号认证接口

> 备注:在Demo里面的js方法要放到在QQ打开的首页里面。
> 跳转到指定页面时会带上参数code和apitype,如果是QQ访问,apitype=1

#### 请求说明
    请求方式: POST
    dataType:"json"
    Content-Type: application/json

#### URL:
    https://ums.scut.edu.cn/Wisdom/qq/getAuthentication

#### 请求包结构体为:
```json
{
"code":"" //QQ提供的code
}
```
#### 返回结果
成功时：
```json
{
"userId":"",         //用户的学工号 (String)
"types":["","",""],  //用户所在的组的id (JSONArray)
"account":"",        //用户的中央认证帐号 (String)
"cardId":"",        //用户的一卡通卡号 (String)
"result":"success"  //(发送成功)
}
```
```json
{
  "result":"no authentication"  //(该用户未认证)
}
```
失败时：
```json
{
  "result":"fail"  //code错误
}
```
***
### 微信企业号认证接口

> 备注:在Demo里面的js方法要放到在微信企业号打开的首页里面。
> 跳转到指定页面时会带上参数code和apitype,如果是微信访问,apitype=2
#### 请求说明
    请求方式: POST
    dataType:"json"
    Content-Type: application/json

#### URL:
    https://ums.scut.edu.cn/Wisdom/wx/getAuthentication

#### 请求包结构体为:
```json
    {
    "code":""  //在企业号里面打开页面微信会提供一个code(String)
    }

```
#### 返回结果
成功时：
```json
{
"userId":"",         //用户的学工号 (String)
"types":["","",""],  //用户所在的组的id (JSONArray)
"account":"",        //用户的中央认证帐号 (String)
"cardId":"",        //用户的一卡通卡号 (String)
"result":"success"  //(发送成功)
}
```
失败时：
```json
  {
    "result":"fail" // code错误
  }
```
***
## 第三方可推送`服务`与`标签`接口

`服务`和`标签`说明:  
`服务`只是决定消息从哪个地方发出来,不影响发送到的用户。  
`标签`里面的是用户，选择标签就是选择要发送的用户。

### 请求说明
    请求方式:POST
    dataType:"json"
    Content-Type: application/json
### URL:
    https://ums.scut.edu.cn/Wisdom/message/getAuth
### 请求包结构体为:
```json
    {
         "account":" ",  //账号（String）(另外方式告知)
         "password":" "  //密码（String）（另外方式告知）
    }
```
###返回结果
成功时：
```json
    {
      "services": [         //所能推送的服务
        {
          "serviceId":"",   //服务ID（long）
          "serviceName":""  //服务名称(String)
        }],
      "labels": [           //所能推送的标签
        {
          "labelId":"",     //标签ID（long）
          "labelName":""    //标签姓名(String)
        }]
    }
```
失败时：
```json
    {
        "result":" wrong account or password " //账号或密码错误
    }
```
## 消息发送接口
### 请求说明
    请求方式:POST
    dataType:"json"
    Content-Type: application/json
### URL:
    https://ums.scut.edu.cn/Wisdom/message/thirdAccess
### 请求包结构体为:
```json
  {
    "account":"",    //账号（String）(另外方式告知)
    "password":"",   //密码（String）（另外方式告知）
    "serviceId":"",  //消息所属服务Id(long) (通过获取权限接口获得)
    "messages":"",   //消息数组(JSONArray)
    "type":"",       //所推送人群标签号(long)（群发的时候传对应标签号，单发就传0）
    "desAccount":"", //目标用户的学号或中央认证帐号(String)（针对某个用户推送消息的时候传对应学号或中央认证帐号，群发时传字符串"0"）
    "isPushAPP":"",  //是否推给APP的标记(int)（0：不推给APP;1：推给APP）
    "isPushWX":"",   //是否推给微信的标记(int)（0：不推给微信;1：推给微信）
    "isPushQQ":"",   //是否推给QQ的标记(int)（0：不推给QQ;1：推给QQ）
    "isPushSMS":"",  //是否推给短信的标记(int)（0：不推给短信;1：推给短信）
    "isPushMail":"",  //是否推给邮箱的标记(int)（0：不推给用户邮箱;1：推给用户邮箱）

    "isSmart":"",     //是否开启智能推送模式(int)(0:否,1:是)(智能模式是指:如果用户关注了微信，就不发短信) (该参数非必填,默认开启)
    "external":[{     //发送消息到指定手机号(JSON数组) (该参数非必填)
                  "name":"张三",
    	          "phone":"13800138000"
               }]
  }
```
> messages中的每个对象都有以下属性：
```json
  {
     "title":"",    //消息标题（String）（没有就传"",即空字符串）
     "url":"",      //消息所在网页url（String）（没有就传"",即空字符串）
     "summary":"",  //消息简介(String)（纯文本消息就传消息内容）
     "picture":""   //图片地址(String) （没有就传"",即空字符串）
   }
```
### 返回结果
成功时：
```json
    {
        "users":[],        //所推送用户的学号数组 (JSONArray)
        "result":"success" //(发送成功)
    }
```
失败时：
```json
{
        "result":"title is too long"            //(标题太长(超过16个字))
}
```
```json
{
        "result":"summary is too long"          //(图文最多60字,纯文字最多400字)
}
```
```json
{
        "result":" wrong account or password "  //(账号或密码错误)
}
```
```json
{
        "result":"no permission"                // (没有权限)
}
```
```json
{
        "result":"no such user"                 //(单发时学号或中央认证帐号错误)
}
```






#第三方拾卡寻人接入
### URL:
    https://ums.scut.edu.cn/Wisdom/message/findPersonForThird 

#### 根据华南理工大学的中央认证系统接入
