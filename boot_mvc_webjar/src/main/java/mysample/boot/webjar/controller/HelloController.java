package mysample.boot.webjar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mysample.boot.webjar.domain.User;
import mysample.boot.webjar.service.HelloService;


/**
 * webjar 연습
 * 
 * 참고
 * http://www.namooz.com/2015/12/02/spring-boot-thymeleaf-9-webjars/
 * 도서: spring mvc4 익히기
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
		
		model.addAttribute("name", name);
		model.addAttribute("users", users);
		return "hello/hello";
		
	}
}
