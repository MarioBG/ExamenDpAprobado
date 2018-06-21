<%--
 * list.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="article/user/edit.do" modelAttribute="articleForm">

	<form:hidden path="id"/>

	<acme:textbox code="article.title" path="title"/>
	<br/>
	
	<acme:textarea code="article.summary" path="summary"/>
	<br/>
	
	<acme:textarea code="article.body" path="body"/>
	<br/>
	
	<acme:textarea code="article.pictures" path="pictures"/>
	<spring:message code="article.eachPicture"/>
	<br/>
	
	<acme:selectObligatory items="${newspapers }" itemLabel="title" code="article.newspaper" path="newspaperId"/>
	<br/>
	
	<%-- <jstl:if test="${articleForm.isFinal == false }"> --%>
		<acme:checkbox code="article.isFinal" path="isFinal"/>
	<%-- </jstl:if> --%>
	
	<input type="button" name="cancel" 
		value="<spring:message code="article.cancel"/>"
		onclick="javascript: relativeRedir('/');"/>
	
	<security:authorize access="hasRole('USER')">
		<input type="submit" name="save" 
		value="<spring:message code="article.save"/>"/>&nbsp;
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${article.id !=0 }">
		<input type="submit" name="delete" 
		value="<spring:message code="article.delete"/>"
		onclick="return confirm('<spring:message code="article.confirm.delete"/>')" />&nbsp;
	</jstl:if>
	</security:authorize>
	
	
	
</form:form>