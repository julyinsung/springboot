package mysample.boot.security.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysample.boot.security.domain.User;
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

	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping("/user")
	public String hello(@RequestParam(value = "name", required = false, defaultValue = "Worlds") String name,
			Model model) {

		List<User> users = service.getUserAll();

		// System.out.println(passwordEncoder.getClass().getName());
		// System.out.println(passwordEncoder.encode("july"));
		// System.out.println(passwordEncoder.matches("july",
		// "$2a$10$xK5Apa2VXuLEE1Yhcx.VcOFQOYlMKE4S/2CIMFk61Qh/zqDeQUWa2"));

		model.addAttribute("name", name);
		model.addAttribute("users", users);
		return "user/user";

	}

	@RequestMapping("/addUser")
	public String addUser(@ModelAttribute User user, Model model) {

		List<User> users = service.getUserAll();

		model.addAttribute("users", users);
		return "user/addUser";
	}

	@RequestMapping(value = "/addUserAction", method = RequestMethod.POST)
	public String addUserAction(@Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "user/addUser";
		}

		return "redirect:/user";

	}

	@RequestMapping({ "/", "/login" })
	public String longin(Model model) {
		return "login/login";
	}

}
