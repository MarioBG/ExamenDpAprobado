<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<p><spring:message code="welcome.greeting.prefix" />
<jstl:choose>
	<jstl:when test="${name.equals('anonymous user')}">
		<spring:message code="welcome.greeting.anonymousUser"/>
	</jstl:when>
	<jstl:otherwise>
		<jstl:out value="${name}"/>
	</jstl:otherwise>
</jstl:choose>
<spring:message code="welcome.greeting.suffix" /></p>

<spring:message code="welcome.pattern.date" var="patternDate"/>
<p><spring:message code="welcome.greeting.current.time" /><fmt:formatDate value="${moment}" pattern="${patternDate}"/></p> 
