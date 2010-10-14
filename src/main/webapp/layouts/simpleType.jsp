<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col field-label">
	<label for="${field.name}">${!empty field.decoration && !empty field.decoration.label ? field.decoration.label : field.name}${field.required ? "*" : ""}</label>
</div>
<div class="col field-input">
	<fieldset>
	<input type="hidden" class="fieldid" value="${param.path}" />
	<jsp:include page="${field.baseType}.jsp">
		<jsp:param name="id" value="${param.path}" />
	</jsp:include>
	</fieldset>
</div>
<div class="simpleContent-controls col"><c:if test="${field.maxOccurs gt 1}">
	<button class="replicate-plus" title="Add a ${field.name}" value="${param.path}">+</button>
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
	<c:if test="${!empty field.minExclusive}">,minExclusive:${field.minExclusive}</c:if>
	<c:if test="${!empty field.maxExclusive}">,maxExclusive:${field.maxExclusive}</c:if>
	<c:if test="${!empty field.pattern}">,pattern:/${field.pattern}/</c:if>
	<c:if test="${field.baseType eq 'int' || field.baseType eq 'integer'}">,digits:true</c:if>
};
</script>