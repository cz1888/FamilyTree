 <%@ page contentType="text/html;charset=UTF-8" language="java"%>
 <form method="post" action="${pageContext.request.contextPath}/user/toSignUp">
	<table>
	<tr>
		<td>用户名</td>
		<td><input type="text" name="username"></td>
	</tr>
	<tr>
		<td>密码</td>
		<td><input type="password" name="password"></td>
	</tr>
	<tr>
		<td><input type="submit" value="注册" ></td>
		<td></td>
	</tr>
	</table>
  </form>
