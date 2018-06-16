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
		WebDriver webDriver = new FirefoxDriver(); //��������������ȸ�IE�������������������������������ע��汾��Ӧ���Ƚ��鷳����ٶȰ汾��Ӧ��
        
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
        webDriver.get("http://www.chinatax.gov.cn/n810341/n810755/c3518520/content.html");
        
        WebElement passLoginEle= webDriver.findElement(By.xpath("//a[@class='forget-pwd J_Quick2Static' and @target='_blank' and @href='']")); //�����¼
        
        passLoginEle.click(); //��ʾ�˺��������ģ�µ���¼�����������ͼ��Ϊ�ɼ���
        
        
        webDriver.findElement(By.id("J_SubmitStatic")).click(); //�����¼��ť  
        webDriver.switchTo().defaultContent();

	}
	

}
