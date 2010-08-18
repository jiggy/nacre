<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div style="border: thin solid black; margin-left: 5px; padding: 5px;">
<c:choose>
	<c:when test="${field.fieldType eq 'SimpleType'}">
		<jsp:include page="simpleType.jsp" />
	</c:when>
	<c:otherwise>
		<jsp:include page="complexType.jsp" />
	</c:otherwise>
</c:choose>
</div>