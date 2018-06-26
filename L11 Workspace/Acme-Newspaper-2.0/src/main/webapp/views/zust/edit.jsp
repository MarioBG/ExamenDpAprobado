<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="zust/admin/edit.do" modelAttribute="zust">

	<form:hidden path="id" />
	<form:hidden path="ticker" />
	<form:hidden path="admin" />
	<form:hidden path="version" />

	<acme:textbox code="zust.title" path="title" />
	<br />

	<acme:textarea code="zust.description" path="description" />
	<br />

	<acme:textbox code="zust.moment" path="moment" placeholder="dd/MM/yyyy" />
	<br />

	<acme:textbox code="zust.gauge" path="gauge"
		placeholder="Point (1, 2 or 3)" />
	<br />



	<acme:checkbox code="zust.isFinal" path="isFinal" />

	<input type="button" name="cancel"
		value="<spring:message code="zust.cancel"/>"
		onclick="javascript: relativeRedir('/');" />

	<security:authorize access="hasRole('ADMIN')">
		<input type="submit" name="save"
			value="<spring:message code="zust.save"/>" />&nbsp;
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${zust.id !=0 && zust.isFinal == false}">
			<input type="button" value="<spring:message code="zust.delete" />"
				onclick="javascript: window.location.assign('zust/admin/delete.do?zustId=${zust.id}')" />
		</jstl:if>
	</security:authorize>

</form:form>