<%--
 * edit.jsp
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

<h3><b><spring:message code="message.subject"/>:&nbsp;</b><jstl:out value="${messageDisplay.subject}"/></h3>

<b><spring:message code="message.body"/>:&nbsp;</b><jstl:out value="${messageDisplay.body}"/>
<br/>

<spring:message code="message.pattern.date" var="patternDate"/>
<b><spring:message code="message.moment"/>:&nbsp;</b><fmt:formatDate value="${messageDisplay.moment}" pattern="${patternDate}"/>
<br/>

<b><spring:message code="message.priority"/>:&nbsp;</b><jstl:out value="${messageDisplay.priority}"/>
<br/>

<b><spring:message code="message.sender"/>:&nbsp;</b><jstl:out value="${sender.name}"/>
<br/>

<b><spring:message code="message.recipient"/>:&nbsp;</b><jstl:out value="${recipient.name}"/>
<br/>

<b><spring:message code="message.folder"/>:&nbsp;</b><jstl:out value="${messageDisplay.folder.name}"/>
<br/>

<a href="message/edit.do?messageId=${messageDisplay.id}"><spring:message code="message.editMessage"/></a>
<br/>

<input type="button" name="cancel" value="<spring:message code="message.cancel" />" onclick="javascript: relativeRedir('message/list.do?folderId=${messageDisplay.folder.id}');" />