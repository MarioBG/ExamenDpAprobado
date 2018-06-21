

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

<form:form action="message/edit.do" modelAttribute="messageForm">

	<form:hidden path="id" />
	<form:hidden path="senderId" />
	<jstl:choose>
		<jstl:when test="${messageForm.id == 0}">
			<form:hidden path="folderId" />
		</jstl:when>
		<jstl:otherwise>
			<form:hidden path="recipientId"/>
			<form:hidden path="subject"/>
			<form:hidden path="body"/>
			<form:hidden path="priority" />
		</jstl:otherwise>
	</jstl:choose>
	<form:hidden path="moment" />

	<jstl:choose>
		<jstl:when test="${messageForm.id == 0}">
			<acme:select items="${actors}" itemLabel="name"
				code="message.recipient" path="recipientId" />
		</jstl:when>
		<jstl:otherwise>
			<b><label> <spring:message code="message.recipient" />:&nbsp;
			</label></b>
			<input value="${recipientName}" readonly="readonly" />
			<br />

			<b><label> <spring:message code="message.sender" />:&nbsp;
			</label></b>
			<input value="${senderName}" readonly="readonly" />
			<br />

			<b><label> <spring:message code="message.moment" />:&nbsp;
			</label></b>
			<spring:message code="message.pattern.date" var="patternDate" />
			<fmt:formatDate value="${messageForm.moment}"
				pattern="${patternDate}" />
			<br />
		</jstl:otherwise>
	</jstl:choose>

	<jstl:choose>
		<jstl:when test="${messageForm.id == 0}">
			<acme:textbox code="message.subject" path="subject" />
		</jstl:when>
		<jstl:otherwise>
			<b><label> <spring:message code="message.subject" />:&nbsp;
			</label></b>
			<input value="${messageForm.subject}" readonly="readonly" />
			<br />
		</jstl:otherwise>
	</jstl:choose>

	<jstl:choose>
		<jstl:when test="${messageForm.id == 0}">
			<acme:textarea code="message.body" path="body" />
		</jstl:when>
		<jstl:otherwise>
			<b><label> <spring:message code="message.body" />:&nbsp;
			</label></b>
			<textarea readonly="readonly">
				<jstl:out value="${messageForm.body}" />
			</textarea>
			<br />
		</jstl:otherwise>
	</jstl:choose>

	<jstl:choose>
		<jstl:when test="${messageForm.id == 0}">
			<b><form:label path="priority">
					<spring:message code="message.priority" />:&nbsp;</form:label></b>
			<form:select path="priority">
				<form:options items="${priorities}" />
			</form:select>
			<br />
		</jstl:when>
		<jstl:otherwise>
			<b><label> <spring:message code="message.priority" />:&nbsp;
			</label></b>
			<input value="${messageForm.priority}" readonly="readonly" />
			<br />
		</jstl:otherwise>
	</jstl:choose>

	<jstl:if test="${messageForm.id != 0}">
		<acme:selectObligatory items="${folders}" itemLabel="name"
			code="message.folder" path="folderId" />
	</jstl:if>

	<jstl:choose>
		<jstl:when test="${messageForm.id == 0}">
			<acme:submit name="save" code="message.send" />
			&nbsp;
		</jstl:when>
		<jstl:when test="${messageForm.id != 0}">
			<acme:submit name="save" code="message.save" />
			&nbsp;
		</jstl:when>
	</jstl:choose>
	<jstl:if test="${messageForm.id != 0}">
		<acme:submit name="delete" code="message.delete" />
		&nbsp;
	</jstl:if>
	<acme:cancel url="folder/list.do" code="message.cancel" />

</form:form>