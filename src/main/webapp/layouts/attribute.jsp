<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fieldset class="attribute" id="${field.name}">
<legend>${field.name}</legend>
<input type="hidden" name="namespace" value="${field.namespace}" />
<input type="hidden" name="type" value="${field.fieldType}" />
	<%-- this field is an attribute --%>
	<input type="hidden" name="isAttribute" value="true" />
	<c:set var="id" value="${param.path}@${field.name}" />
	<jsp:include page="instance.jsp">
		<jsp:param name="path" value="${id}" />
	</jsp:include>
</fieldset>
<%-- 
field -> (name, path, namespace)
instance -> (unique id)
--%>