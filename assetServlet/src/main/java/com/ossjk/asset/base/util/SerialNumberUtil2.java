//package com.ossjk.asset.base.util;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Formatter;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class SerialNumberUtil2 {
//
//	private static SimpleDateFormat yyyyFormat = new SimpleDateFormat("yyyy");
//
//	private static SimpleDateFormat mmFormat = new SimpleDateFormat("MM");
//
//	private static SimpleDateFormat ddFormat = new SimpleDateFormat("dd");
//
//	private static Logger logger = LoggerFactory.getLogger(SerialNumberUtil2.class);
//
//	private static String findNum(String rule) {
//		Pattern pattern = Pattern.compile("(?<=\\{)(.+?)(?=\\})");
//		Matcher matcher = pattern.matcher(rule);
//		if (matcher.find()) {
//			return matcher.group();
//		} else {
//			return null;
//		}
//	}
//
//	private static String handlerY(String rule) {
//		String year = yyyyFormat.format(new Date());
//		String numStr = findNum(rule);
//		if (!CommonUtil.isBlank(numStr)) {
//			try {
//				int num = CommonUtil.int2(numStr);
//				return year.substring(year.length() - num, year.length());
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error(rule + "表达式解释错误。");
//			}
//		} else {
//			return year;
//		}
//		return null;
//	}
//
//	private static String handlerM(String rule) {
//		String mm = mmFormat.format(new Date());
//		if (rule.length() > 0) {
//			return mm;
//		} else {
//			logger.error(rule + "表达式解释错误。");
//		}
//		return null;
//	}
//
//	private static String handlerD(String rule) {
//		String day = ddFormat.format(new Date());
//		if (rule.length() > 0) {
//			return day;
//		} else {
//			logger.error(rule + "表达式解释错误。");
//		}
//		return null;
//	}
//
//	private static String handlerN(String rule) {
//		String numStr = findNum(rule);
//		if (!CommonUtil.isBlank(numStr)) {
//			try {
//				int num = CommonUtil.int2(numStr);
//				return String.format("%0" + num + "d", 0);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error(rule + "表达式解释错误。");
//			}
//		} else {
//			return String.format("%04d", 0);
//		}
//		return null;
//	}
//
//	private static String handlerS(String rule) {
//		return rule.substring(rule.indexOf("S") + 1, rule.length());
//	}
//
//	public static String nextHandler(String rule, String current) {
//		String[] tmpAry = rule.split("%");
//		if (!CommonUtil.isBlank(tmpAry)) {
//			StringBuffer ruleingBuffer = new StringBuffer();
//			for (String tmp : tmpAry) {
//				if (tmp.indexOf("Y") == 0) {
//					ruleingBuffer.append(handlerY(tmp));
//				} else if (tmp.indexOf("M") == 0) {
//					ruleingBuffer.append(handlerM(tmp));
//				} else if (tmp.indexOf("D") == 0) {
//					ruleingBuffer.append(handlerD(tmp));
//				} else if (tmp.indexOf("N") == 0) {
//					String nums = handlerN(tmp);
//					if (!CommonUtil.isBlank(current)) {
//						String serNum = current.substring(current.indexOf(ruleingBuffer.toString()) + ruleingBuffer.toString().length() + 1, ruleingBuffer.toString().length() + nums.length());
//						ruleingBuffer.append(String.format("%0" + nums.length() + "d", 1 + CommonUtil.int2(serNum)));
//					} else {
//						ruleingBuffer.append(nums);
//					}
//
//				} else if (tmp.indexOf("S") == 0) {
//					ruleingBuffer.append(handlerS(tmp));
//				}
//			}
//			return ruleingBuffer.toString();
//		} else {
//			logger.error(rule + "表达式解释错误。");
//		}
//		return null;
//	}
//
//	// public static void main(String[] args) {
//	// SerialNumberUtil2 numberUtil2 = new SerialNumberUtil2();
//	// System.out.println(numberUtil2.nextHandler("%SABC%N{6}%Y{2}",
//	// "ABC00000218"));
//	// }
//
//}