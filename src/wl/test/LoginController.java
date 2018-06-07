package wl.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import wl.test.craw.CrawTest;
import wl.test.gank.GankTest;

@RequestMapping("/test")
@Controller
public class LoginController {
	@RequestMapping("/ceshi")
	public void rwts(HttpServletRequest request,HttpServletResponse response) throws Exception{
        GankTest gn=new GankTest();
        gn.test4();
	}
	
	
	@RequestMapping("/ceshi2")
	public void rwts2(HttpServletRequest request,HttpServletResponse response) throws Exception{
        CrawTest gn=new CrawTest();
        gn.test();
	}
}
