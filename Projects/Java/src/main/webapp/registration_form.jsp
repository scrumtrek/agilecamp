<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>Регистрация</title>
</head>
<body>
Регистрация:
<form action="do" method="POST">
    <input type="hidden" name="action" value="register" />
	Логин:<input type="text" name="name"/><br/>
	Пароль:<input type="password" name="password"/><br/>
	Повтор пароля:<input type="password" name="password_again"/><br/>
	<input type="submit"/>
</form>

</body>
</html>