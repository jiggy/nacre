<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="col field-label">${!empty field.decoration && !empty field.decoration.label ? field.decoration.label : field.name} (${field.baseType})</div><div class="col field-input"><jsp:include page="${field.baseType}.jsp" /></div>
