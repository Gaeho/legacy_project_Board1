package com.oracle.springMVCBoard.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.oracle.springMVCBoard.dao.BDao;
import com.oracle.springMVCBoard.dto.BDto;

public class BListCommand implements BCommand {

	
	@Override
	public void execute(Model model) {
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
		model.addAttribute("list",dtos);
	}
}
