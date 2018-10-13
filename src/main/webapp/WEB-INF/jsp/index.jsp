<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8" />
<title>Welcome</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
	<h1>Welcome</h1>
	<h2>${message}</h2>
	<BR/>
	
	<ul type="circle">
		<li><a href="view_data"		>NLP Processed Data</a></li>
		<li><a href="export_csv"	>Export Csv</a></li>
		<li><a href="set_key"		>Add Word2Vec keys</a></li>
		<li><a href="word2vec"		>Train the Word2Vec</a></li>
		<li><a href="result_nlp_w2v">Search result by Word2Vec</a></li>
	</ul>
	
</body>
</html>