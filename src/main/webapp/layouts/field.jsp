<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${field.attribute}">
		<c:set var="id" value="${param.path}@${field.name}" />
		<jsp:include page="instance.jsp">
			<jsp:param name="path" value="${id}" />
		</jsp:include>
	</c:when>
	<c:otherwise>
		<c:set var="id" value="${param.path}/${field.name}" />
			<div id="${id}" class="repeater-container">
				<c:forEach begin="1" end="${field.minOccurs == 0 ? 1 : field.minOccurs}" var="idx">
					<jsp:include page="instance.jsp">
						<jsp:param name="path" value="${id}[${idx - 1}]" />
					</jsp:include>
				</c:forEach>
			</div>
	</c:otherwise>
</c:choose>
<c:if test="${!empty field.attributes}">
	<c:set var="path" value="${param.path}/${field.name}" />
	<c:forEach items="${field.attributes}" var="attr">
		<c:set var="field" value="${attr}" scope="request" />
		<jsp:include page="field.jsp">
			<jsp:param name="path" value="${path}" />
		</jsp:include>
	</c:forEach>
</c:if>
