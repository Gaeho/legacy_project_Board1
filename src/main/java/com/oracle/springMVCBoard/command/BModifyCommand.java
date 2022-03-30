package com.oracle.springMVCBoard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.oracle.springMVCBoard.dao.BDao;

public class BModifyCommand implements BCommand {

	@Override
	public void execute(Model model) {
		
		//value에 객체를 받는데 어떤 객체를 받을지 몰라 Object로 받음
		Map<String, Object> map = model.asMap();
						//asMap()메소드는 모델에 있는 것을 Map형태로 돌려줌
				
		//object 형태로 받은 객체를 http~ 클래스로 캐스팅 해서 request에 저장한다.
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		BDao dao = new BDao();
		dao.modify(bId, bName, bTitle, bContent);
		
		/* model.addAttribute("",board); /->수정 후에 어디론가 자료와 함께 이동할 필요가 없다
		 * 컨텐츠 작성때는 필요했다.*/

	}

}
