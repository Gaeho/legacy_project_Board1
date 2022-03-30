package com.oracle.springMVCBoard.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oracle.springMVCBoard.command.BCommand;
import com.oracle.springMVCBoard.command.BContentCommand;
import com.oracle.springMVCBoard.command.BDeleteCommand;
import com.oracle.springMVCBoard.command.BListCommand;
import com.oracle.springMVCBoard.command.BModifyCommand;
import com.oracle.springMVCBoard.command.BReplyCommand;
import com.oracle.springMVCBoard.command.BReplyViewCommand;
import com.oracle.springMVCBoard.command.BWriteCommand;

@Controller
public class BController {
	
	BCommand command = null;
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		System.out.println("BController list Start!");
		command = new BListCommand();
		command.execute(model); //db에 접근 execute메서드는 BListcommand에 정의되어 있다.
		
		return "list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("Bcontroller content_view Start!");
		model.addAttribute("request",request);
		command = new BContentCommand();
		command.execute(model);
		
		return "content_view";
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model) {
		System.out.println("Bcontroller modify Start!");
		model.addAttribute("request",request);
		command = new BModifyCommand();
		command.execute(model);
	
		return "redirect:list";
	} 
	
	
	//작성 1번 컨트롤러                    get/post 적을 시 value="url", 형식을 사용한다.
	@RequestMapping(value="/write_view", method=RequestMethod.GET)
	public String write_view(Model model) {
		System.out.println("Bcontroller write_view Start!");
		
		return "write_view";
	}
	
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String write(HttpServletRequest request,Model model) {
		
		System.out.println("Bcontroller write Start!");

		model.addAttribute("request",request);
		command = new BWriteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	

	@RequestMapping(value="/reply_view")
	public String reply_view(HttpServletRequest request,Model model) {
		System.out.println("Bcontroller reply_view Start!");
		
		model.addAttribute("request",request);
		command = new BReplyViewCommand();
		command.execute(model);
		
		return "reply_view";
	}
	
	@RequestMapping(value="/reply", method = RequestMethod.POST)
	public String reply(HttpServletRequest request,Model model) {
		System.out.println("Bcontroller reply Start!");
		
		model.addAttribute("request",request);
		command = new BReplyCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping(value="/delete")
	public String delete(HttpServletRequest request,Model model) {
		System.out.println("Bcontroller reply Start!");
		
		model.addAttribute("request",request);
		command = new BDeleteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
}
