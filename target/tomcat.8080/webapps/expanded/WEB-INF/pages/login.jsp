<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<h3>Login</h3>
	<form:form action="login" method="post" modelAttribute="userForm">
		<table border="0">
			<c:if test="${errorMsg != null}">
				<tr>
					<td><strong style="color: red">${errorMsg }</strong></td>
				</tr>
			</c:if>
			<tr>
				<td>Username: </td>
				<td><form:input path="username" /></td>
			</tr>
			<tr>
				<td>Password: </td>
				<td><form:password path="password"/></td>
			</tr>
			<tr>
                    <td colspan="2" align="right"><input type="submit" name="btnLogin" value="Login"/></td>
            </tr>
			<tr>
				<td colspan="2">Not have an account? Register <a href="" >here.</a></td>
			</tr>
			<tr>
				<td colspan="2"><a href="${fbAPIWrapper.dialogLink }">Login with Facebook</a></td>
			</tr>
			<tr>
				<td colspan="2"><a href="${googleAPIWrapper.dialogLink }" >Login with Google</a></td>
			</tr>
		</table>
	</form:form>
</body>
</html>