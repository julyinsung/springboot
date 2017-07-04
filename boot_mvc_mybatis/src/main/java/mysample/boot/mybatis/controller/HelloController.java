package mysample.boot.mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mysample.boot.mybatis.domain.User;
import mysample.boot.mybatis.service.HelloService;

/**
 * Mybatis 설정 연습
 * 
 * 참고
 * https://github.com/sivaprasadreddy/springboot-tutorials/tree/master/springboot-mybatis-demo
 * http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
 * http://sivalabs.in/2016/03/springboot-working-with-mybatis/
 * http://www.donnert.net/82
 * http://jsonobject.tistory.com/225
 * 
 * @author july
 *
 */
@Controller
public class HelloController {

	@Autowired HelloService service;
	
	@RequestMapping("/")
	public String hello(@RequestParam(value="name", required=false, defaultValue="Worlds") String name
			,Model model){

		List<User> users = service.getUserAll();
		
		System.out.println(users.toString());
		
		model.addAttribute("name", name);
		model.addAttribute("users", users);
		return "hello/hello";
		
	}
}
