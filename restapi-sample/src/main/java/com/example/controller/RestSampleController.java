package com.example.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.validation.FileInfo;
import com.example.validation.Files;
import com.example.validation.JoinFile;


/**
 * https://gist.github.com/ihoneymon/a343e2f4a0299988206e
 * https://spring.io/blog/2013/12/19/serving-static-web-content-with-spring-boot
 * http://stackoverflow.com/questions/24661289/spring-boot-not-serving-static-content
 * 
 * @author july
 */
@RestController
public class RestSampleController {
	
	@RequestMapping("/")
	public FileInfo RestTest(HttpServletRequest request){
		Files f = new JoinFile();
		
		String filePath = null;
		try {
			filePath = new ClassPathResource("CC01_IF20170131_0002_REQ").getFile().getPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(">>>>>>>>>>> filePath" +filePath);
		
		f.processFile(filePath);
		return f.getFileInfo();
	}
}
