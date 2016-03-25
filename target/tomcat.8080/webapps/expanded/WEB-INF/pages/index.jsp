<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index page</title>
</head>
<body>
	<h1>Hello, ${user.fullname}</h1>
	<table border="0">
		<tr>
			<td><strong>Username: </strong></td>
			<td>${user.username}</td>
		</tr>
		<c:if test="${user.socialId == null }">
			<tr>
				<td colspan="2"><strong>You logged in using normal account!</strong></td>
			</tr>
		</c:if>
		<c:if test="${user.socialId != null }">
			<tr>
				<td colspan="2"><strong>You logged in using Social Network account!</strong></td>
			</tr>
			<tr>
				<td><strong>Social ID:</strong></td>
				<td>${user.socialId }</td>
			</tr>
			<tr>
				<td><strong>Social Link:</strong></td>
				<td>${user.socialLink }</td>
			</tr>
		</c:if>
	</table>
</body>
</html>