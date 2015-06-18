package com.outwit.das.common;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.remoting.caucho.HessianServiceExporter;

import com.alibaba.druid.filter.config.ConfigTools;
import com.outwit.das.utils.ObjectUtil;
import com.outwit.das.utils.PropsUtil;


public class DaslHessianServiceExporter extends HessianServiceExporter{
    
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
		
		String hessian_user = request.getParameter("hessian_user");
		String hessian_password = request.getParameter("hessian_password");
		System.out.println(hessian_user+":"+hessian_password);
		if(!ObjectUtil.objIsNotNull(hessian_user) || !ObjectUtil.objIsNotNull(hessian_password)){
			Log.getCommon().debug("用户名或密码为空,非法请求");
			return; 
		}
		try {
			if(PropsUtil.getConfigMap().get("hessian.username").equals(ConfigTools.encrypt(hessian_user))){
				 if(PropsUtil.getConfigMap().get("hessian.password").equals(ConfigTools.encrypt(hessian_password))){
					 Log.getCommon().debug("验证通过执行下一步");
					 System.out.println("验证通过执行下一步");
					 super.handleRequest(request, response);
				 }else{
					 Log.getCommon().error("密码错误");
					 return;
				 }
			}else{
				Log.getCommon().error("用户名不存在");
				return;
			}
		} catch (Exception e) {
			Log.getCommon().error(e);
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
