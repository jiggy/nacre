<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- simpleType name: ${field.name}, id: ${id} -->
<div class="col field-label">${!empty field.decoration && !empty field.decoration.label ? field.decoration.label : field.name}</div>
<div class="col field-input" id="${id}-input"><jsp:include page="${field.baseType}.jsp" /></div>
<div class="simpleContent-controls col"><c:if test="${field.maxOccurs gt 1}">
	<button class="replicate-plus" title="Add a ${field.name}" value="${id}">+</button>
	<button class="replicate-minus" title="Remove this ${field.name}">-</button>
</c:if>
</div>
<!-- /simpleType name: ${field.name}, id: ${id} -->