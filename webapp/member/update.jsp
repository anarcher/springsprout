<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>SpringSprout</title>
</head>

<body>
<h1>수정</h1>
<a href="/member/list.do?${c.allParam}">취소</a>
<form:form commandName="member" method="post">
이메일 : <form:input path="email" />
	<form:errors path="email" />
	<br />
비밀번호 : <form:password path="password" showPassword="true" />
	<form:errors path="password" />
	<br />
이름 : <form:input path="name" />
	<form:errors path="name" />
	<br />
<input type="submit" value="저장" />
</form:form>
</body>

</html>