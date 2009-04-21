<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SpringSprout</title>
</head>

<body>
<h1>수정</h1>
<a href="/member/list.do?size=${pageParam.size}&page=${pageParam.page}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">취소</a>
<form:form commandName="member" method="post">
이메일 : <form:input path="email" />
	<form:errors path="email" />
	<br />
비밀번호 : <form:password path="password" />
	<form:errors path="password" />
	<br />
이름 : <form:input path="name" />
	<form:errors path="name" />
	<br />
<input type="submit" value="저장" />
</form:form>
</body>

</html>