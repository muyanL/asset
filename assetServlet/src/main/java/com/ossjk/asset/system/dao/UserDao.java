package com.ossjk.asset.system.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.ossjk.asset.base.dao.BaseDao;
import com.ossjk.asset.system.pojo.User;

public class UserDao extends BaseDao<User> {

	public static void main(String[] args) {
		UserDao userDao = new UserDao();
		try {
			userDao.autoGenEntityTitle();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
