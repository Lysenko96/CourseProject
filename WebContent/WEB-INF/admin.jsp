<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Panel</title>
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
	Successfully Admin!
	<div align="center">
		<h1>User list</h1>
		<table border="1" cellpadding="10">
			<tr>
				<th>id</th>
				<th>Email</th>
				<th>Name</th>
				<th>Surname</th>
				<th>Role</th>
				<th>Action</th>
			</tr>
			<c:forEach items="${listUser}" var="user" varStatus="status">
				<tr>
					<td>${user.id}</td>
					<td>${user.email}</td>
					<!-- link to user page for name with rules admin (edit, delete, add) -->
					<td>${user.name}</td>
					<td>${user.surname}</td>
					<td><a href="user?id=${user.id}">${user.role}</a></td>
					<td><a href="edit?id=${user.id}">Edit</a> <a
						href="delete?id=${user.id}">Delete</a>
				</tr>
			</c:forEach>
		</table>
		<a href="/">log out</a>
	</div>
</body>
</html>