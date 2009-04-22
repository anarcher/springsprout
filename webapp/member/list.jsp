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

<form method="GET" action="/member/list.do">
	�̸�: <input type="text" name="s_name" value="${c.searchParam.name}" />
	�̸���: <input type="text" name="s_email" value="${c.searchParam.email}" />
	<input type="submit" value="�˻�" />
</form>
</div>

<div>
<c:if test="${empty memberList}">
ȸ�� ����� �����ϴ�.
</c:if>

<c:if test="${! empty memberList}">

������ ������: ${c.pageParam.size}<br/>
���� ������: ${c.pageParam.page}<br/>
�� ����: ${c.pageParam.totalRowsCount}<br/>
���� ������ ù ��° ��� �ε���: ${c.pageParam.firstRowNumber}<br/>

<c:if test="${c.pageParam.totalRowsCount > c.pageParam.size}">

	<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=${c.orderParam.field}&o_direction=${c.orderParam.direction}">ó��</a> |

	<c:if test="${c.pageParam.beginPage - 10 > 0}">
		<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=${c.orderParam.field}&o_direction=${c.orderParam.direction}">����</a> |
	</c:if>

	<c:forEach begin="${c.pageParam.beginPage}" end="${c.pageParam.endPage}" varStatus="current">
		<c:choose>
			<c:when test="${current.count == c.pageParam.page}">
				<a href="/member/list.do?p_page=${current.count}&p_size=${c.pageParam.size}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=${c.orderParam.field}&o_direction=${c.orderParam.direction}"><strong>${current.count}</strong></a> |
			</c:when>
			<c:otherwise>
				<a href="/member/list.do?p_page=${current.count}&p_size=${c.pageParam.size}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=${c.orderParam.field}&o_direction=${c.orderParam.direction}">${current.count}</a> |
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:if test="${c.pageParam.beginPage + 10 < c.pageParam.totalPage}">
		<a href="/member/list.do?p_page=${current.count + 10}&p_size=${c.pageParam.size}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=${c.orderParam.field}&o_direction=${c.orderParam.direction}">����</a> |
	</c:if>

	<a href="/member/list.do?p_page=${c.pageParam.totalPage}&p_size=${c.pageParam.size}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=${c.orderParam.field}&o_direction=${c.orderParam.direction}">������</a>

</c:if>

<table>
	<tr>
		<th>
			<c:choose>
				<c:when test="${c.orderParam.field == 'email' && c.orderParam.direction == 'asc'}">
					<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=email&o_direction=desc">�̸���V</a>
				</c:when>
				<c:otherwise>
					<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=email&o_direction=asc">�̸���^</a>
				</c:otherwise>
			</c:choose>
		</th>
		<th>
			<c:choose>
				<c:when test="${c.orderParam.field == 'name' && c.orderParam.direction == 'asc'}">
					<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=name&o_direction=desc">�̸�V</a>
				</c:when>
				<c:otherwise>
					<a href="/member/list.do?p_page=1&p_size=${c.pageParam.size}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=name&o_direction=asc">�̸�^</a>
				</c:otherwise>
			</c:choose>
		</th>
	</tr>
<c:forEach var="member" items="${memberList}">
	<tr>
		<td><a href="/member/${member.id}.do?p_size=${c.pageParam.size}&p_page=${c.pageParam.page}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=${c.orderParam.field}&o_direction=${c.orderParam.direction}">${member.email}</a></td>
		<td>${member.name}</td>
	</tr>
</c:forEach>
</table>
</c:if>
</div>
</body>

</html>