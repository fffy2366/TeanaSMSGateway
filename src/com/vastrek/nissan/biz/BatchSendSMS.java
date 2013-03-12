package com.vastrek.nissan.biz;

import java.util.List;

import com.nissan.sms.SmsSender;
import com.vastrek.nissan.entity.Code;
import com.vastrek.nissan.util.HttpUtil;

public class BatchSendSMS {
	public static void main(String[] args) {
	}
	public static void sendSms(){
		CodeDaoImpl impl = new CodeDaoImpl() ;
		List<Code> list = impl.findAllCodeByType("mobile") ;

		SmsSender sender = new SmsSender() ;
				
		for (int i = 0; i < list.size(); i++) {
			String mobiles = list.get(i).getMobile() ; 
			String content  = "天籁任务，正式启动 即刻激活你的NEO DNA，与天籁共同开创新的世界，你准备好了吗？这是您的专属任务激活码:"+list.get(i).getCode()+" 马上挑战http://teana.dongfeng-nissan.com.cn/" ;
			String res=sender.getConnect();
			if(res.contains("True")){
				res=sender.sendSMS(mobiles,content);
			}else{
				res="False";
			}
			
			SmsSender.log("endSms",mobiles,content,res);
			
			impl.update("mobile",list.get(i).getMobile()) ;
		}
	}
}
