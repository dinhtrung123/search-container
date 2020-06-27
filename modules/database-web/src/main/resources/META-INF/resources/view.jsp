<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portal.kernel.dao.search.SearchContainer"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.blogspot.chingovan.database.service.StudentLocalServiceUtil"%>
<%@page import="com.blogspot.chingovan.database.model.Student"%>
<%@ include file="./init.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 	pageEncoding="UTF-8"%>

<%
	final DateFormat DD_MM_YYYYY = new SimpleDateFormat("dd/MM/yyyy");
	final String DATE_EMPTY_FORMAT = "--/--/----";
	final String FEMALE = LanguageUtil.get(request, "database-portlet.student-list.gender.female");
	final String MALE = LanguageUtil.get(request, "database-portlet.student-list.gender.male");
    String fullName = ParamUtil.getString(request, "fullTen","");
    String msgEmpty = "Khong co du lieu";
    int cur = ParamUtil.getInteger(request, "cur" , 1 ) ;
    int delta = ParamUtil.getInteger(request, "delta" ,2) ;
    PortletURL portletURL = renderResponse.createActionURL() ;
	portletURL.setParameter("mvcPath", "/view.jsp");
	portletURL.setParameter("fullTen", fullName);
    SearchContainer searchContainer = new SearchContainer<Student>(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, cur, delta, portletURL, null, msgEmpty);
    int start = searchContainer.getStart();
  
	int end = searchContainer.getEnd();
  
     List<Student> students =StudentLocalServiceUtil.getAllStudent(fullName,start,end) ;
     int totaltRecords = StudentLocalServiceUtil.getCountStudent(fullName) ;

		searchContainer.setTotal(totaltRecords);
		searchContainer.setResults(students) ;
     
%>
 <portlet:renderURL var = "addStudentRenderURL">
    <portlet:param name="mvcPath" value="/add-student.jsp"/>
 </portlet:renderURL>
<portlet:renderURL var = "searchURL">
<portlet:param name="mvcPath" value="/view.jsp"/>
</portlet:renderURL>
   
<p>
	<b><liferay-ui:message key="database-portlet.student-list"/></b>
</p>


<% if( students != null && !students.isEmpty()){ 
	int index = 0;
%>
	<div class="mb-5">
		<a href="<%=addStudentRenderURL%>"
			class="btn  btn-primary btn-default"> <i
			class="glyphicon glyphicon-plus"></i> Add Student
		</a>
	</div>
  <aui:form action="<%=searchURL%>" name="fm" method="post">
  
   	<div class="col-md-12">
		<div>
			<div class="col-md-5">
				<label>Student Name</label>
				<aui:input label="" title="" type="text"
					class="oep-inputfield search" id="fullTen" name="fullTen"
					value="" placeholder="Input key search" />
			</div>
		</div>
		<div class="text-right">
			<aui:button type="submit" icon="icon-search" value="Seach" />
		</div>

		<table class="table table-bordered table-hover">
			<tr>
				<th><liferay-ui:message
						key="database-portlet.student-list.order" /></th>
				<th><liferay-ui:message
						key="database-portlet.student-list.code" /></th>
				<th><liferay-ui:message
						key="database-portlet.student-list.fullName" /></th>
				<th><liferay-ui:message
						key="database-portlet.student-list.birthday" /></th>
				<th><liferay-ui:message
						key="database-portlet.student-list.gender" /></th>
				<th><liferay-ui:message
						key="database-portlet.student-list.address" /></th>
				<th><liferay-ui:message key="database-portlet.student-list.sua" /></th>
				<th><liferay-ui:message key="database-portlet.student-list.xoa" /></th>

			</tr>
			<%for(Student student : students) { %>
			<tr>
				<td><%= ++index %></td>
				<td><%= student.getCode() %></td>
				<td><%= student.getFullName() %></td>
				<td><%= student.getBirthday() != null ? DD_MM_YYYYY.format( student.getBirthday()) : DATE_EMPTY_FORMAT %></td>
				<td><%= student.getGender() ? MALE : FEMALE%></td>
				<td><%= student.getAddress() != null ? student.getAddress() : "" %></td>

				<portlet:renderURL var="updateStudentRenderURL">
					<portlet:param name="mvcPath" value="/add-student.jsp" />
					<portlet:param name="studentId"
						value="<%=String.valueOf(student.getStudentId())%>" />
				</portlet:renderURL>

				<portlet:actionURL name="deleteStudent" var="deleteStudentActionURL">
					<portlet:param name="studentId"
						value="<%=String.valueOf(student.getStudentId())%>" />
				</portlet:actionURL>



				<td class="text-center" style="width: 50px"><a
					href="<%=updateStudentRenderURL%>"
					class="btn  btn-primary btn-default btn-sm px-2 py-1"> <i
						class="glyphicon glyphicon-edit"></i>
				</a></td>

				<td class="text-center" style="width: 50px"><a
					href="<%=deleteStudentActionURL%>"
					class="btn  btn-primary btn-default btn-sm px-2 py-1"
					onclick="return confirm('Are you sure you want to delete this item?');">
						<i class="glyphicon glyphicon-remove"></i>
				</a></td>

			</tr>
			<%
			}
		%>
	
</table>

<c:if test="<%= searchContainer != null %>">
			<div id = "pagination">
			<br/>
			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
			</div>
			</c:if>

</aui:form>
<%
	} else {
%>
	<b><liferay-ui:message key="database-portlet.student-list.empty"/></b>
<%}%>