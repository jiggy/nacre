<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="id" value="${param.path}/${field.name}" />
<div id="${id}" class="repeater-container">
<c:forEach begin="1" end="${field.minOccurs == 0 ? 1 : field.minOccurs}" var="idx">
	<jsp:include page="instance.jsp">
		<jsp:param name="path" value="${id}[${idx - 1}]" />
	</jsp:include>
</c:forEach>
</div>