<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${field.attribute}">
		<%-- this field is an attribute --%>
		<c:set var="id" value="${param.path}@${field.name}" />
		<jsp:include page="instance.jsp">
			<jsp:param name="path" value="${id}" />
		</jsp:include>
	</c:when>
	<c:otherwise>
		<%-- not an attribute --%>
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
