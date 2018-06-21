<%-- edit.jsp de Application --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h3>
	<b><spring:message code="followUp.title" />:&nbsp;</b>
	<jstl:out value="${followUp.title}" />
</h3>

<b><spring:message code="followUp.article" />:&nbsp;</b>
<jstl:out value="${followUp.article.title}" />
<br />

<b><spring:message code="followUp.user" />:&nbsp;</b>
<jstl:out value="${followUp.article.writer.name} ${followUp.article.writer.surname}" />
<br />

<spring:message var="patternDate" code="followUp.pattern.date" />
<b><spring:message code="followUp.publicationMoment" />:&nbsp;</b>
<fmt:formatDate value="${followUp.publicationMoment}"
	pattern="${patternDate}" />
<br />

<b><spring:message code="followUp.summary" />:&nbsp;</b>
<jstl:out value="${followUp.summary}" />
<br />

<b><spring:message code="followUp.text" />:&nbsp;</b>
<jstl:out value="${followUp.text}" />
<br />

<jstl:forEach var="picture" items="${pictures}">
	<img src="<jstl:out value="${picture}"/>" width="450" height="174">
	<br />
</jstl:forEach>

<acme:cancel url="followUp/user/list.do" code="followUp.back" />