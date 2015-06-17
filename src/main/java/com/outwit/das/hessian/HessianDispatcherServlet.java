package com.outwit.das.hessian;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.caucho.hessian.server.HessianServlet;
import com.outwit.das.common.SpringContextUtil;
import com.outwit.das.utils.ClassHelper;
import com.outwit.das.utils.CollectionUtil;
import com.outwit.das.utils.IocHelper;
import com.outwit.das.utils.WebUtil;

//@WebServlet(urlPatterns = HessianConstant.URL_PREFIX + "/*", loadOnStartup = 0)
public class HessianDispatcherServlet extends HessianServlet {
	
	private static final long serialVersionUID = 4974171086148904627L;
	// 定义一个 Hessian Servlet Map，用于管理 Hessian URL 与 Hessian Servlet 之间的映射关系
    private final Map<String, HessianServlet> hessianServletMap = new HashMap<String, HessianServlet>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        System.out.println("开始初始化");
        // 获取所有标注了 @Hessian 注解的类（接口）
        List<Class<?>> hessianInterfaceList = ClassHelper.getClassListByAnnotation(Hessian.class);
        
        if (CollectionUtil.isNotEmpty(hessianInterfaceList)) {
            // 遍历所有 Hessian 接口
            for (Class<?> hessianInterface : hessianInterfaceList) {
                // 获取 Hessian URL
            	System.out.println(hessianInterface.getName());
            	String backpage = hessianInterface.getPackage().getName();
            	backpage = backpage.replace("com.outwit.das", "").replace(".service", "").replace('.','/');
            	System.out.println(backpage);
                // 获取 Hessian 接口的实现类
                Class<?> implClass = IocHelper.findImplementClass(hessianInterface);
                System.out.println(implClass.getName());
                String beanName = hessianInterface.getSimpleName();
                beanName = beanName.substring(0, 1).toLowerCase()+beanName.substring(1, beanName.length()) + "Impl";
                Object implInstance = SpringContextUtil.getBean(beanName);
                // 获取实现类实例
                // 创建 Hessian Servlet
                HessianServlet hessianServlet = new HessianServlet();
                hessianServlet.setHomeAPI(hessianInterface); // 设置接口
                hessianServlet.setHome(implInstance); // 设置实现类实例
                hessianServlet.init(config); // 初始化 Servlet
                // 将 Hessian URL 与 Hessian Servlet 放入 Hessian Servlet Map 中
                hessianServletMap.put(HessianConstant.URL_PREFIX + backpage + "/" +hessianInterface.getSimpleName(), hessianServlet);
            }
        }
    }
 
    public void service(ServletRequest request, ServletResponse response) throws IOException, ServletException {
    	System.out.println("请求来了");
        // 获取请求 URL
        HttpServletRequest req = (HttpServletRequest) request;
        String url = WebUtil.getRequestPath(req);
        // 从 Hessian Servlet Map 中获取 Hessian Servlet
        System.out.println(url);
        HessianServlet hessianServlet = hessianServletMap.get(url);
        if (hessianServlet != null) {
            // 执行 Servlet
        	System.out.println("执行servlet");
            hessianServlet.service(request, response);
        }
    }
}
