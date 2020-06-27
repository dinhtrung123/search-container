package com.blogspot.chingovan.database.service.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import com.blogspot.chingovan.database.model.Student;
import com.blogspot.chingovan.database.model.impl.StudentImpl;
import com.blogspot.chingovan.database.service.persistence.StudentFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

public class StudentFinderImpl  extends StudentFinderBaseImpl implements StudentFinder{
	 @ServiceReference(type = CustomSQL.class)
		private CustomSQL customSQL ;
		
		@SuppressWarnings("unchecked")
		public List<Student> getAllStudent(String fullName,int start,int end){
			List<Object> params  = new ArrayList<>() ;
			Session session = null ;
			
			try {
				
				  session=openSession();
				  String sql = customSQL.get(getClass(),"com.blogspot.chingovan.database.service.persistence.impl.StudentFinderImpl.getAllStudent");
				  if(Validator.isNotNull(fullName))
				  {
					  sql = sql.replace("[$Full_Name$]","where FullName = ?") ;
					  params.add(fullName) ;
				  }else
				  {
					  sql = sql.replace("[$Full_Name$]","") ; 
				  }
				 
				  SQLQuery sqlQuery=session.createSQLQuery(sql);
				  sqlQuery.setCacheable(false);
				  sqlQuery.addEntity("Student", StudentImpl.class);
		           QueryPos queryPos=QueryPos.getInstance(sqlQuery);
		           for (Object object : params) {
					queryPos.add(object);
				}    
		           return (List<Student>) QueryUtil.list(sqlQuery, getDialect(),start,end);
		           
			} catch (Exception e) {
				
			}finally{
				   closeSession(session);
			}
			
			return null ;
		
//			Session session = null;
//			try {
//				String strQuery = customSQL.get(getClass(),"com.blogspot.chingovan.database.service.persistence.impl.StudentFinderImpl.getAllStudent");
//				List<Object> params = new ArrayList<Object>();
//				if (!fullName.equals("")) {
//					strQuery = strQuery.replace("[$Full_Name$]"," where st.FullName = ?") ;
//					params.add(fullName);
//				} else {
//					strQuery = strQuery.replace("[$Full_Name$]", "");
//				}
//				
//				session = openSession();
//				SQLQuery query = session.createSQLQuery(strQuery);
//				query.addEntity("Student", StudentImpl.class);
//				QueryPos queryPos = QueryPos.getInstance(query);
//				for (Object obj : params) {
//					queryPos.add(obj);
//				}
//				return (List<Student>) QueryUtil.list(query, getDialect(),start,end);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			} finally {
//				if (session != null) {
//					closeSession(session);
//				}
//			}
//			return null;
		}
		public int getCountStudent(String fullName){
			List<Object> params  = new ArrayList<>() ;
			Session session = null ;
			
			try {
				
				  session=openSession();
				  String sql = customSQL.get(getClass(),"com.blogspot.chingovan.database.service.persistence.impl.StudentFinderImpl.getCountStudent");
				  if(Validator.isNotNull(fullName))
				  {
					  sql = sql.replace("[$Full_Name$]","where FullName = ?") ;
					  params.add(fullName) ;
				  }else
				  {
					  sql = sql.replace("[$Full_Name$]","") ; 
				  }
				 System.out.println("cau lenh sql" + sql);
				  SQLQuery sqlQuery=session.createSQLQuery(sql);
				  sqlQuery.setCacheable(false);
		           QueryPos queryPos=QueryPos.getInstance(sqlQuery);
		           for (Object object : params) {
					queryPos.add(object);
				}    
		           return  Integer.parseInt(String.valueOf(sqlQuery.uniqueResult()));
		           
			} catch (Exception e) {
				
			}finally{
				   closeSession(session);
			}
			
			return 0 ;
		}
}
