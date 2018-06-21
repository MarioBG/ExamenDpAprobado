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

<form:form action="user/user/edit.do" modelAttribute="userForm">
	
	<form:hidden path="id" />
	<form:hidden path="repeatPassword" />
	<form:hidden path="termsAndConditions" />
	<form:hidden path="password" />
	<form:hidden path="username" />
	
	<acme:textbox code="user.name" path="name"/>
	<br/>
	<acme:textbox code="user.surname" path="surname"/>
	<br/>
	<acme:textbox code="user.email" path="email"/>
	<br/>
	<acme:textbox code="user.phone" path="phone"/>
	<br/>
	<acme:textbox code="user.address" path="address"/>
	<br/>
	
	<acme:submit name="save" code="user.save"/>
	<acme:submit name="cancel" code="user.cancel"/>

</form:form>
