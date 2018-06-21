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

<form:form action="volume/user/edit.do" modelAttribute="volumeForm">

	<form:hidden path="id"/>
	<form:hidden path="userId"/>
	
	<acme:textbox code="volume.title" path="title"/>
	<br/>
	
	<acme:textarea code="volume.description" path="description"/>
	<br/>
	
	<acme:textbox code="volume.year" path="year" placeholder="yyyy"/>
	<br/>
	
	<jstl:if test="${volumeForm.id != 0}">
		<a href="newspaper/user/listAddNewspapers.do?volumeId=${volumeForm.id}"><spring:message code="volume.addNewspapers"/></a>
		<br/>
	</jstl:if>
	
	<acme:submit name="save" code="volume.save"/>
	&nbsp;
	<acme:cancel url="volume/user/list.do" code="volume.back"/>
	
</form:form>