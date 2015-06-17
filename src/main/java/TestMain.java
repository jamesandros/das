import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;


public class TestMain {
	public static void main(String[] args) {
		List<String> stList = new ArrayList<>();
		stList.add("/124312");
		stList.add("/124543");
		stList.add("/124323");
		stList.add("/165434");
		System.out.println(StringUtils.toStringArray(stList));
	}
}
