# http-me  关于http一些总结  z18501368267@gmail.com


* [1.基本原理](#1)
* [2.java原生http请求](#2)
* [3.httpclient](#3)
* [4.okhttp](#4)
* [5.feign](#5)


<h2 id="1">基本原理</h2>
#### 简介

通过TCP实现了可靠的数据传输，能够保证数据的完整性，正确性的应用层协议
他是一种无序状态协议

#### 交互流程

1. 通过请求url拿到域名端口等信息 最后解析出ip端口
2. 通过ip端口建立tcp连接
3. 建立了tcp连接，web浏览器就可以发送请求信息
4. web服务器收到请求信 返回应答报文信息（包含应答头信息和实际数据）
5. 关闭tcp连接(Connection:keep-alive 则不会关闭连接 也是连接池的基础)

#### 请求方式
```
GET
通过请求URI得到资源
POST,
用于添加新的内容
PUT
用于修改某个内容
DELETE,
删除某个内容
CONNECT,
用于代理进行传输，如使用SSL
OPTIONS
询问可以执行哪些方法
PATCH,
部分文档更改
PROPFIND, (wedav)
查看属性
PROPPATCH, (wedav)
设置属性
MKCOL, (wedav)
创建集合（文件夹）
COPY, (wedav)
拷贝
MOVE, (wedav)
移动
LOCK, (wedav)
加锁
UNLOCK (wedav)
解锁
TRACE
用于远程诊断服务器
HEAD
类似于GET, 但是不返回body信息，用于检查对象是否存在，以及得到对象的元数据
```

#### http应答码
```
1XX－信息类(Information),表示收到Web浏览器请求，正在进一步的处理中
2XX－成功类（Successful）,表示用户请求被正确接收，理解和处理例如：200 OK
3XX-重定向类(Redirection),表示请求没有成功，客户必须采取进一步的动作。
4XX-客户端错误(Client Error)，表示客户端提交的请求有错误 例如：404 NOT Found，意味着请求中所引用的文档不存在。
5XX-服务器错误(Server Error)表示服务器不能完成对请求的处理：如 500
```

#### 请求头
可以在header中添加自己的键值对
```
Accept: application/json, text/plain, */*
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9,en;q=0.8
Connection: keep-alive
Content-Length: 225
Content-Type: application/json;charset=UTF-8
Cookie: _ntes_nnid=158bdaef762659c16ff34db660b2fd26,1531815420954; _ga=GA1.2.1334314859.1531993501; menuShow=true; _gid=GA1.2.1762628390.1536542626; JSESSIONID=2FA599A886A737DF7076F79E9DEB663B.crm
Host: www.baidu.com
Origin: http://www.baidu.com
TOKEN: TUb5bb22d18jWCOX7Q5e5QX_GTbm-RMiAOo60YADUyhDZbVoTPemykm5c4Za5EnScUfebZxlp9QfJby-AV2XWw
User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36
```
#### 响应头
```
Bdpagetype: 2
Bdqid: 0x87c517480001d0a2
Cache-Control: private
Connection: Keep-Alive
Content-Encoding: gzip
Content-Type: text/html;charset=utf-8
Date: Fri, 14 Sep 2018 08:39:50 GMT
Expires: Fri, 14 Sep 2018 08:39:50 GMT
Server: BWS/1.1
Set-Cookie: BDSVRTM=444; path=/
Set-Cookie: BD_HOME=1; path=/
Set-Cookie: H_PS_PSSID=1434_26966_21123_19897_26350_22075; path=/; domain=.baidu.com
Strict-Transport-Security: max-age=172800
Transfer-Encoding: chunked
X-Ua-Compatible: IE=Edge,chrome=1
```

<h2 id="2">java原生http请求</h2>

<h2 id="3">httpclient</h2>

<h2 id="4">okhttp</h2>

<h2 id="5">feign</h2>

