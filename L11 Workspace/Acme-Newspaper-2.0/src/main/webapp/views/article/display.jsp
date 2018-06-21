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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:forEach var="picture" items="${pictures}">
	<img src="<jstl:out value="${picture}"/>" width="450" height="174">
	<br />
</jstl:forEach>

<h2>
	<b><spring:message code="article.title" />:&nbsp;</b>
	<jstl:out value="${article.title}" />
</h2>

<h3>
	<b><spring:message code="article.summary" />:&nbsp;</b>
	<jstl:out value="${article.summary}" />
</h3>

<spring:message var="patternDate" code="newspaper.pattern.date" />
<b><spring:message code="article.publicationMoment" />:&nbsp;</b>
<fmt:formatDate value="${article.publicationMoment}"
	pattern="${patternDate}" />
<br />

<b><spring:message code="article.writer" />:&nbsp;</b>
<a href="user/display.do?userId=${article.writer.id}"> <jstl:out
		value="${article.writer.name} ${article.writer.surname}" /></a>
<br />

<b><spring:message code="article.body" />:&nbsp;</b>
<jstl:out value="${article.body}" />
<br />

<b><spring:message code="article.newspaper" />:&nbsp;</b>
<a href="newspaper/display.do?newspaperId=${article.newspaper.id}">
	<jstl:out value="${article.newspaper.title}" />
</a>
<br />

<security:authorize access="hasRole('USER')">
	<security:authentication property="principal" var="loggedactor"/>
	<jstl:if test="${article.isFinal == true and article.newspaper.publicationDate lt date and article.writer.userAccount.id eq loggedactor.id}">
		<a href="followUp/user/create.do?articleId=${article.id}"> <spring:message
				code="article.createFollowUp" /></a>
	</jstl:if>
</security:authorize>

<jstl:if test="${advertisement!=null}">
	<a href="${advertisement.page}"> <img src="${advertisement.banner}"/></a>
</jstl:if>

