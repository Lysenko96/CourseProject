<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring Login Form</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
	crossorigin="anonymous" />

</head>
<body>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
		integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
		integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
		crossorigin="anonymous"></script>
	<form:form name="submitForm" method="POST">
		<div align="center">
			<table>
				<tr>
					<td>Email</td>
					<td><input type="text" name="email" id="email" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" id="password" /></td>
				</tr>
				<tr>
					<td>User Name</td>
					<td><input type="text" name="name" id="name" /></td>
				</tr>
				<tr>
					<td>User Surname</td>
					<td><input type="text" name="surname" id="surname" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Submit" /></td>
				</tr>
				<tr>
					<td><select name="role">
							<option value="" selected disabled hidden>select role</option>
							<option value="ADMIN">admin</option>
							<option value="USER">user</option>
					</select></td>
					<%-- <%=request.getParameter("role")%> --%>
				</tr>
			</table>
			<a href="/catalog/">to start page</a>
		</div>
	</form:form>
</body>
</html>