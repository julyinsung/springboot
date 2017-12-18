package mysample.boot.security.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mysample.boot.security.domain.User;
import mysample.boot.security.domain.common.CommonListVO;
import mysample.boot.security.service.HelloService;

/**
 * 
 * 
 * @author july
 *
 */
@Controller
public class HelloController {

	@Autowired
	HelloService service;

	@RequestMapping("/user")
	public String user(@ModelAttribute User user, Model model) {
		model.addAttribute("users", user);
		return "user/user";
	}
	
	@RequestMapping("/userListAction")
	public String userListAction(@ModelAttribute User user, Model model) {
		System.out.println("user.getNowPage ="+user.getNowPage());
		
		CommonListVO oList = service.getUserAll(user);
		System.out.println(oList);
		
		model.addAttribute("dataList", oList);
		model.addAttribute("user", user);
		return "user/userListAction";
	}

	@RequestMapping("/addUser")
	public String addUser(@ModelAttribute User user, Model model) {
		model.addAttribute("users", user);
		return "user/addUser";
	}

	@RequestMapping(value = "/addUserAction", method = RequestMethod.POST)
	public String addUserAction(@Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "user/addUser";
		}

		service.addUser(user);
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		System.out.println(securityContext.getAuthentication().isAuthenticated());
		if(securityContext.getAuthentication().isAuthenticated()){
			return "redirect:/user";	
		} else {
			return "redirect:/login";
		}

	}

	@RequestMapping({ "/", "/login" })
	public String longin(Model model) {
		return "login/login";
	}
	

}
