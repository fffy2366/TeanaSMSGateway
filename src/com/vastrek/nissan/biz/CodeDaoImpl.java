package com.vastrek.nissan.biz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vastrek.nissan.dao.BaseDao;
import com.vastrek.nissan.dao.CodeDao;
import com.vastrek.nissan.entity.Code;
import com.vastrek.nissan.util.HttpUtil;

public class CodeDaoImpl extends BaseDao implements CodeDao{

	public static void main(String[] args) {
		//批量发送邮件
		/*
		CodeDaoImpl impl = new CodeDaoImpl() ;
		List<Code> list = impl.findAllCodeByType("email") ;
		//impl.update("fengxuting@qq.com") ;
		
		for (int i = 0; i < list.size(); i++) {
			//System.out.println(list.get(i).getEmail());
			//System.out.println(list.get(i).getCode());
			HttpUtil.sendEmail(list.get(i).getEmail(), list.get(i).getCode()) ;
			impl.update("email",list.get(i).getEmail()) ;
		}*/
	}
	public List<Code> findAllCodeByType(String type) {
		String sql = "SELECT * FROM code_sms c WHERE type='"+type+"' AND flag = 0 ORDER BY id ASC";
		//System.out.println("sql:"+sql);
		List<Code> list = new ArrayList<Code>();
		super.getConnection();
		ResultSet rs = super.ExecuteQuery(sql, null);
		try {
			while (rs.next()) {
				Code code = new Code() ;
				code.setEmail(rs.getString("email")) ;
				code.setMobile(rs.getString("mobile")) ;
				code.setCode(rs.getString("code")) ;
				list.add(code);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		super.Close();
		System.out.println("size:"+list.size());
		return list;
	}
	public int update(String type,String emailormobile) {
		// TODO Auto-generated method stub
		String sql = "UPDATE code_sms SET flag = 1 WHERE "+type+" = '"+emailormobile+"'" ;
		//System.out.println("sql:"+sql);
		super.getConnection();
		int ret = super.ExecuteUpdate(sql, null) ;
		//if(ret>0){	
		//}
		super.Close();
		return ret;
	} 
	
}
