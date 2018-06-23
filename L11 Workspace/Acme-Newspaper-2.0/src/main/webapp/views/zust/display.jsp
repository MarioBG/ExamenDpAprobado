<%--
 * display.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h3>
	<b><spring:message code="zust.title" />:&nbsp;</b>
	<jstl:out value="${zust.title}" />
</h3>

<p>
	<b><spring:message code="zust.ticker" />:&nbsp;</b>
	<jstl:out value="${zust.ticker}" />
</p>

<p>
	<b><spring:message code="zust.gauge" />:&nbsp;</b>
	<jstl:out value="${zust.gauge}" />
<p/>

<spring:message var="patternDate" code="zust.pattern.date" />
<b><spring:message code="zust.moment" />:&nbsp;</b>
<fmt:formatDate value="${zust.moment}"
	pattern="${patternDate}" />
<br />
<br />

<b><spring:message code="zust.address" />:&nbsp;</b>
<jstl:out value="${zust.address}" />
<br />

<acme:cancel code="zust.back" url="zust/list.do" />

