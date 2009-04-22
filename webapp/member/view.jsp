<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SpringSprout</title>
</head>

<body>
<div>
<a href="/member/list.do?size=${pageParam.size}&page=${pageParam.page}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">목록으로</a> |
<a href="/member/update/${member.id}.do?size=${pageParam.size}&page=${pageParam.page}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">수정</a> |
<a href="/member/delete/${member.id}.do?size=${pageParam.size}&page=${pageParam.page}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">삭제</a>
</div>

<div>
이름: ${member.name}
이메일: ${member.email}
</div>
</body>

</html>