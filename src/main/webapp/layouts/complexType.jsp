<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- div class="complexContent-controls col"><c:if test="${field.maxOccurs gt 1}">
	<button class="replicate-plus" title="Add a ${field.name}" value="${param.path}">+</button>
	<button class="replicate-minus" title="Remove this ${field.name}">-</button>
</c:if></div>--%>
<c:choose>
	<c:when test="${field.combinationType eq 'or'}">
		<div class="choice-selector">
			<c:forEach items="${field.fields}" var="child">
				<a href="#">${child.name}</a> 
			</c:forEach>
		</div>
	</c:when>
	<c:otherwise>
		<c:forEach items="${field.fields}" var="child">
			<c:set var="field" value="${child}" scope="request" />
			<jsp:include page="field.jsp">
				<jsp:param name="path" value="${param.path}" />
			</jsp:include>
		</c:forEach>
	</c:otherwise>
</c:choose>