<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<form action="people-servlet" method="POST">
    <table border="0" cellpadding="0" cellspacing="4">
        <tr>
            <td align="right">Vorname:</td>
            <td><input name="vorname" type="text" size="20" maxlength="40" value="Jean Claude"></td>
        </tr>
        <tr>
            <td align="right">Nachname:</td>
            <td><input name="nachname" type="text" size="20" maxlength="40"></td>
        </tr>
        <tr>
            <td align="right">Beruf:</td>
            <td><input name="beruf" type="text" size="20" maxlength="40"></td>
        </tr>
    </table>
    <p>
        <input type="submit" name="z" value="Abschicken"> <br>
    </p>
</form>
</body>
</html>