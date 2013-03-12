package com.vastrek.nissan.util;
/**
 *HttpUtil.java
 *下午05:26:36
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author frank
 * @description 发送http请求
 * @filename HttpUtil.java
 * @time 2013-2-26 下午05:26:36
 * @version 1.0
 */
public class HttpUtil {
	public static void main(String[] args) {
		sendEmail("fengxuting@qq.com","TEST") ;
	}
	public static String sendEmail(String address,String code){
		Map<String, String> params = new HashMap<String, String>() ;
		//TokenId=string&mailTo=string&mailSubject=string&mailContent=string&senderName=string&senderEmail=string
		String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
		"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
		"<head>"+
		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+
		"<title>天籁</title>"+
		"</head>"+
		"<body>"+
		"<table width=\"666\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+
		"  <tr>"+
		"    <td colspan=\"3\"><img src=\"http://neodna.dongfeng-nissan.com.cn/edm/images/01.jpg\" alt=\"\" /></td>"+
		"  </tr>"+
		"  <tr>"+
		"    <td width=\"237\"><img src=\"http://neodna.dongfeng-nissan.com.cn/edm/images/02.jpg\" alt=\"\" /></td>"+
		"    <td width=\"192\" style=\"background:#fff;color:#c51733;font-size:20px;font-weight:bold;text-align:center;\">"+code+"</td>"+
		"    <td width=\"237\"><img src=\"http://neodna.dongfeng-nissan.com.cn/edm/images/03.jpg\" alt=\"\" /></td>"+
		"  </tr>"+
		"  <tr>"+
		"    <td colspan=\"3\"><img src=\"http://neodna.dongfeng-nissan.com.cn/edm/images/04.jpg\" alt=\"\" /></td>"+
		"  </tr>"+
		"  <tr>"+
		"    <td colspan=\"3\"><table width=\"666\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+
		"        <tr>"+
		"          <td><img src=\"http://neodna.dongfeng-nissan.com.cn/edm/images/05.jpg\" alt=\"\" /></td>"+
		"          <td><a href=\"http://teana.dongfeng-nissan.com.cn\"><img src=\"http://neodna.dongfeng-nissan.com.cn/edm/images/06.jpg\" alt=\"\" style=\"border:0 none;\" /></a></td>"+
		"          <td><img src=\"http://neodna.dongfeng-nissan.com.cn/edm/images/07.jpg\" alt=\"\" /></td>"+
		"        </tr>"+
		"      </table></td>"+
		"  </tr>"+
		"  <tr>"+
		"    <td colspan=\"3\"><img src=\"http://neodna.dongfeng-nissan.com.cn/edm/images/08.jpg\" alt=\"\" /></td>"+
		"  </tr>"+
		"</table>"+
		"</body>"+
		"</html>" ;
		params.put("TokenId", "DFL2013-MG-01") ;
		params.put("mailTo", address) ;
		params.put("mailSubject", "天籁任务 正式启动 你准备好了吗？") ;
		params.put("mailContent", content) ;
		params.put("senderName", "东风日产-新天籁") ;
		params.put("senderEmail", "npassstg@dongfeng-nissan.com.cn") ;
		
		System.out.println(http("http://202.96.191.174/DFLMailService/MailService.asmx/SendEmailWithSender", params));
		
		return "success" ;
	}
	public static String http(String url, Map<String, String> params) {
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}
		//System.out.println("send_url:" + url);
		//System.out.println("send_data:" + sb.toString());
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(con
					.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(con
					.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}

}