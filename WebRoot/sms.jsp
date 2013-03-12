<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.nissan.sms.SmsSender"%>
<%
	String mobiles=request.getParameter("ms");
	String content=request.getParameter("c");
	
	if(mobiles==null){
		out.println("request mobiles");
		return;
	}
	if(content==null){
		out.println("request content");
		return;
	}
	content=new String(content.getBytes("ISO-8859-1"),"UTF-8");
	SmsSender.log("startSms",mobiles,content);
	SmsSender sender=new SmsSender();
	String res=sender.getConnect();
	if(res.contains("True")){
		res=sender.sendSMS(mobiles,content);
	}else{
		res="False";
	}
	SmsSender.log("endSms",mobiles,content,res);
	out.println(res);
	sender.close();
 %>
