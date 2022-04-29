<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Fetch Brewery Info</title>
</head>
<body>
<h1><%= "Fetch Brewery Information" %>
</h1>
<br/>
<form action="fetch-server" method="GET">
    <label>Type the name of the brewery. </label>
    <!-- box for enter the input text -->
    <input type="text" name="inputText" value="" /><br>
    <!-- submit button -->
    <input type="submit" value="Submit" />
</form>
</body>
</html>