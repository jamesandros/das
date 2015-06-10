package com.outwit.das.common;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.remoting.caucho.HessianServiceExporter;

import com.outwit.das.exception.BusinessException;

public class DaslHessianServiceExporter extends HessianServiceExporter{
    
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
		String hessian_user = request.getParameter("hessian_user");
		String hessian_password = request.getParameter("hessian_password");
		if(!ObjectIsNullUtil.objIsNotNull(hessian_user) || !ObjectIsNullUtil.objIsNotNull(hessian_password)){
			Log.getCommon().debug("用户名或密码为空,非法请求");
			return; 
		}
		
		
		if("andros" == hessian_user){
			Log.getCommon().debug("该用户不存在,不能完成请求");
			return; 
		}else{
			if("andros520".equals(hessian_password)){
				super.handleRequest(request, response);  
			}else{
				Log.getCommon().debug("非法的用户请求");
				return;
			}
		}
		
//		try {
//			String comppanyServiceName = CompanyServiceImpl.class.getSimpleName();
//			CompanyService companyService = SpringContextUtil.getBean(comppanyServiceName.substring(0, 1).toLowerCase()+comppanyServiceName.substring(1, comppanyServiceName.length()));
//			T_company t_company = companyService.findCompanyById(hessian_user);
//			
//			
//		} catch (BusinessException e) {
//			Log.getCommon().error("用户身份认证出错", e);
//			return;
//		}
    }  
}
