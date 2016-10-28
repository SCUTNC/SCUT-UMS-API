# 华工信使第三方调用API
"华工信使"是由华南理工大学建设的统一消息推送平台，面向校内用户提供消息推送服务。校内第三方应用，可通过该平台向校内用户进行包括华工信使、社交网络（微信企业号、手机QQ公众号）、短信、电子邮件等形式的消息推送。

## 第三方集成身份认证接口
接入华南理工大学统一身份认证的第三WebAPP，可通过添加"华工信使第三方放认证接口"实现与华工信使APP、华南理工大学微信企业号及华南理工大学手机QQ公众号进行集成。

### 手机QQ公众号认证接口

> 备注:在Demo里面的js方法要放到在QQ打开的首页里面。

#### 请求说明
    请求方式: POST
    dataType:"json"
    Content-Type: application/json

#### URL:
    http://ghxs.88u.cas.scut.edu.cn/Wisdom/qq/getAuthentication

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
  "account":" ",      //用户的中央认证帐号 (String)
  "result":"success"  //(发送成功)
}
```
```json
{
  " result":"no authentication"  //(该用户未认证)
}
```
失败时：
```json
{
  "result":"fail" //code错误
}
```

### 微信企业号认证接口

#### 请求说明
    请求方式: POST
    dataType:"json"
    Content-Type: application/json

#### URL:
    http://ghxs.88u.cas.scut.edu.cn/Wisdom/wx/getAuthentication

#### 请求包结构体为:
```json
    {
    "code":"  " //在企业号里面打开页面微信会提供一个code(String)
    }

```
#### 返回结果
成功时：
```json
    {
    " account":"  ",  //用户的中央认证帐号 (String)
    " result":"success" //(发送成功)
    }
```

失败时：
```json
  {
    "result":"fail" // code错误
  }
```

## 第三方可推送`服务`与`标签`接口



### 请求说明
    请求方式:POST
    dataType:"json"
    Content-Type: application/json
### URL:
    http://ghxs.88u.cas.scut.edu.cn/Wisdom/message/getAuth

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
      " services": [ //所能推送的服务
        {
          "serviceId":服务ID（long）
          "serviceName":服务名称(String)
        }]
      "labels": [//所能推送的标签
        {
          "labelId":标签ID（long）
          "labelName":标签姓名(String)
        }]
    }
```
失败时：
```json
    {
        " result":" wrong account or password " //账号或密码错误
    }
```

## 消息发送接口
### 请求说明
    请求方式:POST
    dataType:"json"
    Content-Type: application/json
### URL:
    http://ghxs.88u.cas.scut.edu.cn/Wisdom/message/thirdAccess

### 请求包结构体为:
```json
  {
    "account":"",    //账号（String）(另外方式告知)
    "password":"",   //密码（String）（另外方式告知）
     "serviceId":"", //消息所属服务Id(long)( 1：就业招聘；2：学生活动;3：拾卡寻人;4.成绩查询;5.重要通知 6.网络保障;7.校园网)
     "messages":"",  //消息数组(JSONArray)
    "type":"",       //所推送人群标签号(long)（群发的时候传对应标签号，单发就传0）
    "desAccount":"",//目标用户的学号(String)（针对某个用户推送消息的时候传对应学号，群发时传字符串"0"）
    "isPushAPP":"",  //是否推给APP的标记(int)（0：不推给APP;1：推给APP）
    "isPushWX":"",   //是否推给微信的标记(int)（0：不推给微信;1：推给微信）
    "isPushQQ":"",   //是否推给QQ的标记(int)（0：不推给QQ;1：推给QQ）
    "isPushSMS":"",  //是否推给短信的标记(int)（0：不推给短信;1：推给短信）
    "isPushMail":"" //是否推给邮箱的标记(int)（0：不推给用户邮箱;1：推给用户邮箱）
  }
```
#### messages中的每个对象都有以下属性：
```json
  {
     "title":"",    //消息标题（String）（没有就传"",即空字符串）
     "url":"",      //消息所在网页url（String）（没有就传"",即空字符串）
     "summary":"", //消息简介(String)（纯文本消息就传消息内容）
     "picture":"" //图片地址(String) （没有就传"",即空字符串）
   }
```
### 返回结果
成功时：
```json
    {
        " users":"",        //所推送用户的学号数组 (JSONArray)
        " result":"success" //(发送成功)
    }
```
失败时：

``on
    {
        "result":" wrong account or password "  //账号或密码错误
    }
```

```json
  {
    "result":"fail"  //发送失败
  }
```

```json
  {
     "result":" wrong account or password "  //账号或密码错误
   }
```
```json
   {
      "result":"fail"  //发送失败
   }
```


