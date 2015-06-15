
import com.caucho.hessian.client.HessianProxyFactory;


/**
 * yangsheng 2011-3-2
 * Hessian 服务器属性配置类。
 * 包含服务器URL设置及创建服务端代理类等方法
 */
public class HessianServer {

	private static HessianProxyFactory s_objHessianProxyFactory = new HessianProxyFactory();

	/**
	 * 创建服务接口对象
	 * 根据指定服务器地址创建接口对象， 以下参数都不能为空
	 * @param clazz    接口类型
	 * @param strURL   服务器地址
	 * @param uri      服务器的请求URI，除域名之外的部分
	 * @return
	 */
	public static Object createService(String strURL){
	    try {
            // 当接口类有方法被重载时，需设置 以下属性为真
	    	 s_objHessianProxyFactory.setConnectTimeout(60000);
            s_objHessianProxyFactory.setOverloadEnabled(true);
            return s_objHessianProxyFactory.create(strURL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	/**
	 * 使用已有服务器主机地址创建服务器代理对象。
	 * @param clazz 需创建的代理对象类型
	 * @param uri 服务器的请求URI，除域名之外的部分
	 * @return
	 */
//	public static Object createService(@SuppressWarnings("rawtypes") Class clazz,String module_url) {
//		try {
//			// 当接口类有方法被重载时，需设置 以下属性为真
//			 s_objHessianProxyFactory.setConnectTimeout(60000);
//			s_objHessianProxyFactory.setOverloadEnabled(true);
//			return s_objHessianProxyFactory.create(clazz, PropertiesConfig.getHessianConfig().getProperty("hession_sersver")+PropertiesConfig.getHessianConfig().getProperty(module_url)+clazz.getSimpleName()+"?hessian_user="+PropertiesConfig.getHessianConfig().getProperty("hession_companyId")+"&hessian_password="+PropertiesConfig.getHessianConfig().getProperty("hession_privateKey"));
//		} catch (Exception e) {
//		    throw new RuntimeException(e);
//		}
//	}
}
