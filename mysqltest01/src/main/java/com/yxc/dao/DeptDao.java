package com.yxc.dao;


import com.yxc.bean.Dept;

public interface DeptDao {

	public Dept findByDname(String Dname);
	public Dept findByDeptNo(Integer deptno);
	
}
