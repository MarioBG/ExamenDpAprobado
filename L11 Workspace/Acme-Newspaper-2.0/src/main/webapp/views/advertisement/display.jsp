<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<ul style="list-style-type: disc">

	<li><b><spring:message code="advertisement.newspaper"></spring:message>:</b> <jstl:out
			value="${advertisement.newspaper.title}" /></li>

	<li><b><spring:message code="advertisement.bannerURL"></spring:message>:</b> <jstl:out
			value="${advertisement.banner}" /></li>
	<li><img src="${advertisement.banner}"/></li>
	
	<li><b><spring:message code="advertisement.infoPageLink"></spring:message>:</b> <jstl:out
			value="${advertisement.page}" /></li>
			
	<li><b><spring:message code="advertisement.creditCard"></spring:message>:</b>
	<ul>
	
	<li><b><spring:message code="creditCard.holderName"></spring:message>:</b> <jstl:out
			value="${advertisement.creditCard.holder}" /></li>
	<li><b><spring:message code="creditCard.brandName"></spring:message>:</b> <jstl:out
			value="${advertisement.creditCard.brand}" /></li>
	<li><b><spring:message code="creditCard.number"></spring:message>:</b> <jstl:out
			value="${advertisement.creditCard.number}" /></li>
	<li><b><spring:message code="creditCard.expirationYear"></spring:message>:</b> <jstl:out
			value="${advertisement.creditCard.expirationYear}" /></li>
	<li><b><spring:message code="creditCard.expirationMonth"></spring:message>:</b> <jstl:out
			value="${advertisement.creditCard.expirationMonth}" /></li>
	<li><b><spring:message code="creditCard.CVV"></spring:message>:</b> <jstl:out
			value="${advertisement.creditCard.cvv}" /></li>
	
	
	</ul>
	</li>
	
</ul>

<input type="button" name="back"
	value="<spring:message code="ms.back" />"
	onclick="javascript: relativeRedir('advertisement/agent/list.do')" />