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

<form:form action="newspaper/user/edit.do" modelAttribute="newspaperForm">

	<form:hidden path="id"/>
	
	<acme:textbox code="newspaper.title" path="title"/>
	<br/>
	
	<acme:textarea code="newspaper.description" path="description"/>
	<br/>
	
	<acme:textbox code="newspaper.publicationDate" path="publicationDate" placeholder="dd/MM/yyyy"/>
	<br/>
	
	<acme:textbox code="newspaper.picture" path="picture"/>
	<br/>
	
	<acme:checkbox code="newspaper.isPrivate" path="isPrivate"/>
	<br/>
	
	<acme:submit name="save" code="newspaper.save"/>
	&nbsp;
	<acme:cancel url="newspaper/user/list.do" code="newspaper.back"/>
	
</form:form>