<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="thisField" value="${field}" />
<fieldset class="instance">
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
<c:if test="${!empty thisField.attributes}">
	<%-- field has attributes --%>
	<c:forEach items="${thisField.attributes}" var="attr">
		<c:set var="field" value="${attr}" scope="request" />
		<jsp:include page="attribute.jsp">
			<jsp:param name="path" value="${param.path}" />
		</jsp:include>
	</c:forEach>
</c:if>
</fieldset>
