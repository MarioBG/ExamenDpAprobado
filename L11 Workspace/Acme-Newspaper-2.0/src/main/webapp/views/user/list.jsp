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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- displaying grid -->

<h3>
	<jstl:choose>
		<jstl:when test="${requestURI == 'user/list.do'  }">
			<spring:message code="user.system"/>
		</jstl:when>
		<jstl:when test="${requestURI == 'user/user/list-followers.do'  }">
			<spring:message code="user.followers"/>
		</jstl:when>
		<jstl:when test="${requestURI == 'user/user/list-followed.do'  }">
			<spring:message code="user.followed"/>
		</jstl:when>
	</jstl:choose>
</h3>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="users" requestURI="${requestURI }" id="row">

	<security:authorize access="hasRole('USER')">
		<jstl:if test="${requestURI != 'user/user/list-followers.do'}">
			<display:column>
				<jstl:choose>
					<jstl:when test="${principal.followed.contains(row)}">
						<a href="user/user/unfollow.do?userId=${row.id}"><spring:message code="user.unfollow"/></a>
					</jstl:when>
					<jstl:otherwise>
						<a href="user/user/follow.do?userId=${row.id}"><spring:message code="user.follow"/></a>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
		</jstl:if>
	</security:authorize>

	<!-- Attributes -->

	<display:column title="${articlesHeader}">
		<a href="user/display.do?userId=${row.id}"> <spring:message
				code="user.display" />
		</a>
	</display:column>

	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

</display:table>

<spring:message var="backValue" code="newspaper.back" />
<input type="button" name="back" value="${backValue}"
	onclick="javascript: relativeRedir('welcome/index.do');" />





