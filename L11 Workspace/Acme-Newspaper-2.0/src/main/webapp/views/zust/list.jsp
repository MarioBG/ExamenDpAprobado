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


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="zust" requestURI="zut/admin/list.do" id="row" defaultsort="3"
	defaultorder="descending">


	<display:column>
		<a href="zust/display.do?zustId=${row.id}"> <spring:message
				code="zust.display" /></a>
	</display:column>

	<spring:message code="zust.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader }" />

	<spring:message code="zust.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<spring:message code="zust.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />

	<spring:message code="zust.format.date" var="formatDate" />
	<spring:message code="zust.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader }"
		sortable="true" format="${formatDate}" />

	<spring:message code="zust.isFinal" var="isFinalHeader" />
	<display:column title="${ifFinalHeader}">
		<jstl:if test="${row.isFinal == true}">
			<spring:message code="zust.yes" />
		</jstl:if>
		<jstl:if test="${row.isFinal == false}">
			<spring:message code="zust.no" />
		</jstl:if>
	</display:column>

</display:table>

<a href="welcome/index.do">&laquo; <spring:message code="terms.back" /></a>