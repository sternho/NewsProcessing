<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>
	<p th:text="${status}" />
	
	<form action="/word2vec_train" method="post" enctype="multipart/form-data" >
		Select files: <input type="file" name="file" multiple>
		<br />
		<input type="submit" />
	</form>

	<p>You can select more than one file when browsing for files.
	<br />
	Maximum file size: 1000MB
	</p>

</body>
</html>
