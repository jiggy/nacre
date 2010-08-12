<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Form</title>
</head>
<body>
<c:forEach items="${form.fields}" var="field">
	${field.name} : ${field.fieldType} <br />
	<c:choose>
		<c:when test="${field.fieldType eq 'SimpleType'}">
			<c:if test="${field.baseType eq 'string'}">
				${field.baseType}: <input type="text" name="${field.name}" maxlength="${field.maxLength}" /><br />
			</c:if>
		</c:when>
		<c:otherwise>
			<c:forEach items="${field.fields}" var="child">
				&nbsp;&nbsp;&nbsp;${child.name} : ${child.fieldType} <br />
			</c:forEach>
		</c:otherwise>
	</c:choose>
</c:forEach>
</body>
</html>