<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="thisField" value="${field}" />
<c:choose>
	<c:when test="${field.fieldType eq 'SimpleType'}">
		<fieldset class="instance field-box row">
			<jsp:include page="simpleType.jsp">
				<jsp:param name="path" value="${param.path}" />
			</jsp:include>
		</fieldset>
		<c:if test="${!empty thisField.attributes}">
			<%-- field has attributes --%>
			<c:forEach items="${thisField.attributes}" var="attr">
				<c:set var="field" value="${attr}" scope="request" />
				<jsp:include page="field.jsp">
					<jsp:param name="path" value="${param.path}" />
				</jsp:include>
			</c:forEach>
		</c:if>
	</c:when>
	<c:otherwise>
		<fieldset class="instance field-box row">
			<jsp:include page="complexType.jsp">
				<jsp:param name="path" value="${param.path}" />
			</jsp:include>
			<c:if test="${!empty thisField.attributes}">
				<%-- field has attributes --%>
				<c:forEach items="${thisField.attributes}" var="attr">
					<c:set var="field" value="${attr}" scope="request" />
					<jsp:include page="field.jsp">
						<jsp:param name="path" value="${param.path}" />
					</jsp:include>
				</c:forEach>
			</c:if>
		</fieldset>
	</c:otherwise>
</c:choose>
