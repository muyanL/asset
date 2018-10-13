package com.ossjk.asset.base.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用方法类
 * 
 * @Author chair
 * @Version 1.0, 2017年8月20日
 * @See
 * @Since com.jk.bestbaby.util
 * @Description: TODO
 */
public abstract class CommonMethod implements Serializable {
	/**
	 * SimpleDateFormat("yyyy-MM-dd");
	 */
	public static SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * SimpleDateFormat("yyyyMMdd");
	 */
	public static SimpleDateFormat iso0Format = new SimpleDateFormat("yyyyMMdd");
	/**
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	 */
	public static SimpleDateFormat iso1Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	/**
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	 */
	public static SimpleDateFormat iso2Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	/**
	 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	 */
	public static SimpleDateFormat iso3Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	/**
	 * SimpleDateFormat("yyyyMMddHHmmssSSS");
	 */
	public static SimpleDateFormat iso4Format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 * SimpleDateFormat("yyyyMMddHHmmss");
	 */
	public static SimpleDateFormat iso5Format = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 */
	public static SimpleDateFormat iso6Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * SimpleDateFormat("HH:mm:ss");
	 */
	public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	/**
	 * SimpleDateFormat("HHmmss");
	 */
	public static SimpleDateFormat time1Format = new SimpleDateFormat("HHmmss");
	/**
	 * SimpleDateFormat("HHmmssSSS");
	 */
	public static SimpleDateFormat time2Format = new SimpleDateFormat("HHmmssSSS");

	public static String DRL_WORKING_FOLDER = "DRL_WORKING_FOLDER";

	public static Double MAX_AMT_15 = 9999999999999.99;

	public static Double MAX_AMT_18 = 9999999999999999.99;

	public static List<String> FS_MODS = new ArrayList<String>();

	/**
	 * str不为空时去除字符串后面的空格，否则返回null
	 * 
	 * @param str
	 * @return
	 */
	public static String trimString(Object str) {
		if (str != null) {
			return trimSuffix(str.toString());
		}
		return null;
	}

	/**
	 * str是否Date的实例，是返回format后Date，否则去除字符串后的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trimStringDecimal(Object str) {
		if (str instanceof Date) {
			return time2Format.format((Date) str);
		}
		if (str != null) {
			if (str.toString().trim().length() > 0) {
				return trimSuffix(str.toString());
			} else {
				return "0";
			}
		}
		return null;
	}

	/**
	 * 比较日期大小
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareTo(Date date1, Date date2) {
		if (date1 == null) {
			return date2 == null ? 0 : -1;
		} else {
			return date1.compareTo(date2);
		}
	}

	/**
	 * 比较BigDecimal的大小；BigDecimal，用来对超过16位有效位的数进行精确的运算
	 * 
	 * @param dec1
	 * @param dec2
	 * @return
	 */
	public static int compareTo(BigDecimal dec1, BigDecimal dec2) {
		if (dec1 == null) {
			return dec2 == null ? 0 : -1;
		} else {
			return dec1.compareTo(dec2);
		}
	}

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.length() == 0 || " ".equals(str);
	}

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(Object obj) {
		return obj == null || isBlank(obj.toString());
	}

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(Object[] obj) {
		return obj == null || obj.length == 0;
	}

	public static boolean isBlank(List list) {
		return list == null || list.size() == 0;
	}

	public static boolean isBlank(Set set) {
		return set == null || set.size() == 0;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(double str) {
		return str == 0;
	}

	/**
	 * 比较大小，按字母顺序排序
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int compareTo(String str1, String str2) {
		if (str1 == null) {
			if (str2 == null) {
				return 0;
			} else {
				return isBlank(str2) ? 0 : -1;
			}
		} else {
			if (str1.length() == 0) {
				return isBlank(str2) ? 0 : -1;
			}
			if (str2 == null || isBlank(str2)) {
				return 1;
			}
			return str1.compareTo(str2);
		}
	}

	/**
	 * 去除字符串后面的空格
	 * 
	 * @param s
	 * @return
	 */
	public static String trimSuffix(String s) {

		int len = s.length();
		char[] val = s.toCharArray();
		while (len > 0 && (val[len - 1] <= ' ')) {
			len--;
		}
		return ((len > 0) && len <= s.length()) ? s.substring(0, len) : s;
	}

	/**
	 * int转String，length>i，则在i前面添加0
	 * 
	 * @param i
	 *            数量
	 * @param length
	 *            数量长度
	 * @return
	 */
	public static String intToString(int i, int length) {
		String result = String.valueOf(i);
		try {
			while (result.length() < length) {
				result = "0" + result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 检查字符串传入的是否纯数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否日期
	 * 
	 * @param strDate
	 * @param dateFormat
	 * @return
	 */
	public static boolean isDate(String strDate, String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		try {
			Date tmpDate = df.parse(strDate);
			String strTempDate = df.format(tmpDate);
			if (!strTempDate.equals(strDate)) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 *            需要截取的字符串
	 * @param length
	 *            截取的长度
	 * @return
	 */
	public static String strToL(String str, int length) {
		try {
			if (str == null) {
				str = "";
			}
			if (str.length() > length) {
				str = str.substring(0, length);
			}
			while (str.length() < length) {
				str = str + " ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 得到字符串 为空时返回空字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		return obj == null ? "" : obj.toString();
	}
}
