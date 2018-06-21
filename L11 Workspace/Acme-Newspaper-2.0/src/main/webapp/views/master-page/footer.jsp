<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
 <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme-Newspaper-2.0 Co., Inc.</b><br/>
<spring:message code="master.page.email" var="email"/>
<spring:message code="master.page.contact-info" /> <a href="mailto:${email}">${email}</a>.
<div class="data-protection">
	<spring:message code="master.page.data-protection" />
</div>
<div class="copyright-notice">
	<spring:message code="master.page.cookies" />
</div>
