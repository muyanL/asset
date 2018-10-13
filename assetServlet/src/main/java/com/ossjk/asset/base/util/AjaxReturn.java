package com.ossjk.asset.base.util;

public class AjaxReturn {

	private String code;
	private String msg;
	private Object content;

	public AjaxReturn() {

	}

	public AjaxReturn(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public AjaxReturn(String code, String msg, Object content) {
		this.code = code;
		this.msg = msg;
		this.content = content;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

}
