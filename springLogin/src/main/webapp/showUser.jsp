<%@ page import="java.io.*,java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor="#D9DFAA">
<h2>Hello,${user.username},你是第N个访问者！</h2>
<table width="100%" border="1" align="center">
<th>用户信息</th><th>登录时间</th>
<tr><td>${user.username }</td><td>${user.time }</td></tr>
</table>
  <font color="red">欢迎登陆！！！</font>
</body>
</html>