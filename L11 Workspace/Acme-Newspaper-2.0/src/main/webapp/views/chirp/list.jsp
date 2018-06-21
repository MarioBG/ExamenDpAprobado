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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- displaying grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="chirps" requestURI="${requestURI }" id="row">

	<!-- Attributes -->

	<spring:message code="chirp.showUser" var="userHeader" />
	<display:column title="${userHeader}">
		<a href="user/display.do?userId=${row.user.id}">
			<spring:message code="user.display"/>
		</a>
	</display:column>

	<spring:message code="chirp.title" var="nameHeader" />
	<display:column property="title" title="${nameHeader}" sortable="true" />

	<spring:message code="chirp.description" var="emailHeader" />
	<display:column property="description" title="${emailHeader}" sortable="true" />
	
	<security:authorize ifAllGranted="ADMIN">
	<spring:message code="chirp.delChirp" var="deleteHeader"/>
		<display:column title="${deleteHeader}">
			<a href="chirp/admin/delete.do?chirpId=${row.id}">
				<spring:message code="chirp.delete"/>
			</a>
		</display:column>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('USER')">
	<p><a href="chirp/user/create.do"><spring:message code="chirp.publish"/></a></p>
</security:authorize>

<a href="welcome/index.do">&laquo; <spring:message code="terms.back"/></a>





