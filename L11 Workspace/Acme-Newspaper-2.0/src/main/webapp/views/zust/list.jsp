
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



<!-- ZUST QUE AÚN SE PUEDEN EDITAR -->
<h2>
	<spring:message code="zust.zustToEdit" />
</h2>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="zustToEdit" requestURI="zut/admin/list.do" id="row"
	defaultsort="3" defaultorder="descending">

	<spring:message code="zust.title" var="titleHeader" />
	<display:column title="${titleHeader }" class="gauge${row.gauge}">
		<jstl:out value="${row.title}" />
	</display:column>

	<spring:message code="zust.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<spring:message code="zust.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />

	<spring:message code="zust.format.date" var="formatDate" />
	<spring:message code="zust.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader }"
		sortable="true" format="${formatDate}" />

	<spring:message code="zust.isFinal" var="isFinalHeader" />
	<display:column title="${isFinalHeader}">
		<jstl:if test="${row.isFinal == true}">
			<spring:message code="zust.yes" />
		</jstl:if>
		<jstl:if test="${row.isFinal == false}">
			<spring:message code="zust.no" />
		</jstl:if>
	</display:column>

	<display:column>
		<jstl:if test="${row.isFinal == false}">
			<security:authorize access="hasRole('ADMIN')">
				<a href="zust/admin/edit.do?zustId=${row.id}"> <spring:message
						code="zust.edit" /></a>
			</security:authorize>
		</jstl:if>
	</display:column>

</display:table>


<!-- TODOS MIS ZUST -->
<h2>
	<spring:message code="zust.zustList" />
</h2>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="zust" requestURI="zut/admin/list.do" id="row" defaultsort="3"
	defaultorder="descending">

	<spring:message code="zust.title" var="titleHeader" />
	<display:column title="${titleHeader }" class="gauge${row.gauge}">
		<jstl:out value="${row.title}" />
	</display:column>

	<spring:message code="zust.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<spring:message code="zust.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />

	<spring:message code="zust.format.date" var="formatDate" />
	<spring:message code="zust.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader }"
		sortable="true" format="${formatDate}" />

	<spring:message code="zust.isFinal" var="isFinalHeader" />
	<display:column title="${isFinalHeader}">
		<jstl:if test="${row.isFinal == true}">
			<spring:message code="zust.yes" />
		</jstl:if>
		<jstl:if test="${row.isFinal == false}">
			<spring:message code="zust.no" />
		</jstl:if>
	</display:column>

	<spring:message code="zust.newspaper" var="newspaperHeader" />
	<display:column property="newspaper.title" title="${newspaperHeader}" />

</display:table>

<a href="welcome/index.do">&laquo; <spring:message code="terms.back" /></a>