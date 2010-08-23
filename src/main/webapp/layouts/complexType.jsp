<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="complexContent-box">
<div class="complexContent-header row">
	<div class="complexContent-label col"><strong>${field.name}</strong></div>
	<div class="complexContent-controls col"><c:if test="${field.maxOccurs gt 1}">
		<button class="replicate-plus" title="Add a ${field.name}" value="${id}">+</button>
		<button class="replicate-minus" title="Remove this ${field.name}">-</button>
	</c:if>
	</div>
</div>
<c:forEach items="${field.fields}" var="child">
	<c:set var="field" value="${child}" scope="request" />
	<jsp:include page="field.jsp" />
</c:forEach>
</div>