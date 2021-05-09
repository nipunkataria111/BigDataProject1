<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Movie Recommender System by Sawan</title>
</head>
<body>



	<div
		style="margin: auto; display: block; position: relative; border-style: double; width: 450px; margin-bottom: 10px;">
		<br>
		<form method="POST" action="Recommenderr">
			<h1 style="margin: auto; padding-left: 25px;">Movie Recommender System</h1>
			<hr>
			<br>
			<div style="padding-left: 10px;">
				<label>Select User ID: </label> <input type="text" name="UserID" /><br>
				<br> <label>Enter the number of movies: </label> <input
					type="text" name="n" /><br>
				<br> <label>Select a recommendation engine: </label> <input
					type="radio" value="Item" name="rsystem" /> <label>Item-based
				</label> &nbsp; &nbsp; &nbsp; <input type="radio" value="user"
					name="rsystem" /> <label>User-based </label><br>
				<br> <input type="submit" style="margin-left: 160px; margin-bottom: 10px;"/><br>
			</div>


		</form>
	</div>
</body>
</html>