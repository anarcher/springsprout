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
<a href="<c:url value="/member/add.do"/>">회원 추가</a>

<form method="get" action="/member/list.do">
	이름: <input type="text" name="s_name" value="${c.searchParam.name}" />
	이메일: <input type="text" name="s_email" value="${c.searchParam.email}" />
	<input type="hidden" name="p_size" value="${c.pageParam.size}" />
	<input type="submit" value="검색" />
</form>
</div>

<div>
<c:if test="${empty memberList}">
회원 목록이 없습니다.
</c:if>

<c:if test="${! empty memberList}">

페이지 사이즈: ${c.pageParam.size}<br/>
현재 페이지: ${c.pageParam.page}<br/>
총 페이지: ${c.pageParam.totalPage}<br/>
총 갯수: ${c.pageParam.totalRowsCount}<br/>
현재 페이지 첫 번째 목록 인덱스: ${c.pageParam.firstRowNumber}<br/>

<form method="get" action="/member/list.do">
	<select name="p_size">
		<c:forEach items="${c.pageParam.pageSizes}" var="currentSize">
			<c:choose>
			<c:when test="${currentSize == c.pageParam.size}">
				<option value="${currentSize}" selected="selected">${currentSize}</option>
			</c:when>
			<c:otherwise>
				<option value="${currentSize}">${currentSize}</option>
			</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
	<input type="hidden" name="s_name" value="${c.searchParam.name}" />
	<input type="hidden" name="s_email" value="${c.searchParam.email}" />
	<input type="submit" value="페이징" />
</form>
<c:if test="${c.pageParam.totalRowsCount > c.pageParam.size}">

	<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&${c.searchParam}&${c.orderParam}">처음</a> |

	<c:if test="${c.pageParam.beginPage - 10 > 0}">
		<a href="/member/list.do?p_page=${c.pageParam.beginPage - 10}&p_size=${c.pageParam.size}&${c.searchParam}&${c.orderParam}">이전</a> |
	</c:if>

	<c:forEach begin="${c.pageParam.beginPage}" end="${c.pageParam.endPage}" var="current" >
		<c:choose>
			<c:when test="${current == c.pageParam.page}">
				<a href="/member/list.do?p_page=${current}&p_size=${c.pageParam.size}&${c.searchParam}&${c.orderParam}"><strong>${current}</strong></a> |
			</c:when>
			<c:otherwise>
				<a href="/member/list.do?p_page=${current}&p_size=${c.pageParam.size}&${c.searchParam}&${c.orderParam}">${current}</a> |
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:if test="${c.pageParam.beginPage + 10 < c.pageParam.totalPage}">
		<a href="/member/list.do?p_page=${c.pageParam.endPage + 1}&p_size=${c.pageParam.size}&${c.searchParam}&${c.orderParam}">다음</a> |
	</c:if>

	<a href="/member/list.do?p_page=${c.pageParam.totalPage}&p_size=${c.pageParam.size}&${c.searchParam}&${c.orderParam}">마지막</a>

</c:if>

<table>
	<tr>
		<th>
			<c:choose>
				<c:when test="${c.orderParam.field == 'email' && c.orderParam.direction == 'asc'}">
					<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&${c.searchParam}&o_field=email&o_direction=desc">이메일V</a>
				</c:when>
				<c:otherwise>
					<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&${c.searchParam}&o_field=email&o_direction=asc">이메일^</a>
				</c:otherwise>
			</c:choose>
		</th>
		<th>
			<c:choose>
				<c:when test="${c.orderParam.field == 'name' && c.orderParam.direction == 'asc'}">
					<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&${c.searchParam}&o_field=name&o_direction=desc">이름V</a>
				</c:when>
				<c:otherwise>
					<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&${c.searchParam}&o_field=name&o_direction=asc">이름^</a>
				</c:otherwise>
			</c:choose>
		</th>
	</tr>
<c:forEach var="member" items="${memberList}">
	<tr>
		<td><a href="/member/${member.id}.do?${c.allParam}">${member.email}</a></td>
		<td>${member.name}</td>
	</tr>
</c:forEach>
</table>
</c:if>
</div>
</body>

</html>