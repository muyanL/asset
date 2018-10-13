package com.ossjk.asset.system.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ossjk.asset.base.servlet.BaseServlet;
import com.ossjk.asset.base.util.AjaxReturn;
import com.ossjk.asset.base.util.CommonUtil;
import com.ossjk.asset.base.util.Constant;
import com.ossjk.asset.system.dao.UserDao;
import com.ossjk.asset.system.pojo.User;

public class UserServlet extends BaseServlet {

	private Logger logger = Logger.getLogger(UserServlet.class);
	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String method = getMethod(req);
		if ("list".equals(method)) {
			list(req, resp);
		} else if ("toInsert".equals(method)) {
			toInsert(req, resp);
		} else if ("insert".equals(method)) {
			insert(req, resp);
		} else if ("toUpdate".equals(method)) {
			toUpdate(req, resp);
		} else if ("update".equals(method)) {
			update(req, resp);
		} else if ("delete".equals(method)) {
			delete(req, resp);
		} else if ("batchDelete".equals(method)) {
			batchDelete(req, resp);
		}
	}

	/**
	 * 列表
	 * 
	 * @param req
	 * @param resp
	 */
	public void list(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setAttribute("records", userDao.selectAll());
			forward(req, resp, "/WEB-INF/page/system/user/list.jsp");
		} catch (Exception e) {
			logger.error("", e);
			redirect(req, resp, req.getContextPath() + "/system/index.do?method=toError");
		}

	}

	/**
	 * 去增加
	 * 
	 * @param req
	 * @param resp
	 */
	public void toInsert(HttpServletRequest req, HttpServletResponse resp) {
		try {
			forward(req, resp, "/WEB-INF/page/system/user/edit.jsp");
		} catch (Exception e) {
			logger.error("", e);
			redirect(req, resp, req.getContextPath() + "/system/index.do?method=toError");
		}
	}

	/**
	 * 去更新
	 * 
	 * @param req
	 * @param resp
	 */
	public void toUpdate(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setAttribute("record", userDao.selectById(req.getParameter("id")));
			forward(req, resp, "/WEB-INF/page/system/user/edit.jsp");
		} catch (Exception e) {
			logger.error("", e);
			redirect(req, resp, req.getContextPath() + "/system/index.do?method=toError");
		}
	}

	/**
	 * 增加
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void insert(HttpServletRequest req, HttpServletResponse resp) {
		try {
			User user = getParamtoObject(User.class, req);
			user.setCrtm(CommonUtil.getDate());
			user.setMdtm(CommonUtil.getDate());
			if (userDao.insert(user) > 0) {
				renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS));
			} else {
				renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_ERROR, Constant.RETURN_MESSAGE_ERROR));
			}
		} catch (Exception e) {
			logger.error("", e);
			renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_EXCEPTION, Constant.RETURN_MESSAGE_EXCEPTION));
		}
	}

	/**
	 * 更新
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void update(HttpServletRequest req, HttpServletResponse resp) {
		try {
			User user = getParamtoObject(User.class, req);
			user.setMdtm(CommonUtil.getDate());
			if (userDao.updateBySelected(user) > 0) {
				renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS));
			} else {
				renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_ERROR, Constant.RETURN_MESSAGE_ERROR));
			}
		} catch (Exception e) {
			logger.error("", e);
			renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_EXCEPTION, Constant.RETURN_MESSAGE_EXCEPTION));
		}
	}

	/**
	 * 删除
	 * 
	 * @param req
	 * @param resp
	 */
	public void delete(HttpServletRequest req, HttpServletResponse resp) {
		try {
			if (userDao.delete(req.getParameter("id")) > 0) {
				renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS));
			} else {
				renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_ERROR, Constant.RETURN_MESSAGE_ERROR));
			}
		} catch (Exception e) {
			logger.error("", e);
			renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_EXCEPTION, Constant.RETURN_MESSAGE_EXCEPTION));
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param req
	 * @param resp
	 */
	public void batchDelete(HttpServletRequest req, HttpServletResponse resp) {
		try {
			if (userDao.batchDelete(req.getParameterValues("ids")) > 0) {
				renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_SUCCESS, Constant.RETURN_MESSAGE_SUCCESS));
			} else {
				renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_ERROR, Constant.RETURN_MESSAGE_ERROR));
			}
		} catch (Exception e) {
			logger.error("", e);
			renderAjaxReturn(resp, new AjaxReturn(Constant.RETURN_CODE_EXCEPTION, Constant.RETURN_MESSAGE_EXCEPTION));
		}
	}

}
