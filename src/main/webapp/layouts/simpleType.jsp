<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- simpleType name: ${field.name}, id: ${id} -->
<div class="col field-label">${!empty field.decoration && !empty field.decoration.label ? field.decoration.label : field.name}</div>
<div class="col field-input" id="${id}-input"><jsp:include page="${field.baseType}.jsp" /></div>
<!-- /simpleType name: ${field.name}, id: ${id} -->