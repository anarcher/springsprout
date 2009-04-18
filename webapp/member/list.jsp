<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SpringSprout</title>
</head>

<body>
<div>
<a href="/member/add.do">회원 추가</a>
</div>

<div>
<form:form method="GET" commandName="searchParam">
	이름: <form:input path="name" />
	이메일: <form:input path="email" />
	<input type="submit" value="검색" />
</form:form>
</div>

<div>
<c:if test="${empty memberList}">
회원 목록이 없습니다.
</c:if>

<c:if test="${! empty memberList}">


페이지 사이즈: ${pageParam.size}<br/>
현재 페이지: ${pageParam.page}<br/>
총 갯수: ${pageParam.totalRowsCount}<br/>
현재 페이지 첫 번째 목록 인덱스: ${pageParam.firstRowNumber}<br/>

<c:if test="${pageParam.totalRowsCount > pageParam.size}">

	<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">처음</a> |

	<c:if test="${pageParam.beginPage - 10 > 0}">
		<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">이전</a> |
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
		<a href="/member/list.do?page=${current.count + 10}&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">다음</a> |
	</c:if>

	<a href="/member/list.do?page=${pageParam.totalPage}&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=${orderParam.field}&direction=${orderParam.direction}">마지막</a>

</c:if>

<table>
	<tr>
		<th>
			<c:choose>
				<c:when test="${orderParam.field == 'email' && orderParam.direction == 'asc'}">
					<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=email&direction=desc">이메일V</a>
				</c:when>
				<c:otherwise>
					<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=email&direction=asc">이메일^</a>
				</c:otherwise>
			</c:choose>
		</th>
		<th>
			<c:choose>
				<c:when test="${orderParam.field == 'name' && orderParam.direction == 'asc'}">
					<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=name&direction=desc">이름V</a>
				</c:when>
				<c:otherwise>
					<a href="/member/list.do?page=1&size=${pageParam.size}&name=${searchParam.name}&email=${searchParam.email}&field=name&direction=asc">이름^</a>
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