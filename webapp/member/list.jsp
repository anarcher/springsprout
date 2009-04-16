<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>springsprout</title>
</head>

<body>
<div>
<a href="/member/add.do">ȸ�� �߰�</a>
</div>

<div>
<c:if test="${empty memberList}">
ȸ�� ����� �����ϴ�.
</c:if>

<c:if test="${! empty memberList}">
<form:form method="GET" commandName="searchParam">
	�̸�: <form:input path="name" />
	�̸���: <form:input path="email" />
	<input type="submit" value="�˻�" />
</form:form>


������ ������: ${pageParam.size}<br/>
���� ������: ${pageParam.page}<br/>
�� ����: ${pageParam.totalRowsCount}<br/>
���� ������ ù ��° ��� �ε���: ${pageParam.firstRowNumber}<br/>

<c:if test="${pageParam.totalRowsCount > pageParam.size}">

	<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}">ó��</a> |

	<c:if test="${pageParam.beginPage - 10 > 0}">
		<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}">����</a> |
	</c:if>

	<c:forEach begin="${pageParam.beginPage}" end="${pageParam.endPage}" varStatus="current">
		<c:choose>
			<c:when test="${current.count == pageParam.page}">
				<a href="/member/list.do?page=${current.count}&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}"><strong>${current.count}</strong></a> |
			</c:when>
			<c:otherwise>
				<a href="/member/list.do?page=${current.count}&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}">${current.count}</a> |
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:if test="${pageParam.beginPage + 10 < pageParam.totalPage}">
		<a href="/member/list.do?page=${current.count + 10}&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}">����</a> |
	</c:if>

	<a href="/member/list.do?page=${pageParam.totalPage}&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}">������</a>

</c:if>

<table>
	<tr>
		<th>�̸���</th>
		<th>�̸�</th>
	</tr>
<c:forEach var="member" items="${memberList}">
	<tr>
		<td><a href="/member/${member.id}.do?size=${pageParam.size}&page=${pageParam.page}&name=${searchParam.name}&email=${searchParam.email}">${member.email}</a></td>
		<td>${member.name}</td>
	</tr>
</c:forEach>
</table>
</c:if>
</div>
</body>

</html>