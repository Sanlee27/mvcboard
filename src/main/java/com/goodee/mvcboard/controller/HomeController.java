package com.goodee.mvcboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 서블릿 대체
public class HomeController {
	@GetMapping("/home") // web.xml > url 패턴매핑 or 에노테이션 WebServlet
	public String home(){
		
		return "home"; // requestDispatcher.forward()
	}
}
