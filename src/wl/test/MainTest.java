package wl.test;



import java.io.IOException;


import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;



public class MainTest {

	public static void main(String[] args) {
		WebDriver webDriver = new FirefoxDriver(); //创建火狐驱动（谷歌IE需下载驱动程序并添加浏览器插件，还有注意版本对应，比较麻烦，请百度版本对应）
        
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
        webDriver.get("http://www.chinatax.gov.cn/n810341/n810755/c3518520/content.html");
        
        WebElement passLoginEle= webDriver.findElement(By.xpath("//a[@class='forget-pwd J_Quick2Static' and @target='_blank' and @href='']")); //密码登录
        
        passLoginEle.click(); //显示账号密码表单域（模仿点击事件，将隐藏视图变为可见）
        
        
        webDriver.findElement(By.id("J_SubmitStatic")).click(); //点击登录按钮  
        webDriver.switchTo().defaultContent();

	}
	

}
