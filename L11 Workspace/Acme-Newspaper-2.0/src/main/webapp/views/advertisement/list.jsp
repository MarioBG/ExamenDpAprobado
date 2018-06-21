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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="advertisements" requestURI="${requestURI}"
	id="row">

	<!-- Action links -->
	<!-- Attributes -->

	<%-- <display:column>
		<a href="advertisement/agent/edit.do?advertisementId=${row.id}"><spring:message
				code="advertisement.edit" /></a>
	</display:column> --%>

	<%-- <spring:message code="advertisement.bannerURL" var="bannerURL" />
	<display:column property="bannerURL" title="${bannerURL}"
		sortable="false" /> --%>

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="advertisement/admin/delete.do?advertisementId=${row.id }"> <spring:message
					code="article.delete" /></a>
		</display:column>
	</security:authorize>

	<spring:message code="advertisement.title" var="page" />
	<display:column property="title" title="${page}"
		sortable="false" />
	
	<spring:message code="advertisement.infoPageLink" var="page" />
	<display:column property="page" title="${page}"
		sortable="false" />

	<spring:message code="advertisement.creditCard" var="creditCard" />
	<display:column property="creditCard.brand" title="${creditCard}"
		sortable="false" />
		
		<spring:message code="advertisement.newspaper" var="newspaper" />
	<display:column property="newspaper.title" title="${newspaper}"
		sortable="false" />
		
	<display:column>
			<a href="advertisement/agent/display.do?advertisementId=<jstl:out value="${row.getId()}"/>"><spring:message code="newspaper.display" /></a><br/>
	</display:column>
</display:table>


