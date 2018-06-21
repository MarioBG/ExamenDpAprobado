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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="messages" id="row" pagesize="5" class="displaytag" requestURI="message/list.do">

	<display:column>
		<a href="message/edit.do?messageId=${row.id}"><spring:message code="message.edit"/></a>
	</display:column>
	
	<display:column>
		<a href="message/display.do?messageId=${row.id}"><spring:message code="message.display"/></a>
	</display:column>
	
	<spring:message var="momentHeader" code="message.moment"/>
	<spring:message var="formatDate" code="message.format.date"/>
	<display:column property="moment" title="${momentHeader}" sortable="true" format="${formatDate}"/>
	
	<spring:message var="subjectHeader" code="message.subject"/>
	<display:column property="subject" title="${subjectHeader}"/>
	
	<spring:message var="priorityHeader" code="message.priority"/>
	<display:column property="priority" title="${priorityHeader}" sortable="true"/>
	
</display:table>

<security:authorize access="isAuthenticated()">
	<a href="message/create.do"><spring:message code="message.create"/></a>
	<br/>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<a href="message/admin/create-notification.do"><spring:message code="message.createNotification"/></a>
	<br/>
</security:authorize>

<jstl:set var="parentFolder" value="${folder.parent}"/>
<jstl:choose>
	<jstl:when test="${parentFolder == null}">
		<input type="button" name="cancel" value="<spring:message code="message.cancel" />" onclick="javascript: relativeRedir('folder/list.do');" />
	</jstl:when>
	<jstl:when test="${parentFolder != null}">
		<input type="button" name="cancel" value="<spring:message code="message.cancel" />" onclick="javascript: relativeRedir('folder/list.do?folderId=${parentFolder.id}');" />
	</jstl:when>
</jstl:choose>