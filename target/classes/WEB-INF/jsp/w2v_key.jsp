<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #f2f2f2
}

th {
	background-color: #4CAF50;
	color: white;
}
</style>
</head>
<body>

	<form action="set_key">
		Add Key: <input name="key" type='text' /><br/>
		<input type="submit" />		
	</form>
	<br/><br/>
	
	<table>
		<tr>
			<th>KEY</th>
			<th>VALUE</th>
		</tr>
		<c:forEach var="data" items="${keys}">
			<tr>
				<td>${data.key}</td>
				<td>${data.value}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
