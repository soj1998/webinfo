package com.wl.runzekeji.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRegUtils {
	/**
	* ��ȡƥ��������ַ���
	* @param content
	* @param sPattern
	* @return
	*/
	public static List<String> getMatcherStrs(String content,String sPattern){
	Pattern p = Pattern.compile(sPattern);
	Matcher m = p.matcher(content);
	List<String> result=new ArrayList<String>();
	while(m.find()){
	result.add(m.group());
	}
	return result;
	}

	/**
	* ��ȡ����ƥ����ַ���
	* @param content
	* @param sPattern
	* @return
	*/
	public static String getMatcherStr(String content,String sPattern){
	List<String> strs=getMatcherStrs( content, sPattern);
	if(strs.size()>0){
	return strs.get(0);
	}else{
	return "";
	}

	}

	/**
	* ��ȡƥ��������ַ���
	* @param content
	* @param sPattern
	* @return
	*/
	public static List<String> getMatcherStrsAmong(String content,String sPattern){
	Pattern p = Pattern.compile(sPattern);
	Matcher m = p.matcher(content);
	List<String> result=new ArrayList<String>();
	while(m.find()){
	result.add(m.group(1));
	}
	return result;
	}
	public static String getMatcherStrAmong(String content,String sPattern){
	List<String> strs=getMatcherStrsAmong(content,sPattern);
	if(strs.size()>0){
	return strs.get(0);
	}else{
	return "";
	}
	}

	/**
	* ��ȡ����ƥ�������
	* @param content
	* @param splitStr
	* @return
	*/
	public static List<String> getDates(String content,String splitStr){
	return getMatcherStrs(content,"\\d{4}\\"+splitStr+"\\d{1,2}\\"+splitStr+"\\d{1,2}");
	}

	/**
	* ��ȡ��һ��ƥ�������
	* @param content
	* @param splitStr
	* @return
	*/
	public static String getDate(String content,String splitStr){
	return getDates(content,splitStr).get(0);
	}
	public static List<String> getDatesReg(String content){
		return getMatcherStrs(content,"\\d{4}\\��\\d{1,2}\\��\\d{1,2}��");
	}
	/**
	* ��ȡ���һ��ƥ�������
	* @param content
	* @param splitStr
	* @return
	*/
	public static String getLastDatesReg(String content){
		List<String> rs=getDatesReg(content);
		if(rs.size()>0){
			return rs.get(rs.size()-1);
		}
		return "";
	}
}
