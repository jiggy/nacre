<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<input id="datefield" type="text" name="${field.name}" maxlength="${field.maxLength}" />
<script type="text/javascript">
	$("#datefield").datepicker();
</script>
