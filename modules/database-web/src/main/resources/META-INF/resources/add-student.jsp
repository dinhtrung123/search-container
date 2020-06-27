<%@ include file="./init.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" 	pageEncoding="UTF-8"%>
<%@page import="com.blogspot.chingovan.database.service.StudentLocalServiceUtil"%>
<%@page import="com.blogspot.chingovan.database.model.Student"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%final DateFormat DD_MM_YYYYY = new SimpleDateFormat("dd/MM/yyyy");
   String DATE_EMPTY_FORMAT = "--/--/----"; %>
<portlet:defineObjects />
 
 <%
     long studentId = ParamUtil.getLong(request, "studentId",0) ;
     System.out.println("studentid" + studentId);
     Student student = StudentLocalServiceUtil.fetchStudent(studentId) ;
   
 %>
<portlet:actionURL name="addStudent" var="addStudentActionURL">
   <portlet:param name="studentId" value="<%=String.valueOf(studentId)%>"/> 
</portlet:actionURL>
            
                
<h2>Add Student Form here !</h2>
 <%if(student != null) {%> 
<aui:form action="<%=addStudentActionURL %>" name="studentForm" method="POST">
    <aui:input name="userName" value="<%=student.getUserName()%>"> 
         <aui:validator name="required"/>
         <aui:validator name="String"/>
    </aui:input>
 
    <aui:input name="fullName" value="<%= student.getFullName() %>" >
         <aui:validator name="required"/>
         <aui:validator name="String"/>
    </aui:input>
 
    <aui:input name="birthday" value="<%= student.getBirthday() !=null ?  DD_MM_YYYYY.format(student.getBirthday()) : "" %>" >
         <aui:validator name="required"/>
         <aui:validator name="Date"/>
    </aui:input>
     <label class="control-label no-padding-right">Gender</label>   
        <aui:select lable = "Gender" name="gender" id="gender">
					<option value="true" <%=(student.getGender() == true) ? "selected='selected'"  : ""  %>>Male</option>
					<option value="false" <%=(student.getGender()== false) ? "selected='selected'"  : "" %>>Female</option>
				
		</aui:select> 
			 
 
    <aui:input name="address" value="<%=student.getAddress() %>"  >
         <aui:validator name="required"/>
         <aui:validator name="String"/>
    </aui:input>
  
 
    <aui:button type="submit" name="" value="Submit"></aui:button>
</aui:form>
<%} else { %>
		<aui:form action="<%=addStudentActionURL %>" name="studentForm" method="POST">
		    <aui:input name="userName" value=""> 
		         <aui:validator name="required"/>
		         <aui:validator name="String"/>
		    </aui:input>
		 
		    <aui:input name="fullName" value="" >
		         <aui:validator name="required"/>
		         <aui:validator name="String"/>
		    </aui:input>
		 
		    <aui:input name="birthday" value="" >
		         <aui:validator name="required"/>
		         <aui:validator name="Date"/>
					    </aui:input>
					    
			 <label class="control-label no-padding-right">Gender</label>   
			 <aui:select lable = "Gender" name="gender" id="gender">
					<option value="true" >Male</option>
					<option value="false">Female</option>
				
		     </aui:select> 
			
				<aui:input name="address" value=""  >
		         <aui:validator name="required"/>
		         <aui:validator name="String"/>
		    </aui:input>
		  
		 
		    <aui:button type="submit" name="" value="Submit"></aui:button>
		</aui:form>
<%} %>
