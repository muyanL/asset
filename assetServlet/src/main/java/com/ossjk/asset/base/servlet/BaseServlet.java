package com.ossjk.asset.base.servlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ossjk.asset.base.util.AjaxReturn;
import com.ossjk.asset.base.util.CommonUtil;
import com.ossjk.asset.base.util.ReflectUtil;

public class BaseServlet extends HttpServlet {
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	public static final String HEADER_ENCODING = "encoding";
	public static final String HEADER_NOCACHE = "no-cache";
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final boolean DEFAULT_NOCACHE = true;

	public void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		// Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		// Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}

	public void setDisableCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}

	public void initResponseHeader(HttpServletResponse resp, String contentType, String... headers) {
		String encoding = DEFAULT_ENCODING;
		boolean noCache = DEFAULT_NOCACHE;
		for (String header : headers) {
			String headerName = StringUtils.substringBefore(header, ":");
			String headerValue = StringUtils.substringAfter(header, ":");

			if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
				encoding = headerValue;
			} else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
				noCache = Boolean.parseBoolean(headerValue);
			} else {
				throw new IllegalArgumentException(headerName + "����һ���Ϸ���header����");
			}
		}
		String fullContentType = contentType + ";charset=" + encoding;
		resp.setContentType(fullContentType);
		if (noCache) {
			setNoCacheHeader(resp);
		}
	}

	public void setNoCacheHeader(HttpServletResponse response) {
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
	}

	public void render(HttpServletResponse resp, String contentType, String content, String... headers) {
		initResponseHeader(resp, contentType, headers);
		try {
			resp.getWriter().write(content);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void renderText(HttpServletResponse resp, String text, String... headers) {
		render(resp, BaseServlet.TEXT_TYPE, text, headers);
	}

	public void renderHtml(HttpServletResponse resp, String html, String... headers) {
		render(resp, BaseServlet.HTML_TYPE, html, headers);
	}

	public void renderJson(HttpServletResponse resp, String jsonString, String... headers) {
		render(resp, BaseServlet.JSON_TYPE, jsonString, headers);
	}

	public void renderAjaxReturn(HttpServletResponse resp, AjaxReturn ajaxReturn, String... headers) {
		render(resp, BaseServlet.JSON_TYPE, JSON.toJSONString(ajaxReturn), headers);
	}

	public void redirect(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			response.sendRedirect(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void forward(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getMethod(HttpServletRequest request) {
		return request.getParameter("method");
	}

	public <T> T getParamtoObject(Class<T> class1, HttpServletRequest request) throws Exception {
		T model = null;
		Field[] fields = class1.getDeclaredFields();
		if (!CommonUtil.isBlank(fields)) {
			model = class1.newInstance();
			for (Field field : fields) {
				String type = field.getType().getName();
				if (!CommonUtil.isBlank(request.getParameter(field.getName()))) {
					if (type.endsWith("String")) {
						ReflectUtil.setFieldValue(model, field.getName(), request.getParameter(field.getName()));
					} else if (type.endsWith("double") || type.endsWith("float") || type.endsWith("short") || type.endsWith("long") || type.endsWith("int") || type.endsWith("Double") || type.endsWith("Float") || type.endsWith("Short") || type.endsWith("Long") || type.endsWith("Integer") || type.endsWith("BigDecimal")) {
						ReflectUtil.setFieldValue(model, field.getName(), new BigDecimal(request.getParameter(field.getName())));
					} else if (type.endsWith("Date")) {
						ReflectUtil.setFieldValue(model, field.getName(), CommonUtil.getDate(request.getParameter(field.getName()), "@ISO6"));
					}
				}

			}
		}
		return model;
	}

	/**
	 * 获取Ip地址
	 * 
	 * @return
	 */
	protected String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
