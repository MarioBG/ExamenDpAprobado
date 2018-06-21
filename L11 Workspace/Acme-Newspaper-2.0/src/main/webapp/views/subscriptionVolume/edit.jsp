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

<form:form action="subscriptionVolume/customer/edit.do"
	modelAttribute="subscriptionVolumeForm">

	<form:hidden path="id" />
	<form:hidden path="customerId" />
	<form:hidden path="volumeId" />

	<b><label><spring:message code="subscriptionVolume.volume" />:&nbsp;</label></b>
	<input readonly="readonly" value="<jstl:out value="${volume.title}"/>" />

	<fieldset>
		<legend>
			<spring:message code="subscriptionVolume.creditCard" />
		</legend>

		<acme:textbox code="subscriptionVolume.creditCard.holder"
			path="holder" />
		<br />

		<acme:textbox code="subscriptionVolume.creditCard.brand" path="brand" />
		<br />

		<acme:textbox code="subscriptionVolume.creditCard.number"
			path="number" />
		<br />

		<acme:textbox code="subscriptionVolume.creditCard.expirationMonth"
			path="expirationMonth" placeholder="MM" />
		<br />



		<acme:textbox code="subscriptionVolume.creditCard.expirationYear"
			path="expirationYear" placeholder="YYYY" />
		<br />

		<acme:textbox code="subscriptionVolume.creditCard.cvv" path="cvv"
			placeholder="###" />
		<br />

	</fieldset>

	<acme:submit name="save" code="subscriptionVolume.save" />
	&nbsp;
	<acme:cancel url="volume/list.do" code="volume.back" />

</form:form>