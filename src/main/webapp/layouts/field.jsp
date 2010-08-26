<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${field.attribute}">
		<c:set var="id" value="${param.path}@${field.name}" />
	</c:when>
	<c:otherwise>
		<c:set var="id" value="${param.path}/${field.name}" />
	</c:otherwise>
</c:choose>
<div id="${id}" class="repeater-container">
<c:forEach begin="1" end="${field.minOccurs == 0 ? 1 : field.minOccurs}" var="idx">
	<jsp:include page="instance.jsp">
		<jsp:param name="path" value="${id}[${idx - 1}]" />
	</jsp:include>
</c:forEach>
</div>