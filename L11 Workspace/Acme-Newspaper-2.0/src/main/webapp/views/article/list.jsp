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

<script>
	function searchByKeyword(e) {
		if (e.keyCode == 13) {
			var keyword = document.getElementById("keyword").value;
			window.location.assign("article/list.do?keyword=" + keyword);
			return false;
		}
	}
</script>

<jstl:if test="${requestURI == 'article/list.do' }">
	<input type="text" id="keyword"
		placeholder="<spring:message code="article.search"/>"
		onkeypress="searchByKeyword(event)" />
</jstl:if>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="article" requestURI="${requestURI }" id="row" defaultsort="3"
	defaultorder="descending">

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="article/admin/delete.do?articleId=${row.id }"> <spring:message
					code="article.delete" /></a>
		</display:column>
	</security:authorize>

	<jstl:if test="${requestURI == 'article/user/list.do'}">
		<display:column>
			<jstl:if test="${row.isFinal == false}">
				<a href="article/user/edit.do?articleId=${row.id}"> <spring:message
						code="article.edit" /></a>
			</jstl:if>
		</display:column>
	</jstl:if>

	<display:column>
		<a href="article/display.do?articleId=${row.id }"> <spring:message
				code="article.display" /></a>
	</display:column>

	<spring:message code="article.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader }"
		sortable="true" />

	<spring:message code="article.format.date" var="formatDate" />
	<spring:message code="article.publicationMoment"
		var="publicationMomentHeader" />
	<display:column property="publicationMoment"
		title="${publicationMomentHeader }" sortable="true"
		format="${formatDate}" />

	<spring:message code="article.isFinal" var="isFinalHeader" />
	<display:column title="${ifFinalHeader}" >
		<jstl:if test="${row.isFinal == true}">
			<spring:message code="article.yes"/>
		</jstl:if>
		<jstl:if test="${row.isFinal == false}">
			<spring:message code="article.no"/>
		</jstl:if>
	</display:column>

</display:table>

<a href="welcome/index.do">&laquo; <spring:message code="terms.back"/></a>