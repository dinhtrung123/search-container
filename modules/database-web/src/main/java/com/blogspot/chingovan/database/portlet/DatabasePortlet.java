package com.blogspot.chingovan.database.portlet;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.blogspot.chingovan.database.model.Student;
import com.blogspot.chingovan.database.service.StudentLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=database-web Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class DatabasePortlet extends MVCPortlet {
       
	public static final String DATE_FORMAT_FULL ="yyyyMMddHH24mmss";
	public static final String DATE_FORMAT_D_M_Y ="dd/MM/yyyy";
	public static final String DATE_FORMAT_D_M_Y_H_M_S ="dd/MM/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_D_M_Y_H_M_S_1 ="yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_D_M_Y_H_M ="dd/MM/yyyy HH:mm";	
	public static final String DATE_FORMAT_Y_M_D="yyyyMMdd";
	public static final String DATE_DB_FORMAT_Y_M_D="yyyy-mm-dd";
	public static final String DATE_FORMAT_H_M_D_M_Y="HH:mm dd/MM/yyyy";	
	public static final String DATE_FORMAT_Y_M_D_H="yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT_Y_M_D_KEYCLOAK="yyyy-MM-dd";
	private Log log = LogFactoryUtil.getLog(this.getClass().getName());

	public void addStudent(ActionRequest actionRequest, ActionResponse actionResponse) {

		String userName = ParamUtil.getString(actionRequest, "userName");
		String fullName = ParamUtil.getString(actionRequest, "fullName");
		String address = ParamUtil.getString(actionRequest, "address");
		String birthday = ParamUtil.getString(actionRequest, "birthday");
		String gender = ParamUtil.getString(actionRequest, "gender");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_D_M_Y);
		Date date;
		long id = ParamUtil.getLong(actionRequest, "studentId", 0);
		if (id == 0) {
			long studentId = CounterLocalServiceUtil.increment(Student.class.getName());
			Student student = StudentLocalServiceUtil.createStudent(studentId);
			student.setStudentId(studentId);
			student.setUserName(userName);
			student.setFullName(fullName);
			student.setAddress(address);
            student.setGender(Boolean.parseBoolean(gender));
			try {
				date = simpleDateFormat.parse(birthday);
				student.setBirthday(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			StudentLocalServiceUtil.addStudent(student);

		} else {
			try {
				Student student = StudentLocalServiceUtil.getStudent(id);
				student.setUserName(userName);
				student.setFullName(fullName);
				student.setAddress(address);
				student.setGender(Boolean.parseBoolean(gender));
				try {
					date = simpleDateFormat.parse(birthday);
					student.setBirthday(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				StudentLocalServiceUtil.updateStudent(student);
			} catch (PortalException e) {

				e.printStackTrace();
			}
		}

	}

	@ProcessAction(name = "deleteStudent")
	public void deleteStudent(ActionRequest actionRequest, ActionResponse actionResponse) {
		long studentId = ParamUtil.getLong(actionRequest, "studentId", GetterUtil.DEFAULT_LONG);
		
		try {

			StudentLocalServiceUtil.deleteStudent(studentId);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	

}
     
   
 