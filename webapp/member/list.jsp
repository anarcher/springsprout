<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>SpringSprout</title>
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

	<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">ó��</a> |

	<c:if test="${pageParam.beginPage - 10 > 0}">
		<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">����</a> |
	</c:if>

	<c:forEach begin="${pageParam.beginPage}" end="${pageParam.endPage}" varStatus="current">
		<c:choose>
			<c:when test="${current.count == pageParam.page}">
				<a href="/member/list.do?page=${current.count}&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}"><strong>${current.count}</strong></a> |
			</c:when>
			<c:otherwise>
				<a href="/member/list.do?page=${current.count}&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">${current.count}</a> |
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:if test="${pageParam.beginPage + 10 < pageParam.totalPage}">
		<a href="/member/list.do?page=${current.count + 10}&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">����</a> |
	</c:if>

	<a href="/member/list.do?page=${pageParam.totalPage}&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">������</a>

</c:if>

<table>
	<tr>
		<th>
			<c:choose>
				<c:when test="${orderParam.field == 'email' && orderParam.direction == 'asc'}">
					<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=email&direction=desc">�̸���V</a>
				</c:when>
				<c:otherwise>
					<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=email&direction=asc">�̸���^</a>
				</c:otherwise>
			</c:choose>
		</th>
		<th>
			<c:choose>
				<c:when test="${orderParam.field == 'name' && orderParam.direction == 'asc'}">
					<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=name&direction=desc">�̸�V</a>
				</c:when>
				<c:otherwise>
					<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=name&direction=asc">�̸�^</a>
				</c:otherwise>
			</c:choose>
		</th>
	</tr>
<c:forEach var="member" items="${memberList}">
	<tr>
		<td><a href="/member/${member.id}.do?size=${pageParam.size}&page=${pageParam.page}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">${member.email}</a></td>
		<td>${member.name}</td>
	</tr>
</c:forEach>
</table>
</c:if>
</div>
</body>

</html>