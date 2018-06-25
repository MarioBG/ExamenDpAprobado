<%-- list.jsp de Application --%>

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

<script>
	function searchByKeyword(e) {
		if (e.keyCode == 13) {
			var keyword = document.getElementById("keyword").value;
			window.location.assign("newspaper/list.do?keyword=" + keyword);
			return false;
		}
	}
</script>

<h3>
	<jstl:choose>
		<jstl:when test="${requestURI == 'newspaper/list.do'  }">
			<spring:message code="newspaper.availableNewspapers" />
		</jstl:when>
		<jstl:when test="${requestURI == 'newspaper/user/list.do'  }">
			<spring:message code="newspaper.yourNewspapers" />
		</jstl:when>
		<jstl:when
			test="${requestURI == 'newspaper/user/list-nonPublished.do'  }">
			<spring:message code="newspaper.nonPublished" />
		</jstl:when>
		<jstl:when
			test="${requestURI == 'newspaper/user/listaddNewspapers.do'  }">
			<spring:message code="newspaper.listAddNewspapers" />
		</jstl:when>
		<jstl:when
			test="${requestURI == 'newspaper/customer/list.do'  }">
			<spring:message code="newspaper.subscribedNewspapers" />
		</jstl:when>
	</jstl:choose>
</h3>

<jstl:if test="${requestURI == 'newspaper/list.do'}">
	<input type="text" id="keyword"
		placeholder="<spring:message code="newspaper.search" />"
		onkeypress="searchByKeyword(event)" />
</jstl:if>

<display:table name="newspapers" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a href="newspaper/admin/delete.do?newspaperId=${row.id}"><spring:message
					code="newspaper.delete" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('USER')">
		<jstl:if test="${requestURI == 'newspaper/user/listAddNewspapers.do'}">
			<display:column>
				<jstl:choose>
					<jstl:when test="${!volume.newspapers.contains(row)}">
						<a
							href="volume/user/addNewspaper.do?newspaperId=${row.id}&volumeId=${volume.id}"><spring:message
								code="newspaper.addNewspaper" /></a>
					</jstl:when>
					<jstl:otherwise>
						<a
							href="volume/user/removeNewspaper.do?newspaperId=${row.id}&volumeId=${volume.id}"><spring:message
								code="newspaper.removeNewspaper" /></a>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('AGENT')">
			<display:column>
				<a
					href="advertisement/agent/create.do?newspaperId=${row.id}"><spring:message
						code="newspaper.createAdvertisement" /></a>
			</display:column>
	</security:authorize>

	<display:column>
		<a href="newspaper/display.do?newspaperId=${row.id}"><spring:message
				code="newspaper.display" /></a>
	</display:column>

	<spring:message var="titleHeader" code="newspaper.title" />
	<display:column property="title" title="${titleHeader}" />

	<spring:message var="publicationDateHeader"
		code="newspaper.publicationDate" />
	<spring:message var="formatDate" code="newspaper.format.date" />
	<display:column property="publicationDate"
		title="${publicationDateHeader}" format="${formatDate}"
		sortable="true" />

	<jstl:if test="${requestURI == 'newspaper/list.do' or requestURI == 'newspaper/customer/list.do'}">
		<spring:message var="publisherHeader" code="newspaper.publisher" />
		<display:column title="${publisherHeader}" sortable="true">
			<a href="user/display.do?userId=${row.publisher.id}"><jstl:out
					value="${row.publisher.name} ${row.publisher.surname}" /></a>
		</display:column>
	</jstl:if>

	<spring:message var="isPrivateHeader" code="newspaper.isPrivate" />
	<display:column title="${isPrivateHeader}" sortable="true">
		<jstl:choose>
			<jstl:when test="${row.isPrivate == true}">
				<spring:message code="newspaper.yes" />
			</jstl:when>
			<jstl:when test="${row.isPrivate == false}">
				<spring:message code="newspaper.no" />
			</jstl:when>
		</jstl:choose>
	</display:column>

</display:table>

<security:authorize access="hasRole('USER')">
	<a href="newspaper/user/create.do"><spring:message
			code="newspaper.create" /></a>
	<br />
</security:authorize>

<spring:message var="backValue" code="newspaper.back" />
<jstl:choose>
	<jstl:when
		test="${requestURI == 'newspaper/user/listAddNewspapers.do' }">
		<input type="button" name="back" value="${backValue}"
			onclick="javascript: relativeRedir('volume/user/edit.do?volumeId=${volume.id}');" />
	</jstl:when>
	<jstl:otherwise>
		<input type="button" name="back" value="${backValue}"
			onclick="javascript: relativeRedir('welcome/index.do');" />
	</jstl:otherwise>
</jstl:choose>