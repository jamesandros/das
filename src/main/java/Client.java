
import com.outwit.das.exception.BusinessException;
import com.outwit.das.permission.model.User;
import com.outwit.das.permission.service.UserService;


public class Client {
	public static void main(String[] args) {
		    String url = "http://localhost:8080/hessian/zenmepo/UserService?hessian_user=andros&hessian_password=andros520";
	       
	        UserService userservice= (UserService)HessianServer.createService(UserService.class,url);
	        User u  = new User();
			u.setUsername("1234");
			u.setSign("sadfsdafsd");
			u.setAge("18");
			u.setMobilenum("12341234");
			u.setPassword("123412");
			try {
				userservice.add(u);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}	
