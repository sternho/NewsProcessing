<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Word2Vec X Sentiment Analyst</title>

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

	<table>
		<tr>
			<th>KEY</th>
			<th>Very Positive</th>
			<th>Positive</th>
			<th>Neutral</th>
			<th>Negative</th>
			<th>Very Negative</th>
		</tr>
		<c:forEach var="data" items="${result}">
			<tr>
				<td>${data.getKey()}</td>
				<td>${data.getVeryPositive()}</td>
				<td>${data.getPositive()}</td>
				<td>${data.getNeutral()}</td>
				<td>${data.getNegative()}</td>
				<td>${data.getVeryNegative()}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>