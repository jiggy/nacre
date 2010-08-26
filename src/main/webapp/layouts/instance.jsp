<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="field-box row">
<c:choose>
	<c:when test="${field.fieldType eq 'SimpleType'}">
		<jsp:include page="simpleType.jsp">
			<jsp:param name="path" value="${param.path}" />
		</jsp:include>
	</c:when>
	<c:otherwise>
		<jsp:include page="complexType.jsp">
			<jsp:param name="path" value="${param.path}" />
		</jsp:include>
	</c:otherwise>
</c:choose>
</div>
