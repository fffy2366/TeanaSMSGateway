package com.vastrek.nissan.dao;

import java.util.List;

import com.vastrek.nissan.entity.Code;

public interface CodeDao {
	public List<Code> findAllCodeByType(String type);
	public int update(String type,String emailormobile) ;
}
