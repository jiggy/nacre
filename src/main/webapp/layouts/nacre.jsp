<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="cp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New ${param.type}</title>
<link rel="stylesheet" type="text/css" href="${cp}/css/jquery-ui-1.8.4.custom.css" />
<link rel="stylesheet" type="text/css" href="${cp}/css/nacre.css" />
<script type="text/javascript" src="${cp}/js/lib/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${cp}/js/lib/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript" src="${cp}/js/nacre.js"></script>
</head>
<body>
<c:forEach items="${form.actions}" var="action">
	<a href="${action.uri}">${action.label}</a><br />
</c:forEach>
<c:forEach items="${form.form.fields}" var="field">
	<c:set var="field" value="${field}" scope="request" />
	<c:set var="path" value="/${param.type}" scope="request" />
	<jsp:include page="field.jsp" />
</c:forEach>
</body>
</html>