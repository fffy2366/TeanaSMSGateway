package com.vastrek.nissan.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HightLight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HightLight light = new HightLight();
		//String str = light.IgnoreCaseReplace("bbaaabbb", "aa bb");
		Map keywords = new HashMap<String, String>() ;
//		keywords.put("百度", "http://www.baidu.com") ;
//		keywords.put("google", "http://www.google.com") ;
//		String str = light.IgnoreCaseReplace("baidu百度baidu谷歌googlegoogle百度谷歌谷歌谷歌", keywords) ;
//		System.out.println(str);
		
		
		String text = "111111111#红牛#ddddd" ;
		String key = "红牛" ;
		String url = "http://t.qq.com/" ;
		String str = light.replaceKey(text,key,url) ;
		System.out.println(str);
		
	}

	public String IgnoreCaseReplace(String title, String keywords) {
		String[] gjz = keywords.split(" ");
		System.out.println(gjz.length);
		String ret = null;
		for (String str : gjz) {
			if (str == null || str.equals("")) {
				continue;
			}

			Pattern p = Pattern.compile(str, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(ret == null ? title : ret);
			ret = m.replaceAll("<span class=\"highlight\">" + keywords
					+ "</span>");
		}

		return ret;
	}
	
	public String  IgnoreCaseReplace(String content, Map<String,String> keywords) {
		keywords.put("百度", "http://www.baidu.com") ;
		keywords.put("google", "http://www.google.com") ;
		
		String ret = null ;
		Iterator it = keywords.entrySet().iterator() ;
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next(); 
			Object key = entry.getKey();  
			Object value = entry.getValue();  
			//System.out.println(key);
			//System.out.println(value);
			Pattern p = Pattern.compile((String)key, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(ret == null ? content : ret);
			ret = m.replaceAll("<a href="+(String)value+">" + (String)key
					+ "</a>");
		}
		return ret ;
	}
	
    public static String replaceKey(String text,String key,String url){
		String ret = null ;
		Pattern p = Pattern.compile("#"+key+"#", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(ret == null ? text : ret);
		
		ret = m.replaceAll("<a target=\"_blank\" style=\"color:red\" href="+url+key+">#" + key
				+ "#</a>");
	
    	return ret ;
    }

    public String addUrl(String text){
    	String reg = "http://t.cn/[A-Za-z0-9]{7}" ;
    	Pattern p = Pattern.compile(reg) ;
    	Matcher m = p.matcher(text) ;
    	//text = m.replaceAll("==") ;
    	//text = m.toString() ;
    	StringBuffer sb=new StringBuffer();
        while(m.find()){
            m.appendReplacement(sb, "<a href='"+m.group()+"' target='_blank'>"+m.group()+"</a>");
        }
        m.appendTail(sb);
    	return sb.toString() ;
    }

}
