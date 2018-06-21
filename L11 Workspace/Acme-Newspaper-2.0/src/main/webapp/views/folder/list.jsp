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

<display:table name="folders" id="row" pagesize="6" class="displaytag" requestURI="folder/list.do">
	
	<display:column>
		<jstl:if test="${row.predefined == false}">
			<a href="folder/edit.do?folderId=${row.id}"><spring:message code="folder.edit"/></a>
		</jstl:if>
	</display:column>
	
	
	<display:column>
		<jstl:if test="${!row.messages.isEmpty()}">
			<a href="message/list.do?folderId=${row.id}"><spring:message code="folder.listMessage"/></a>
		</jstl:if>
	</display:column>
	
	<display:column>
		<jstl:if test="${!row.children.isEmpty()}">
			<a href="folder/list.do?folderId=${row.id}"><spring:message code="folder.listFolder"/></a>
		</jstl:if>
	</display:column>
	
	<spring:message var="nameHeader" code="folder.name"/>
	<display:column property="name" title="${nameHeader}" />
		
</display:table>

<a href="folder/create.do"><spring:message code="folder.create"/></a>
<br/>
<a href="message/create.do"><spring:message code="message.create"/></a>
<br/>
<security:authorize access="hasRole('ADMIN')">
	<a href="message/admin/create-notification.do"><spring:message code="message.createNotification"/></a>
	<br/>
</security:authorize>
<br/>
<jstl:set var="parentFolder" value="${folders.toArray()[0].parent}"/>
<jstl:choose>
	<jstl:when test="${parentFolder != null && parentFolder.parent != null}">
		<input type="button" name="cancel" value="<spring:message code="folder.cancel" />" onclick="javascript: relativeRedir('folder/list.do?folderId=${parentFolder.id}')" />
	</jstl:when>
	<jstl:when test="${parentFolder != null && parentFolder.parent == null}">
		<input type="button" name="cancel" value="<spring:message code="folder.cancel" />" onclick="javascript: relativeRedir('folder/list.do')" />
	</jstl:when>
	<jstl:when test="${parentFolder == null}">
		
	</jstl:when>
</jstl:choose>