<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>SpringSprout</title>
</head>

<body>
<div>
<a href="/member/list.do?p_size=${c.pageParam.size}&p_page=${c.pageParam.page}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=${c.orderParam.field}&o_direction=${c.orderParam.direction}">�������</a> |
<a href="/member/update/${member.id}.do?p_size=${c.pageParam.size}&p_page=${c.pageParam.page}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=${c.orderParam.field}&o_direction=${c.orderParam.direction}">����</a> |
<a href="/member/delete/${member.id}.do?p_size=${c.pageParam.size}&p_page=${c.pageParam.page}&s_name=${c.searchParam.name}&s_email=${c.searchParam.email}&o_field=${c.orderParam.field}&o_direction=${c.orderParam.direction}">����</a>
</div>

<div>
�̸�: ${member.name}
�̸���: ${member.email}
</div>
</body>

</html>