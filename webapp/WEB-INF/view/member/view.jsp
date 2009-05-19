<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SpringSprout</title>
</head>

<body>
<div>
<a id="listButton" href="<c:url value="/member/list.do?${c.allParam}"/>">목록으로</a> |
<a id="updateButton" href="<c:url value="/member/update/${member.id}.do?${c.allParam}"/>">수정</a> |
<a id="deleteButton" href="<c:url value="/member/delete/${member.id}.do?${c.allParam}"/>">삭제</a>
</div>

<div>
이름: ${member.name}
이메일: ${member.email}
</div>
</body>

</html>