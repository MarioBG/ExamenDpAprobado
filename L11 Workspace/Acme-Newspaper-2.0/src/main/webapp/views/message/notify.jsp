

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${actionURI}" modelAttribute="messageForm">

	<form:hidden path="id" />
	<form:hidden path="senderId" />
	<form:hidden path="recipientId" />
	<form:hidden path="moment" />

	<acme:textbox code="message.subject" path="subject" />

	<acme:textarea code="message.body" path="body" />

	<b><form:label path="priority">
			<spring:message code="message.priority" />:&nbsp;</form:label></b>
	<form:select path="priority">
		<form:options items="${priorities}" />
	</form:select>
	<br />

	<acme:submit name="notify" code="message.notify" />
	&nbsp;

	<acme:cancel url="folder/list.do" code="message.cancel" />

</form:form>