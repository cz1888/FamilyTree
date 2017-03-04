<%@page import="com.ft.service.UserService,com.ft.entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Manager.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div>管理员界面.</div> <br>
    <%int i; %>
    <table>
    	<tr>
    		<th>用户名</th>
    		<th>密码</th>
    	</tr>
    	<%
    
    	for(i=0;i<5;i++){
    	 %> 
    	<tr>
    		<td>${users[i].username}</td>
    		<td>${users[i].password}</td>
    	</tr>
    	<%} %>
    </table>
  </body>
</html>
