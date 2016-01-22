<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<c:forEach items="${requestScope.data}" var="item">
	${item} <br/>
</c:forEach>

<jsp:include page="footer.jsp"/>
</body>
</html>