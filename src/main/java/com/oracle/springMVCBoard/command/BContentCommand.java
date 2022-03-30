package com.oracle.springMVCBoard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.oracle.springMVCBoard.dao.BDao;
import com.oracle.springMVCBoard.dto.BDto;

//커맨드에 들어 있는 클래스는 DB를 다루는 서비스 페이지이다.
// 작업을 수행하고 return할 값이 있다면 model을 이용하여 값을 전달하고
// 전달할 값이 없다면 여기에서 작업이 종료되고 리턴 값은 없다.
public class BContentCommand implements BCommand {

	@Override
	public void execute(Model model) {
		
		//value에 객체를 받는데 어떤 객체를 받을지 몰라 Object로 받음
		Map<String, Object> map = model.asMap();
				//asMap()메소드는 모델에 있는 것을 Map형태로 돌려줌
		
		//object 형태로 받은 객체를 http~ 클래스로 캐스팅 해서 request에 저장한다.
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		String bId = request.getParameter("bId");
		
		BDao dao = new BDao();//mvc_board에서 만들것을 import해줘야함
		BDto board = dao.contentView(bId);
		
		model.addAttribute("mvc_board",board);
	}

}
