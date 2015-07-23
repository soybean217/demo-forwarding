## Introduction 简介
A demo for store and forwarding http data . Base SP or MM fee or user register interface .

一个存储和转发http数据接口的样例。基于SP或MM计费或用户注册数据接口。

## Structure 结构
demo-base，公共功能包。

demo-interface，HTTP数据接收存储接口工程。interface web project .

demo-admin，统计报表工程。statistics web project .

demo-mm，MM数据接口工程。web interface web project .

## Require 要求
eclipse
MAVEN
mysql

## Usage
### tomcat configure
Import existing project to eclipse .

About project properties: Check java build path . Perhaps need modify pom.xml to refresh maven update . Perhaps need add Dynamic Web Module 3.0 and above Java 1.6 to Project Facets . 

Confirm Deployment Assembly correct . 

在Eclipse中导入已经存在的工程。

工程属性修改：检查Java的构建路径无误。也许可以通过修改一下pom.xml增减空格来激活Maven 。也许需要在Project Facets中添加Dynamic Web Module 3.0和Java 1.6 以上支持。

确认Deployment Assembly配置正确。

/src -> WEB-INF/classes

/WebRoot(jsp file dictionary default perhaps WebContent) -> /

demo-base -> WEB-INF/classes/base.jar

Maven Dependencies -> WEB-INF/lib

### mysql configure

prepare mysql database with database.sql and grant user in src/config.properties 

准备好mysql数据库，结构在database.sql文件中，账户资料配置在config.properties中。

### test sample

Admin project:
http://site:port/demo-admin/demo/login.jsp

Username:1@1.cn
password:1

Interface sample:
http://site:port/demo-interface/demo.jsp?mobile=11&cmd=11&ret=1

It will be store in table receives . Forwarding URL can modify is in demo.jsp , and structure is in /demo-base/src/main/java/org/demo/service/HttpSend.java .

收到的数据将会被存储在receives表中，转发数据的URL在demo.jsp文件中配置，转发数据结构(参数名)在/demo-base/src/main/java/org/demo/service/HttpSend.java文件中。

## License
Apache