<%-- edit.jsp de Application --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="followUp/user/edit.do" modelAttribute="followUpForm">

	<form:hidden path="id"/>
	<form:hidden path="articleId"/>
	<form:hidden path="publicationMoment"/>
	
	<acme:textbox code="followUp.title" path="title"/>
	<br/>
	
	<acme:textarea code="followUp.summary" path="summary"/>
	<br/>
	
	<acme:textarea code="followUp.text" path="text"/>
	<br/>
	
	<acme:textarea code="followUp.pictures" path="pictures"/>
	<spring:message code="followUp.eachPicture"/>
	<br/>
	
	<acme:submit name="save" code="followUp.save"/>
	&nbsp;
	<acme:cancel url="article/display.do?articleId=${followUpForm.articleId}" code="followUp.back"/>
	
</form:form>