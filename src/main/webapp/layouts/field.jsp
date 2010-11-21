<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fieldset class="field" id="${field.name}">
<legend>${field.name}</legend>
<input type="hidden" name="namespace" value="${field.namespace}" />
<input type="hidden" name="type" value="${field.fieldType}" />
<input type="hidden" name="hasAttributes" value="${fn:length(field.attributes) > 0}" />
<c:choose>
	<c:when test="${field.attribute}">
		<%-- this field is an attribute --%>
		<input type="hidden" name="isAttribute" value="true" />
		<c:set var="id" value="${param.path}@${field.name}" />
		<jsp:include page="instance.jsp">
			<jsp:param name="path" value="${id}" />
		</jsp:include>
	</c:when>
	<c:otherwise>
		<%-- not an attribute --%>
		<input type="hidden" name="isAttribute" value="false" />
		<input type="hidden" name="minOccurs" value="${field.minOccurs}" />
		<input type="hidden" name="maxOccurs" value="${field.maxOccurs}" />
		<c:set var="id" value="${param.path}/${field.name}" />
			<c:forEach begin="1" end="${field.minOccurs == 0 ? 1 : field.minOccurs}" var="idx">
				<jsp:include page="instance.jsp">
					<jsp:param name="index" value="${idx}" />
					<jsp:param name="path" value="${id}[${idx - 1}]" />
				</jsp:include>
			</c:forEach>
	</c:otherwise>
</c:choose>
</fieldset>
<%-- 
field -> (name, path, namespace)
instance -> (unique id)
--%>