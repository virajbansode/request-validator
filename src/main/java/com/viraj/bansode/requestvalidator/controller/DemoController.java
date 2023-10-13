package com.viraj.bansode.requestvalidator.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@PostMapping("/demo")
	public String demoMethod(@RequestBody Demo demo) {
		System.out.println("request received  : "+demo);
		
		return demo.getName()+"_ "+demo.getId();
	}

}
