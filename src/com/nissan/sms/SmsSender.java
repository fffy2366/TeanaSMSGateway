package com.nissan.sms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.vastrek.nissan.biz.BatchSendSMS;
import com.vastrek.nissan.biz.CodeDaoImpl;
import com.vastrek.nissan.entity.Code;

import masmclient.LYClient;


/**
 * @author Yao Fuyuan
 * @created 2013-01-31 11:52:00
 * 
 */

public class SmsSender{
	public static void main(String[] args) {
		BatchSendSMS sendSMS = new BatchSendSMS() ;
		sendSMS.sendSms() ;
	}
	
	private LYClient lYClient = null;
	public String getConnect()
	{
		String conStatus ="False";
		try{
			lYClient = new LYClient();

			Properties smsConfig = new Properties();
			try
			{
				smsConfig.load(SmsSender.class.getResourceAsStream("/SmsContent.properties"));
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				conStatus="Cannot find SmsContent.properties:"+e.getMessage();
			}

			String sIP = smsConfig.getProperty("sIP");
			String loginName = smsConfig.getProperty("loginName");
			String loginPWD = smsConfig.getProperty("loginPWD");
			String sPort = smsConfig.getProperty("sPort");
			log(sIP+"\t"+ loginName+"\t"+ loginPWD+"\t"+ sPort);
			conStatus = lYClient.Connect(sIP, loginName, loginPWD, sPort);
		}catch (Exception e1){
			conStatus="Cannot connect:"+e1.getMessage();
		}
		log(conStatus);
		if(conStatus.contains("True")){
			return "True";
		}else{
			return "False";
		}
	}
	/*
	 * 发送短信
	 * mobiles:多个手机号,逗号分割
	 * */
	public String sendSMS(String mobile,String content)
	{
		StringBuffer repNO = new StringBuffer(64);
		String res="False";
		try{
			String repsns=lYClient.GetRepsn();
			repNO.append(repsns);
			String sendResult = lYClient.sendMT(mobile, content , repsns);
//			String[] repsns = new String[1];
//			String[] mobiles = new String[1];
//			String[] smsContents = new String[1];
//			smsContents[0]=content;
//			mobiles[0]=mobile;
//			repsns[0]=lYClient.GetRepsn();
//			repNO.append(repsns[0]);
//			String sendResult = lYClient.sendMT(mobiles, smsContents, repsns);
			if(sendResult.contains("True")){
				res="True";
			}
			repNO.append("\t").append(mobile).append("\t").append(sendResult);
		}catch(Exception e){
			repNO.append("\t").append(mobile).append("\t").append(e.getMessage());
		}
		log("SendResult\t" + repNO.toString());
		return res;
	}

	public void close()
	{
		try{
			if(lYClient!=null){
				lYClient.Close();
			}
		}catch(Exception e){
			log("SendResult\t" + e.getMessage());
		}
	}
    /**
	 * 格式化输出,多个字段时以\t分隔
	 * 
	 * @param msgs 一个或者多个字段
	 * @return void
	 * @author yaofuyuan
	 * @since 2011-08-09 09:35:00
	 */
    public static void log(Object ...msgs){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	StringBuffer logmsg=new StringBuffer(sdf.format(new Date()));
    	for(int i=0,maxlen=msgs.length;i<maxlen;i++){
    		logmsg.append("\t").append(msgs[i].toString());
    	}
    	System.out.println(logmsg);
    }
}