<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="col field-label">
<label for="${field.name}">${!empty field.decoration && !empty field.decoration.label ? field.decoration.label : field.name}${field.required ? "*" : ""}</label></div>
<div class="col field-input"><jsp:include page="${field.baseType}.jsp" /></div>
<div class="simpleContent-controls col"><c:if test="${field.maxOccurs gt 1}">
	<button class="replicate-plus" title="Add a ${field.name}" value="${id}">+</button>
	<button class="replicate-minus" title="Remove this ${field.name}">-</button>
</c:if>
</div>
<script type="text/javascript">
rules['${field.name}'] = {
	required:${field.required ? "true" : "false"}
	<c:if test="${!empty field.minLength}">,minlength:${field.minLength}</c:if>
	<c:if test="${!empty field.maxLength}">,maxlength:${field.maxLength}</c:if>
	<c:if test="${!empty field.length}">,maxlength:${field.length},minlength:${field.length}</c:if>
	<c:if test="${!empty field.minInclusive}">,min:${field.minInclusive}</c:if>
	<c:if test="${!empty field.maxInclusive}">,max:${field.maxInclusive}</c:if>
	<c:if test="${!empty field.pattern}">,pattern:/${field.pattern}/</c:if>
};
</script>