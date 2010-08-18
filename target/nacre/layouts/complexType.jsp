<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h4 class="complex-field-label">${field.name}</h4>
<c:forEach items="${field.fields}" var="child">
	<c:set var="field" value="${child}" scope="request" />
	<jsp:include page="field.jsp" />
</c:forEach>
