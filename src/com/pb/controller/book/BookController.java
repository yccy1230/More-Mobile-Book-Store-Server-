package com.pb.controller.book;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pb.controller.BaseController;
import com.pb.json.BaseJson;
import com.pb.json.BookJson;
import com.pb.json.SearchBookJson;
import com.pb.util.JsonUtil;

@Controller
@RequestMapping("/book")
public class BookController  extends BaseController {
	
	@RequestMapping(value = "/borrowBook", method = { RequestMethod.POST })
	@ResponseBody
	public String borrowBook (HttpServletRequest request,
			HttpServletResponse response){
		BaseJson bj = new BaseJson();
		String bookid = request.getParameter("bookid");
		String id = request.getParameter("id");
		String result = bookService.borrowBook(Integer.parseInt(bookid), Integer.parseInt(id));
		if(result.equals("0001")) {
			bj.setRetcode(result);
			bj.setErrorMsg("书籍不存在，请确认后尝试！");
		}else if(result.equals("0002")){
			bj.setRetcode(result);
			bj.setErrorMsg("资料验证错误，请重新登陆后尝试！");
		}else if(result.equals("0003")){
			bj.setRetcode(result);
			bj.setErrorMsg("系统出错，请联系管理员！");
		}else if(result.equals("0004")){
			bj.setRetcode(result);
			bj.setErrorMsg("积分不足，请给自己的信仰充点值吧！");
		}else{
			bj.setRetcode("0000");
			bj.setObj(result);
			bj.setErrorMsg("借书成功！");
		}
		
		return JsonUtil.getInstance().obj2json(bj);
	}
	
	@RequestMapping(value = "/getBooksByISBN", method = { RequestMethod.POST })
	@ResponseBody
	public String getBooksByISBN (HttpServletRequest request,
			HttpServletResponse response){
		BaseJson bj = new BaseJson();
		String isbn = request.getParameter("isbn");
		List<BookJson> result = bookService.getBooksByISBN(isbn);
		if(result.size()==0) {
			bj.setRetcode("0001");
			bj.setErrorMsg("库里没有这本书哦！");
		}else{
			bj.setRetcode("0000");
			bj.setObj(result);
			bj.setErrorMsg("获取成功！");
		}
		return JsonUtil.getInstance().obj2json(bj);
	}
	
	
	@RequestMapping(value = "/getBooksByKeyword", method = { RequestMethod.POST })
	@ResponseBody
	public String getBooksByKeyword (HttpServletRequest request,
			HttpServletResponse response){
		BaseJson bj = new BaseJson();
		String keyword = request.getParameter("keyword");
		String positionGEO = request.getParameter("positionGEO");
		List<SearchBookJson> result = bookService.getBooksByKeyword(keyword,positionGEO);
		if(result.size()==0) {
			bj.setRetcode("0001");
			bj.setErrorMsg("附近没有搜索到这本书呢！");
		}else{
			bj.setRetcode("0000");
			bj.setObj(result);
			bj.setErrorMsg("获取成功！");
		}
		return JsonUtil.getInstance().obj2json(bj);
	}
	
	@RequestMapping(value = "/reversebook", method = { RequestMethod.POST })
	@ResponseBody
	public String reversebook (HttpServletRequest request,
			HttpServletResponse response){
		BaseJson bj = new BaseJson();
		String userid = request.getParameter("userid");
		String bookid = request.getParameter("bookid");
		String result = bookService.reversebook(userid,bookid);
		if(result.equals("0000")) {
			bj.setRetcode("0000");
			bj.setErrorMsg("预约成功，请及时联系用户取书呀！");
		}else if(result.equals("0001")){
			bj.setRetcode("0001");
			bj.setErrorMsg("身份验证出现问题，请重新登陆试试呀！");
		}else if(result.equals("0002")){
			bj.setRetcode("0002");
			bj.setErrorMsg("被人抢先了一步~ QWQ！");
		}else if(result.equals("0003")){
			bj.setRetcode("0003");
			bj.setErrorMsg("服务器出错，请联系管理员！");
		}
		return JsonUtil.getInstance().obj2json(bj);
	}
}