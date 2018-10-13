package com.ossjk.asset.system.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable {

	/**
	* 
	*/
	private BigDecimal id;
	/**
	 * 组织机构id
	 */
	private BigDecimal ogid;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String pwd;
	/**
	 * 性别 1-男、2-女
	 */
	private BigDecimal sex;
	/**
	 * 生日
	 */
	private String birth;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 登录时间
	 */
	private String logintime;
	/**
	 * 登录ip
	 */
	private String loginip;
	/**
	 * 创建时间
	 */
	private Date crtm;
	/**
	 * 修改时间
	 */
	private Date mdtm;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getOgid() {
		return ogid;
	}

	public void setOgid(BigDecimal ogid) {
		this.ogid = ogid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public BigDecimal getSex() {
		return sex;
	}

	public void setSex(BigDecimal sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public Date getCrtm() {
		return crtm;
	}

	public void setCrtm(Date crtm) {
		this.crtm = crtm;
	}

	public Date getMdtm() {
		return mdtm;
	}

	public void setMdtm(Date mdtm) {
		this.mdtm = mdtm;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", ogid=" + ogid + ", name=" + name + ", pwd=" + pwd + ", sex=" + sex + ", birth=" + birth + ", phone=" + phone + ", email=" + email + ", logintime=" + logintime + ", loginip=" + loginip + ", crtm=" + crtm + ", mdtm=" + mdtm + "]";
	}

}
