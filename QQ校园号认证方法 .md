


#QQ校园号认证接口

####备注:在Demo里面的js方法要放到在QQ打开的首页里面。

##请求说明
###请求方式: 
POST

dataType:"json"

Content-Type: application/json

###URL:
http://ghxs.88u.cas.scut.edu.cn/Wisdom/qq/getAuthentication

###请求包结构体为:
```
{
"code":"" //QQ提供的code
}

```

---


###返回结果
成功时：
```
{
" account":"" //用户的中央认证帐号 (String)
" result":"success"//(发送成功)
}
```
```
{
" result":"no authentication"  //(该用户未认证)
}
```
失败时：
```
{

"result":"fail"(code错误)

}
```


