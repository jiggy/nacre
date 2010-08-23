<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- field name: ${field.name}, id: ${id} -->
<c:set var="id" value="${path}/${field.name}" scope="request" />
<div class="field-box row" id="${id}">
<c:choose>
	<c:when test="${field.fieldType eq 'SimpleType'}">
		<jsp:include page="simpleType.jsp" />
	</c:when>
	<c:otherwise>
		<c:set var="path" value="${path}/${field.name}" scope="request" />
		<jsp:include page="complexType.jsp" />
	</c:otherwise>
</c:choose>
</div>
<!-- /field name: ${field.name}, id: ${id} -->