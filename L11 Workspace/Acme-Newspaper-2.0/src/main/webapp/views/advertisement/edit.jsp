
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="advertisement/agent/edit.do" modelAttribute="advertisementForm">

	<form:hidden path="id" />
	<form:hidden path="agentId" />
	<form:hidden path="newspaperId" />
	
	<form:label path="title">
		<spring:message code="advertisement.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br/>

	<form:label path="banner">
		<spring:message code="advertisement.bannerURL" />:
	</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br/>
	
	
	<form:label path="page">
		<spring:message code="advertisement.infoPageLink" />:
	</form:label>
	<form:input path="page" />
	<form:errors cssClass="error" path="page" />
	<br/>

	<%-- <form:label path="creditCard">
		<spring:message code="advertisement.creditCard"/>:
	</form:label>
	<form:select path="creditCard">
		<form:option value="0">----</form:option>
		<form:options
			items="${creditCards}"
			itemLabel="number"
			itemValue="id"
		/>
	</form:select>
	<form:errors cssClass="error" path="creditCard"/>
	Si la lista de tarjetas esta vacia, debemos crear una nueva
	<jstl:if test="${empty creditCards}">
		<a href="creditCard/manager/create.do"><spring:message code="advertisement.create"/></a>
	</jstl:if> --%>
	<fieldset>
	<legend><spring:message code="advertisement.introduceCreditCard"/></legend><br/>
	
	<form:label path="holder">
		<spring:message code="creditCard.holderName" />:
	</form:label>
	<form:input path="holder" />
	<form:errors cssClass="error" path="holder" />
	<br/>
	
	<form:label path="brand">
		<spring:message code="creditCard.brandName" />:
	</form:label>
	<form:input path="brand" />
	<form:errors cssClass="error" path="brand" />
	<br/>

	<form:label path="number">
		<spring:message code="creditCard.number"/>:
	</form:label>
	<form:input path="number" />
	<form:errors cssClass="error" path="number"/>
	<br/>

	<form:label path="expirationMonth">
		<spring:message code="creditCard.expirationMonth" />:
	</form:label>
	<form:input path="expirationMonth" />
	<form:errors cssClass="error" path="expirationMonth" />
	<br/>

	<form:label path="expirationYear">
		<spring:message code="creditCard.expirationYear" />:
	</form:label>
	<form:input path="expirationYear" />
	<form:errors cssClass="error" path="expirationYear" />
	<br/>

	<form:label path="cvv">
		<spring:message code="creditCard.CVV" />:
	</form:label>
	<form:input path="cvv" />
	<form:errors cssClass="error" path="cvv" />
	<br/>
	</fieldset>

	<br/>
	
	<input type="submit" name="save"
		value="<spring:message code="advertisement.save" />" />&nbsp; 

	<%-- SOLO SE PUEDE ELIMINAR SI ESTAMOS EDITANDO, NO SI ESTAMOS CREANDO --%>
	<jstl:if test="${advertisement.id != 0}">
	<input type="submit" name="delete"
		value="<spring:message code="advertisement.delete" />"
		onclick="return confirm('<spring:message code="advertisement.confirm.delete" />')" />&nbsp; 
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="advertisement.cancel" />"
		onclick="javascript: relativeRedir('advertisement/agent/list.do');" />

</form:form>
