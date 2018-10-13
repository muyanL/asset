package com.ossjk.asset.system.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ossjk.asset.base.servlet.BaseServlet;
import com.ossjk.asset.base.util.CommonUtil;
import com.ossjk.asset.base.util.Constant;
import com.ossjk.asset.system.dao.UserDao;
import com.ossjk.asset.system.pojo.User;

public class IndexServlet extends BaseServlet {

	private Logger logger = Logger.getLogger(IndexServlet.class);
	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String method = getMethod(req);
		if ("toIndex".equals(method)) {
			toIndex(req, resp);
		} else if ("toLogin".equals(method)) {
			toLogin(req, resp);
		} else if ("login".equals(method)) {
			login(req, resp);
		} else if ("logout".equals(method)) {
			logout(req, resp);
		} else if ("toError".equals(method)) {
			toError(req, resp);
		} else if ("toDesktop".equals(method)) {
			toDesktop(req, resp);
		}
	}

	/**
	 * 登出
	 * 
	 * @param req
	 * @param resp
	 */
	public void logout(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.getSession().removeAttribute(Constant.SESSION_USER_KEY);
			forward(req, resp, "/WEB-INF/page/login.jsp");
		} catch (Exception e) {
			logger.error("", e);
			redirect(req, resp, req.getContextPath() + "/system/index.do?method=toError");
		}
	}

	/**
	 * 登录
	 * 
	 * @param req
	 * @param resp
	 */
	public void login(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String name = (String) req.getParameter("name");
			String pwd = (String) req.getParameter("pwd");
			User user = userDao.selectOne("select * from user where name = ?  and pwd = ? ", name, pwd);
			if (!CommonUtil.isBlank(user)) {
				// 登录成功
				req.getSession().setAttribute(Constant.SESSION_USER_KEY, user);

				User user2 = new User();
				user2.setId(user.getId());
				// 更新ip
				user2.setLoginip(getIpAddr(req));
				// 以及登陆时间
				user2.setLogintime(CommonUtil.date6());
				userDao.updateBySelected(user2);
				redirect(req, resp, req.getContextPath() + "/system/index.do?method=toIndex");
			} else {
				// 登录失败
				req.setAttribute("msg", "登录失败");
				forward(req, resp, "/WEB-INF/page/login.jsp");
			}
		} catch (Exception e) {
			logger.error("", e);
			redirect(req, resp, req.getContextPath() + "/system/index.do?method=toError");
		}
	}

	/**
	 * 去登录
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void toLogin(HttpServletRequest req, HttpServletResponse resp) {
		try {
			forward(req, resp, "/WEB-INF/page/login.jsp");
		} catch (Exception e) {
			logger.error("", e);
			redirect(req, resp, req.getContextPath() + "/system/index.do?method=toError");
		}
	}

	/**
	 * 去主页
	 * 
	 * @param req
	 * @param resp
	 */
	public void toIndex(HttpServletRequest req, HttpServletResponse resp) {
		try {
			forward(req, resp, "/WEB-INF/page/index.jsp");
		} catch (Exception e) {
			logger.error("", e);
			redirect(req, resp, req.getContextPath() + "/system/index.do?method=toError");
		}
	}

	/**
	 * 去桌面
	 * 
	 * @param req
	 * @param resp
	 */
	public void toDesktop(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setAttribute("props", System.getProperties());
			forward(req, resp, "/WEB-INF/page/desktop.jsp");
		} catch (Exception e) {
			logger.error("", e);
			redirect(req, resp, req.getContextPath() + "/system/index.do?method=toError");
		}
	}

	/**
	 * 去错误页
	 * 
	 * @param req
	 * @param resp
	 */
	public void toError(HttpServletRequest req, HttpServletResponse resp) {
		forward(req, resp, "/WEB-INF/page/error.jsp");
	}

}
