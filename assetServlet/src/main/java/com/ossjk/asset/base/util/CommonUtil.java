package com.ossjk.asset.base.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 通用工具类
 * 
 * @Author chair
 * @Version 1.0, 2017年8月20日
 * @See
 * @Since com.jk.bestbaby.util
 * @Description: TODO
 */
public abstract class CommonUtil extends CommonMethod {

	/**
	 * Java将Unix时间戳转换成指定格式日期字符串
	 * 
	 * @param timestampString
	 *            时间戳 如："1473048265";
	 * @param formats
	 *            要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
	 * 
	 * @return 返回结果 如："2016-09-05 16:06:42";
	 */
	public static String TimeStamp2Date(String timestampString, String formats) {
		if (isBlank(formats))
			formats = "yyyy-MM-dd HH:mm:ss";
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
		return date;
	}

	/**
	 * 判断List有没值
	 * 
	 * @param list
	 * @return
	 */
	public static Boolean checkList(List list) {

		return (list != null || list.size() > 0);
	}

	/**
	 * 通过反射生成javaBean
	 * 
	 * @param beanObj
	 */
	public static Object cloneJavaBean(Object beanObj) {
		try {
			Method cloneMethod = beanObj.getClass().getMethod("clone");
			Object javaBean = cloneMethod.invoke(beanObj);
			return javaBean;
		} catch (Exception ex) {
			//
		}
		return null;
	}

	/**
	 * Json 双引号转单引号
	 * 
	 * @return
	 */
	public static String jsonDoubleToSingle(String json) {
		if (!isBlank(json)) {
			return json.replace("\"", "\\\"");
		}
		return null;
	}

	/**
	 * 拷贝对象方法（适合同一类型的对象复制）
	 * 
	 * @param objSource
	 *            源对象
	 * @param clazz
	 *            目标类
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T cloneJavaBean(Object objSource, Class<T> clazz) throws InstantiationException, IllegalAccessException {
		if (null == objSource)
			return null;// 如果源对象为空，则直接返回null
		T objDes = clazz.newInstance();
		// 获得源对象所有属性
		Field[] fields = clazz.getDeclaredFields();
		// 循环遍历字段，获取字段对应的属性值
		for (Field field : fields) {
			// 如果不为空，设置可见性，然后返回
			field.setAccessible(true);
			try {
				// 设置字段可见，即可用get方法获取属性值。
				field.set(objDes, field.get(objSource));
			} catch (Exception e) {
				// logger.error("执行{}类的{}属性的set方法时出错。{}",clazz.getSimpleName(),field.getName(),e);
			}
		}
		return objDes;
	}

	/**
	 * 计算String 长度.
	 * 
	 * @param dao
	 * @return
	 */
	public static int size(String dao) {
		if (dao != null) {
			return dao.length();
		}
		return 0;
	}

	/**
	 * 计算BigDecimal 长度.
	 * 
	 * @param dao
	 * @return
	 */
	public static int size(BigDecimal dao) {
		if (dao != null) {
			return 17;
		}
		return 0;
	}

	/**
	 * 数字类型截位.
	 * 
	 * @param d
	 *            被截位的数字
	 * @param len
	 *            长度
	 * @param prec
	 *            需要精确到小数点以后几位。
	 * @return
	 */
	public static double dech(double d, int len, int prec) {
		BigDecimal bigDecimal = new BigDecimal(d);
		if (bigDecimal != null) {
			bigDecimal = bigDecimal.setScale(prec, RoundingMode.HALF_UP);
		}
		return bigDecimal.doubleValue();
	}

	/**
	 * 字符串转换为数字.
	 * 
	 * @param ss
	 * @return
	 */
	public static BigDecimal toBigDecimal(String ss) {
		BigDecimal ret = null;
		if (ss != null && ss.trim().length() > 0) {
			try {
				ret = new BigDecimal(ss.trim());
			} catch (Exception ex) {
			}
		}
		return ret;
	}

	/**
	 * 求两个直接的日期差,月份.
	 * 
	 * *MSECONDS|*SECONDS|*MINUTES|*HOURS|*DAYS|*MONTHS|*YEARS
	 * 
	 * @param tDate
	 *            日期格式"20150404"
	 * @param txDate
	 *            日期格式"20150404"
	 * @param type
	 *            type 选择 DAYS||MONTHS||YEARS
	 * @return
	 */
	public static int diff(String tDate, String txDate, String type) {
		if (txDate != null && tDate != null) {
			DateTime txDT = DateTime.parse(txDate, ISODateTimeFormat.basicDate());
			DateTime tDT = DateTime.parse(tDate, ISODateTimeFormat.basicDate());
			if (type.toUpperCase().endsWith("DAYS")) {
				return Days.daysBetween(txDT, tDT).getDays();
			} else if (type.toUpperCase().endsWith("MONTHS")) {
				return Months.monthsBetween(txDT, tDT).getMonths();
			} else if (type.toUpperCase().endsWith("YEARS")) {
				return Years.yearsBetween(txDT, tDT).getYears();
			} else if (type.toUpperCase().endsWith("YEARS")) {
				return Years.yearsBetween(txDT, tDT).getYears();
			}
		}
		return 0;
	}

	/**
	 * 日期加法.
	 * 
	 * @param txDate
	 * @param proi
	 *            为负数时为减少
	 * @param type
	 *            *DAYS|*MONTHS|*YEARS
	 * @return
	 */
	public static Date plus(Date txDate, int proi, String type) {
		if (txDate != null) {
			DateTime txDT = new DateTime(txDate);
			if (type.toUpperCase().endsWith("DAYS")) {
				return txDT.plusDays(proi).toDate();
			} else if (type.toUpperCase().endsWith("MONTHS")) {
				return txDT.plusMonths(proi).toDate();
			} else if (type.toUpperCase().endsWith("YEARS")) {
				return txDT.plusYears(proi).toDate();
			}
		}
		return txDate;
	}

	/**
	 * 日期加法
	 * 
	 * @param txDate
	 *            日期格式"20150404"
	 * @param proi
	 *            DAYS|加上几天|MONTHS|加上几月|YEARS|加上几年
	 * @param type
	 *            选择 DAYS||MONTHS||YEARS
	 * @return
	 */
	public static String plus(String txDate, int proi, String type) {
		if (txDate != null) {
			DateTime txDT = DateTime.parse(txDate, ISODateTimeFormat.basicDate());
			if (type.toUpperCase().endsWith("DAYS")) {
				return txDT.plusDays(proi).toString(ISODateTimeFormat.basicDate());
			} else if (type.toUpperCase().endsWith("MONTHS")) {
				return txDT.plusMonths(proi).toString(ISODateTimeFormat.basicDate());
			} else if (type.toUpperCase().endsWith("YEARS")) {
				return txDT.plusYears(proi).toString(ISODateTimeFormat.basicDate());
			}
		}
		return txDate;
	}

	/**
	 * 日期加法
	 * 
	 * @param txDate
	 *            日期格式"20150404"
	 * @param proi
	 *            DAYS|加上几天|MONTHS|加上几月|YEARS|加上几年
	 * @param type
	 *            选择 DAYS||MONTHS||YEARS
	 * @return
	 */
	public static Date plusDate(String txDate, int proi, String type) {
		if (txDate != null) {
			DateTime txDT = DateTime.parse(txDate, ISODateTimeFormat.basicDate());
			if (type.toUpperCase().endsWith("DAYS")) {
				return txDT.plusDays(proi).toDate();
			} else if (type.toUpperCase().endsWith("MONTHS")) {
				return txDT.plusMonths(proi).toDate();
			} else if (type.toUpperCase().endsWith("YEARS")) {
				return txDT.plusYears(proi).toDate();
			}
			return txDT.toDate();
		}
		return null;
	}

	/**
	 * 日期加法
	 * 
	 * @param txDate
	 *            日期格式"20150404"
	 * @param proi
	 *            DAYS|加上几天|MONTHS|加上几月|YEARS|加上几年
	 * @param type
	 *            选择 DAYS||MONTHS||YEARS
	 * @return
	 */
	public static String plus(double txDate, int proi, String type) {
		return plus((int) txDate, proi, type);
	}

	/**
	 * 日期加法
	 * 
	 * @param txDate
	 *            日期格式"20150404"
	 * @param proi
	 *            DAYS|加上几天|MONTHS|加上几月|YEARS|加上几年
	 * @param type
	 *            选择 DAYS||MONTHS||YEARS
	 * @return
	 */
	public static String plus(int txDate, int proi, String type) {
		return plus("" + txDate, proi, type);
	}

	/**
	 * 日期加法
	 * 
	 * @param txDate
	 *            日期格式"20150404"
	 * @param proi
	 *            DAYS|加上几天|MONTHS|加上几月|YEARS|加上几年
	 * @param type
	 *            选择 DAYS||MONTHS||YEARS
	 * @return
	 */
	public static String plus(BigDecimal txDate, int proi, String type) {
		if (txDate != null) {
			return plus(txDate.intValue(), proi, type);
		}
		return null;
	}

	/**
	 * 
	 * 日期加法 2个参数相加；如果需要日期加法的话，请按照格式（日期“20140202”），如需要添加一个月，则需要加100 ，所以这个，请按普通加法使用
	 * 
	 * @param txDate
	 * @param proi
	 *            // BigDecimal(int) 创建一个具有参数所指定整数值的对象。 //BigDecimal(double)
	 *            创建一个具有参数所指定双精度值的对象。 //BigDecimal(long) 创建一个具有参数所指定长整数值的对象。
	 *            //BigDecimal(String) 创建一个具有参数所指定以字符串表示的数值的对象。
	 * @return
	 */
	public static BigDecimal plus(BigDecimal txDate, BigDecimal proi) {
		if (txDate == null) {
			txDate = new BigDecimal(0);
		}
		if (proi == null) {
			proi = new BigDecimal(0);
		}
		return txDate.add(proi);
	}

	/**
	 * 日期减法 2个参数相加；如果需要日期减法的话，请按照格式（日期“20140202”），如需要减一个月，则需要加100 ，所以这个，请按普通减法使用
	 * 
	 * @param txDate
	 * @param proi
	 *            // BigDecimal(int) 创建一个具有参数所指定整数值的对象。 //BigDecimal(double)
	 *            创建一个具有参数所指定双精度值的对象。 //BigDecimal(long) 创建一个具有参数所指定长整数值的对象。
	 *            //BigDecimal(String) 创建一个具有参数所指定以字符串表示的数值的对象。
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal txDate, BigDecimal proi) {
		if (txDate == null) {
			txDate = new BigDecimal(0);
		}
		if (proi == null) {
			proi = new BigDecimal(0);
		}
		return txDate.subtract(proi);
	}

	/**
	 * 求两个直接的日期差,月份.
	 * 
	 * @param tDate
	 * @param txDate
	 * @param type
	 *            选择 DAYS||MONTHS||YEARS||HOURS||MSECONDS||MINUTES||SECONDS
	 * @return
	 */
	public static int diff(Date tDate, Date txDate, String type) {
		if (txDate != null && tDate != null) {
			DateTime txDT = new DateTime(txDate);
			DateTime tDT = new DateTime(tDate);
			if (type != null) {
				if (type.toUpperCase().endsWith("DAYS") || type.toUpperCase().endsWith("D")) {
					return Days.daysBetween(txDT, tDT).getDays();
				} else if (type.toUpperCase().endsWith("MONTHS")) {
					return Months.monthsBetween(txDT, tDT).getMonths();
				} else if (type.toUpperCase().endsWith("YEARS") || type.toUpperCase().endsWith("Y")) {
					return Years.yearsBetween(txDT, tDT).getYears();
				} else if (type.toUpperCase().endsWith("HOURS") || type.toUpperCase().endsWith("H")) {
					return Hours.hoursBetween(txDT, tDT).getHours();
				} else if (type.toUpperCase().endsWith("MSECONDS")) {
					return Seconds.secondsBetween(txDT, tDT).getSeconds();
				} else if (type.toUpperCase().endsWith("MINUTES")) {
					return Minutes.minutesBetween(txDT, tDT).getMinutes();
				} else if (type.toUpperCase().endsWith("SECONDS") || type.toUpperCase().endsWith("S")) {
					return Seconds.secondsBetween(txDT, tDT).getSeconds();
				}
			}
		}
		return 0;
	}

	/**
	 * 按int[]截取String，int[]长度就是要截取多少次，int[1]就是截取字符串的长度
	 * 
	 * @see int[]a={1,2,3}; String s[] = toArray("sssslllppoojjj", a); for (String
	 *      string : s) { System.out.println(string.toString()); } 输出s ss sll
	 * @param source
	 *            String
	 * @param lens
	 *            int[]
	 * @return
	 */
	public static String[] toArray(String source, int[] lens) {
		if (source == null || lens.length == 0) {
			return new String[0];
		}

		int sLen = source.length();
		int splitLen = lens.length;
		String[] subStrings = new String[splitLen];
		for (int i = 0, j = 0; i < splitLen; i++) {
			if (sLen >= j + lens[i]) {
				subStrings[i] = source.substring(j, j + lens[i]);
			} else {
				subStrings[i] = source.substring(j, sLen);
				break;
			}

			j += lens[i];
		}

		return subStrings;
	}

	/**
	 * 将字符串转换为byte[]
	 * 
	 * @param s
	 * @param len
	 *            byte长度
	 * @param charset
	 *            字符集
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static List<byte[]> toByteArray(String s, int len, String charset) throws UnsupportedEncodingException {
		return toByteArray(s, len, charset, 1);
	}

	/**
	 * 将字符串转换为byte[]
	 * 
	 * @param s
	 * @param len
	 *            byte长度
	 * @param charset
	 *            字符集
	 * @param agliment
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static List<byte[]> toByteArray(String s, int len, String charset, int agliment) throws UnsupportedEncodingException {
		List<byte[]> target = new ArrayList<byte[]>();
		if (s != null) {
			byte[] bytes = s.getBytes(charset);
			byte[] buf = new byte[len];
			int length = bytes.length;
			int j = 0;
			if (agliment == 0) { // 右对齐
				int i = length - 1;
				for (; i >= 0; i--) {
					if (j == len) {
						target.add(0, buf);
						j = 0;
						buf = new byte[len];
					}
					j++;
					buf[len - j] = bytes[i];
				}
				// 前缀加空格
				j++;
				if (len > j) {
					for (; j <= len; j++) {
						buf[len - j] = ' ';
					}
					target.add(0, buf);
				}
			} else {
				int i = 0;
				for (; i < length; i++) {
					if (j == len) {
						target.add(0, buf);
						j = 0;
						buf = new byte[len];
					}
					buf[j] = bytes[i];
					j++;
				}

				// 后缀加空格
				if (len > j) {
					for (; j < len; j++) {
						buf[j] = ' ';
					}
					target.add(0, buf);
				}
			}
		}
		return target;
	}

	/**
	 * 把长字符串变成数组.按每个字段长度为len
	 * 
	 * @param source
	 *            String
	 * @param len
	 *            指定长度
	 * @return
	 */
	public static String[] toArray(String source, int len) {
		if (source != null) {
			int length = source.length();
			String[] strings = new String[0];
			StringBuffer buffer = new StringBuffer();

			for (int i = 0, j = 0; i < length; i++) {
				if (j < len) {
					buffer.append(source.charAt(i));
				} else {
					String[] tmp = new String[strings.length + 1];
					System.arraycopy(strings, 0, tmp, 0, strings.length);
					tmp[strings.length] = buffer.toString();
					strings = tmp;
					buffer = new StringBuffer();
					buffer.append(source.charAt(i));
					j = 0;
				}
				j++;
			}

			String[] tmp = new String[strings.length + 1];
			System.arraycopy(strings, 0, tmp, 0, strings.length);
			tmp[strings.length] = buffer.toString();
			strings = tmp;
			return strings;
		}
		return new String[0];
	}

	/**
	 * 数字类型截位.
	 * 
	 * @param bigDecimal
	 *            被截位的数字
	 * @param len
	 *            长度
	 * @param prec
	 *            需要精确到小数点以后几位。
	 * @return
	 */
	public static BigDecimal dech(BigDecimal bigDecimal, int len, int prec) {
		if (bigDecimal != null) {
			bigDecimal = bigDecimal.setScale(prec, RoundingMode.HALF_UP);
		}
		return bigDecimal;
	}

	/**
	 * 数字类型截位..
	 * 
	 * @param bigDecimal
	 *            数字
	 * @param len
	 * @param prec
	 *            在bigDecimal后添加小数点，添加相应长度的0
	 * @return
	 */
	public static BigDecimal dec(Object bigDecimal, int len, int prec) {
		if (bigDecimal != null && bigDecimal.toString().trim().length() > 0) {
			BigDecimal dec = new BigDecimal(bigDecimal.toString().trim());
			return dec.setScale(prec, RoundingMode.HALF_DOWN);
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * 数字类型截位.ISO.
	 * 
	 * @param bigDecimal
	 * @param iso
	 * @return
	 */
	public static BigDecimal dec(Object bigDecimal, String iso) {
		if (bigDecimal instanceof Date) {
			String dateDec = char2(bigDecimal, iso);
			return new BigDecimal(dateDec);
		}
		return new BigDecimal(0);
	}

	/**
	 * 数字类型截位..
	 * 
	 * @param bigDecimal
	 * @param len
	 * @param prec
	 * @return
	 */
	// public static double dec(Object bigDecimal, int len, int prec) {
	// if (bigDecimal != null) {
	// return dech(new BigDecimal(bigDecimal.toString()), len,
	// prec).doubleValue();
	// } else {
	// return 0;
	// }
	// }
	/**
	 * 转换为字符串.
	 * 
	 * @param obj
	 * @return
	 */
	public static String char2(Object obj, int len, int prec) {
		if (obj instanceof Double || obj instanceof Integer || obj instanceof Long || obj instanceof Float || obj instanceof BigDecimal) {
			BigDecimal bigDecimal;
			if (obj instanceof Double) {
				bigDecimal = new BigDecimal((Double) obj);
			} else if (obj instanceof Integer) {
				bigDecimal = new BigDecimal((Integer) obj);
			} else if (obj instanceof Float) {
				bigDecimal = new BigDecimal((Float) obj);
			} else if (obj instanceof Long) {
				bigDecimal = new BigDecimal((Long) obj);
			} else {
				bigDecimal = (BigDecimal) obj;
			}
			bigDecimal = bigDecimal.setScale(prec, RoundingMode.HALF_UP);
			return bigDecimal.toString();
		}
		return char2(obj);
	}

	/**
	 * 转换为字符串.
	 * 
	 * @param obj
	 * @return
	 */
	public static String char2(double obj) {
		return "" + obj;
	}

	/**
	 * 转换为字符串.
	 * 
	 * @param obj
	 * @return
	 */
	public static String char2(int obj) {
		return "" + obj;
	}

	/**
	 * 转换为字符串.
	 * 
	 * @param obj
	 * @return
	 */
	public static String char2(long obj) {
		return "" + obj;
	}

	/**
	 * 转换为字符串.
	 * 
	 * @param obj
	 * @return
	 */
	public static String char2(float obj) {
		return "" + obj;
	}

	/**
	 * 转换为字符串.
	 * 
	 * @param obj
	 * @return
	 */
	public static String char2(Object obj) {
		if (obj instanceof Double || obj instanceof Integer || obj instanceof Long) {
			BigDecimal bigDecimal;
			if (obj instanceof Double) {
				bigDecimal = new BigDecimal((Double) obj);
				return bigDecimal.toString();
			} else if (obj instanceof Integer) {
				return ((Integer) obj).toString();
			} else {
				return ((Long) obj).toString();
			}
		}
		if (obj != null) {
			return obj.toString();
		}
		return "";
	}

	/**
	 * 格式化日期
	 * 
	 * @param obj
	 *            日期
	 * @param format
	 * @ISO0 SimpleDateFormat("yyyyMMdd");
	 * @ISO SimpleDateFormat("yyyy-MM-dd");
	 * @ISO2 SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
	 * @ISO1 SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	 * @ISO3 SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	 * @return
	 */
	public static String char2(Object obj, String format) {
		if (obj != null) {
			if (obj instanceof Date && format != null) {
				if (format.toUpperCase().endsWith("ISO0")) {
					return iso0Format.format((Date) obj);
				} else if (format.toUpperCase().endsWith("ISO")) {
					return isoFormat.format((Date) obj);
				} else if (format.toUpperCase().endsWith("ISO2")) {
					return iso2Format.format((Date) obj);
				} else if (format.toUpperCase().endsWith("ISO1")) {
					return iso1Format.format((Date) obj);
				} else if (format.toUpperCase().endsWith("ISO3")) {
					return iso3Format.format((Date) obj);
				} else if (format.toUpperCase().endsWith("ISO4")) {
					return iso4Format.format((Date) obj);
				}
			}
			return char2(obj);
		}
		return "";
	}

	/**
	 * 读取表 锁表 状态.
	 * 
	 * @param objBean
	 * @return
	 */
	public static String status(Object objBean) {
		return "0";
	}

	/**
	 * 获取当前时间
	 * 
	 * 如: HH:mm:ss
	 * 
	 * @return
	 */
	public static String time() {
		return timeFormat.format(new Date());
	}

	/**
	 * 格式化时间
	 * 
	 * 如: HH:mm:ss
	 * 
	 * @return
	 */
	public static String time(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return timeFormat.format(date);
	}

	/**
	 * 获取当前时间
	 * 
	 * 如: HHmmss
	 * 
	 * @return
	 */
	public static String time1() {
		return time1Format.format(new Date());
	}

	/**
	 * 格式化时间
	 * 
	 * 如: HHmmss
	 * 
	 * @return
	 */
	public static String time1(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return time1Format.format(date);
	}

	/**
	 * 获取当前时间
	 * 
	 * 如: HHmmssSSS
	 * 
	 * @return
	 */
	public static String time2() {
		return time2Format.format(new Date());
	}

	/**
	 * 格式化时间
	 * 
	 * 如: HHmmssSSS
	 * 
	 * @return
	 */
	public static String time2(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return time2Format.format(date);
	}

	/**
	 * 获取当前日期
	 * 
	 * 如: yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String date() {
		return isoFormat.format(new Date());
	}

	/**
	 * 格式化Date
	 * 
	 * 如: yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String date(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return isoFormat.format(date);
	}

	/**
	 * 格式化Date
	 * 
	 * 如: yyyyMMdd
	 * 
	 * @return
	 */
	public static String date0(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return iso0Format.format(date);
	}

	/**
	 * 获取当前日期
	 * 
	 * 如: yyyyMMdd
	 * 
	 * @return
	 */
	public static String date0() {
		return iso0Format.format(new Date());
	}

	/**
	 * 获取当前日期
	 * 
	 * 如: yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @return
	 */
	public static String date1() {
		return iso1Format.format(new Date());
	}

	/**
	 * 格式化Date
	 * 
	 * 如: yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @return
	 */
	public static String date1(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return iso1Format.format(date);
	}

	/**
	 * 获取当前日期
	 * 
	 * 如: yyyy-MM-dd HH:mm:ss.SSSSSS
	 * 
	 * @return
	 */
	public static String date2() {
		return iso2Format.format(new Date());
	}

	/**
	 * 格式化Date
	 * 
	 * 如: yyyy-MM-dd HH:mm:ss.SSSSSS
	 * 
	 * @return
	 */
	public static String date2(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return iso2Format.format(date);
	}

	/**
	 * 获取当前日期
	 * 
	 * 如: yyyy-MM-dd'T'HH:mm:ss
	 * 
	 * @return
	 */
	public static String date3() {
		return iso3Format.format(new Date());
	}

	/**
	 * 格式化Date
	 * 
	 * 如: yyyy-MM-dd'T'HH:mm:ss
	 * 
	 * @return
	 */
	public static String date3(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return iso3Format.format(date);
	}

	/**
	 * 获取当前日期
	 * 
	 * 如: yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String date4() {
		return iso4Format.format(new Date());
	}

	/**
	 * 格式化Date
	 * 
	 * 如: yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String date4(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return iso4Format.format(date);
	}

	/**
	 * 获取当前日期
	 * 
	 * 如: yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String date5() {
		return iso5Format.format(new Date());
	}

	/**
	 * 格式化Date
	 * 
	 * 如: yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String date5(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return iso5Format.format(date);
	}

	/**
	 * 获取当前日期
	 * 
	 * 如: yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String date6() {
		return iso6Format.format(new Date());
	}

	/**
	 * 格式化Date
	 * 
	 * 如: yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String date6(Date date) {
		if (CommonUtil.isBlank(date)) {
			return "";
		}
		return iso6Format.format(date);
	}

	/**
	 * 将日期格式. HH:mm:ss 转换为 HHmmss的整数类型
	 * 
	 * 如: 21:33:01 转换为 213301
	 * 
	 * @param time3
	 *            如: 21:33:01
	 * @return
	 */
	public static int time2int(String time3) {
		if (time3 != null) {
			return int2(time3.replaceAll(":", ""));
		}
		return 0;
	}

	/**
	 * date是 星期几
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static int week(String date) {
		DateTime dateTime = DateTime.parse(date, ISODateTimeFormat.basicDate());
		return dateTime.dayOfWeek().get();
	}

	/**
	 * 格式化日期.
	 * 
	 * @param date
	 * @param fmt
	 * @ISO yyyy-MM-dd
	 * @ISO0 yyyyMMdd
	 * @ISO1 yyyy-MM-dd HH:mm:ss.SSS
	 * @ISO2 yyyy-MM-dd HH:mm:ss.SSSSSS
	 * @ISO3 yyyy-MM-dd'T'HH:mm:ss
	 * @ISO4 yyyyMMddHHmmssSSS
	 * @ISO5 yyyyMMddHHmmss
	 * @ISO6 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getDate(Object date, String format) {
		if (date instanceof Date) {
			return (Date) date;
		}
		if (date instanceof BigDecimal) {
			return getDate(date.toString(), format);
		}
		if (date instanceof Integer) {
			return getDate(date.toString(), format);
		}

		if (date != null) {
			return getDate(date.toString(), format);
		}

		return null;
	}

	/**
	 * 格式化日期.
	 * 
	 * @param date
	 * @param fmt
	 * @ISO yyyy-MM-dd
	 * @ISO0 yyyyMMdd
	 * @ISO1 yyyy-MM-dd HH:mm:ss.SSS
	 * @ISO2 yyyy-MM-dd HH:mm:ss.SSSSSS
	 * @ISO3 yyyy-MM-dd'T'HH:mm:ss
	 * @ISO4 yyyyMMddHHmmssSSS
	 * @ISO5 yyyyMMddHHmmss
	 * @ISO6 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getDate(String date, String format) {
		try {
			if (format != null && date != null) {
				if (format.toUpperCase().endsWith("ISO")) {
					return isoFormat.parse(date);
				} else if (format.toUpperCase().endsWith("ISO0")) {
					return iso0Format.parse(date);
				} else if (format.toUpperCase().endsWith("ISO1")) {
					return iso1Format.parse(date);
				} else if (format.toUpperCase().endsWith("ISO2")) {
					return iso2Format.parse(date);
				} else if (format.toUpperCase().endsWith("ISO3")) {
					return iso3Format.parse(date);
				} else if (format.toUpperCase().endsWith("ISO4")) {
					return iso4Format.parse(date);
				} else if (format.toUpperCase().endsWith("ISO5")) {
					return iso5Format.parse(date);
				} else if (format.toUpperCase().endsWith("ISO6")) {
					return iso6Format.parse(date);
				}
			}
		} catch (ParseException e) {
			// e.printStackTrace();
		}
		return null;

	}

	/**
	 * 格式化日期. 如：20150202
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static Date getDate(int date) {
		try {
			return iso0Format.parse("" + date);
		} catch (ParseException e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化日期. 如：20150202
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static Date getDate(BigDecimal date) {
		if (date != null) {
			return getDate(date.intValue());
		}
		return null;
	}

	/**
	 * 获取当前时间
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static Date getDate() {

		return new Date();
	}

	/**
	 * 去掉字符串空格.
	 */
	public static String trim(Object object) {
		if (object != null) {
			return object.toString().trim();
		}
		return "";
	}

	/**
	 * 赋值语句，但将变量右对齐.
	 * 
	 * 字符串前面补空格
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String evalr(String str, int len) {
		return StringUtil.fillStartBlank(str, len);
	}

	/**
	 * 赋值语句，但将变量左对齐.
	 * 
	 * 字符串后面补空格
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String evall(String str, int len) {
		return StringUtil.fillEndBlank(str, len);
	}

	/**
	 * 赋值语句，但将变量右对齐.
	 * 
	 * 字符串前面补0
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String evalr(BigDecimal bigDecimal, int len) {
		return StringUtil.fillStartBlank(bigDecimal.toString(), len, '0');
	}

	/**
	 * 赋值语句，但将变量右对齐.
	 * 
	 * 字符串前面补0
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String evalr(int bigDecimal, int len) {
		return StringUtil.fillStartBlank("" + bigDecimal, len, '0');
	}

	/**
	 * 赋值语句，但将变量右对齐.
	 * 
	 * 字符串前面补0
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String evalr(long bigDecimal, int len) {
		return StringUtil.fillStartBlank("" + bigDecimal, len, '0');
	}

	/**
	 * 将字符串中的某个字符用另外一字符替换.
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String xlate(String src, String tag, String replace) {
		if (replace != null && src != null && tag != null) {
			int length = replace.length();
			String s = "";
			for (int i = 0; i < length; i++) {
				char charAt = replace.charAt(i);
				int indexOf = src.indexOf(charAt);
				System.out.println(indexOf);
				if (indexOf > -1 && tag.length() > indexOf) {
					s += tag.substring(indexOf, indexOf + 1);
				} else {
					s += charAt;
				}
			}
			return s;
		}
		return replace;
	}

	/**
	 * 截取字段.
	 * 
	 * @param source
	 * @param start
	 *            开始位置
	 * @param len
	 *            长度
	 * @return
	 */
	public static String subst(String source, int start, int len) {
		if (len > 0) {
			if (source != null && source.length() > (start - 2)) {
				if (source.length() > (start - 2 + len)) {
					return source.substring(start - 1, start - 1 + len);
				} else {
					return source.substring(start - 1);
				}
			}
			return "";
		} else {
			return subst(source, start);
		}
	}

	/**
	 * 截取字段.
	 * 
	 * @param source
	 * @param start
	 *            开始位置
	 * @param len
	 *            长度
	 * @return
	 */
	public static String subst(String source, double start, int len) {
		return subst(source, (int) start, len);
	}

	/**
	 * 转换为long类型
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static long long2(Object bigDecimal) {
		if (bigDecimal == null) {
			return 0;
		}
		if (bigDecimal instanceof Long) {
			return (Long) bigDecimal;
		}
		if (bigDecimal instanceof BigDecimal) {
			return long2((BigDecimal) bigDecimal);
		}
		return long2(bigDecimal.toString());
	}

	/**
	 * 转换为long类型
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static long long2(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return 0;
		}
		return bigDecimal.longValue();
	}

	/**
	 * 转换为long类型
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static long long2(String bigDecimal) {
		if (bigDecimal == null || bigDecimal.trim().length() == 0) {
			return 0;
		}
		return new BigDecimal(bigDecimal).longValue();
	}

	/**
	 * 转换为double类型
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static double double2(Object bigDecimal) {
		if (bigDecimal == null) {
			return 0;
		}
		if (bigDecimal instanceof Double) {
			return (Double) bigDecimal;
		}
		if (bigDecimal instanceof BigDecimal) {
			return double2((BigDecimal) bigDecimal);
		}
		return double2(bigDecimal.toString());
	}

	/**
	 * 转换为double类型
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static double double2(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return 0;
		}
		return bigDecimal.doubleValue();
	}

	/**
	 * 转换为double类型
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static double double2(String bigDecimal) {
		if (bigDecimal == null || bigDecimal.trim().length() == 0) {
			return 0;
		}
		return new BigDecimal(bigDecimal).doubleValue();
	}

	/**
	 * 转换为int类型
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static int int2(Object bigDecimal) {
		if (bigDecimal instanceof Integer) {
			return (Integer) bigDecimal;
		}
		if (bigDecimal instanceof BigDecimal) {
			return int2((BigDecimal) bigDecimal);
		}
		if (bigDecimal instanceof Double) {
			return int2(new BigDecimal((Double) bigDecimal));
		}
		if (bigDecimal != null) {
			return int2(bigDecimal.toString());
		}
		return 0;
	}

	/**
	 * 转换为int类型
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static int int2(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return 0;
		}
		return bigDecimal.intValue();
	}

	/**
	 * 转换为int类型
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static int int2(String bigDecimal) {
		if (bigDecimal == null || bigDecimal.trim().length() == 0) {
			return 0;
		}
		try {
			BigDecimal bigDecimal2 = new BigDecimal(bigDecimal);
			return bigDecimal2.intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * 截取字段.
	 * 
	 * @param source
	 * @param start
	 *            开始位置
	 * @param len
	 *            长度
	 * @return
	 */
	public static String subst(String source, int start) {
		if (source != null) {
			if (source.length() > (start - 2)) {
				return source.substring(start - 1);
			} else {
				return "";
			}
		}
		return "";
	}

	/**
	 * 求绝对值.
	 * 
	 * @param input
	 * @return
	 */
	public static int abs(int input) {
		return Math.abs(input);
	}

	/**
	 * 求绝对值.
	 * 
	 * @param input
	 * @return
	 */
	public static double abs(double input) {
		return Math.abs(input);
	}

	/**
	 * 字符串替换.
	 * 
	 * @param replacement
	 *            替换字符串
	 * @param source
	 * @param position
	 *            替换第position位开始的length长度字符
	 * @param length
	 *            截取source的长度
	 * @return
	 */
	public static String replace(String replacement, String source, int position, int length) {
		return replaceLocValue(replacement, position, length, source);
	}

	/**
	 * 字符串替换.
	 * 
	 * @param replacement
	 *            替换字符串
	 * @param source
	 * @param position
	 *            替换第position位开始的length长度字符
	 * @param length
	 *            截取source的长度
	 * @return
	 */
	public static String replace(String replacement, String source, double position, int length) {
		return replaceLocValue(replacement, (int) position, length, source);
	}

	/**
	 * 字符串替换.
	 * 
	 * @param replacement
	 *            替换字符串
	 * @param source
	 * @param position
	 *            替换第position位开始的字符，将source直接替换到replacement中,
	 * 
	 * @return
	 */
	public static String replace(String replacement, String source, int position) {
		return replaceLocValue(replacement, position, source.length(), source);
	}

	/**
	 * 字符串替换.
	 * 
	 * @param replacement
	 *            替换字符串
	 * @param source
	 *            直接从replacement第一位开始替换
	 * @return
	 */
	public static String replace(String replacement, String source) {
		return replace(replacement, source, 0);
	}

	/**
	 * 判断对象是否相等, 如果都为null,返回true.
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(Object obj1, Object obj2) {
		if (obj1 == null) {
			if (obj2 == null) {
				return true;
			} else {
				return isBlank(obj2);
			}
		} else {
			return obj1.equals(obj2);
		}
	}

	/**
	 * 判断字符串是否相等, 如果都为null,返回true.
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(String str1, String str2) {
		if (str1 == null) {
			if (str2 == null) {
				return true;
			} else {
				return isBlank(str2);
			}
		} else {
			if (str1.length() == 0) {
				return isBlank(str2);
			}
			return str1.equals(str2);
		}
	}

	/**
	 * 判断字符串和int是否相等, 如果都为null,返回true. 如果都不为null，则判断str1是否相等str2
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(String str1, int str2) {
		if (str1 == null || str1.trim().length() == 0) {
			return str2 == 0;
		}
		return isEquals(new BigDecimal(str1), new BigDecimal(str2));
	}

	/**
	 * 判断double和int是否相等, 如果都为null,返回true. 如果都不为null，则判断str1是否大于str2
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(double str1, int str2) {
		return isEquals(new BigDecimal(str1), new BigDecimal(str2));
	}

	/**
	 * 判断str1是否等于str2
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(int str1, int str2) {
		return str1 == str2;
	}

	/**
	 * 判断str1是否等于str2
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(double str1, double str2) {
		return str1 == str2;
	}

	/**
	 * 判断字符串和BigDecimal是否相等, 如果都为null,返回true. 如果都不为null，则判断str1是否相等str2
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(String str1, BigDecimal str2) {
		if (str1 == null) {
			return str2 == null;
		} else {
			if (str1.length() == 0) {
				return str2 == null;
			}
			if (str2 != null) {
				return new BigDecimal(str1).equals(str2);
			} else {
				return false;
			}

		}

	}

	/**
	 * 判断字符串和日期是否相等, 如果都为null,返回true. 如果都不为null，则判断str1是否相等str2
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(String str1, Date str2) {
		if (str1 == null) {
			return str2 == null;
		} else {
			if (str1.length() == 0) {
				return str2 == null;
			}
			if (str2 != null) {
				return str1.equals(iso0Format.format(str2));
			} else {
				return false;
			}

		}
	}

	/**
	 * 判断日期是否相等, 如果都为null,返回true. 如果都不为null，则判断str1是否相等str2
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(Date date1, Date date2) {
		if (date1 == null) {
			return date2 == null;
		} else {
			if (date2 == null) {
				return false;
			}
			return date1.compareTo(date2) == 0;
		}
	}

	/**
	 * 如果都为null则返回true；如果都不为null则判断是否相等
	 * 
	 * @param dec1
	 * @param dec2
	 * @return
	 */
	public static boolean isEquals(BigDecimal dec1, BigDecimal dec2) {
		if (dec1 == null) {
			return dec2 == null;
		} else {
			if (dec2 == null) {
				return false;
			}
			return dec1.compareTo(dec2) == 0;
		}
	}

	/**
	 * 如果str1为null，str2=0则返回true；如果str1不为null则判断str1==str2
	 * 
	 * @param dec1
	 * @param dec2
	 * @return
	 */
	public static boolean isEquals(BigDecimal str1, int str2) {
		if (str1 == null) {
			return str2 == 0;
		}
		if (str1.intValue() == str2) {
			return isEquals(str1, new BigDecimal(str2));
		} else {
			return false;
		}
	}

	/**
	 * 如果str1为null，str2=0.0，则返回true；如果str1不为null则判断str1==str2
	 * 
	 * @param dec1
	 * @param dec2
	 * @return
	 */
	public static boolean isEquals(BigDecimal str1, double str2) {
		if (str1 == null) {
			return str2 == 0.0;
		} else {
			return str1.doubleValue() == str2;
		}

	}

	/**
	 * 判断字符串是否相等, 如果都为null,返回true. 如果都不为null，则判断str1是否大于str2
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isThan(String str1, String str2) {
		if (str1 == null) {
			if (str2 == null) {
				return true;
			} else {
				return isBlank(str2);
			}
		} else {
			if (str1.length() == 0) {
				return isBlank(str2);
			}
			return str1.compareTo(str2) > 0;
		}
	}

	/**
	 * 判断日期是否相等, 如果都为null,返回true. 如果都不为null，则判断str1是否大于str2
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isThan(Date date1, Date date2) {
		if (date1 == null) {
			return date2 == null;
		} else {
			if (date2 == null) {
				return true;
			}
			return date1.after(date2);
		}
	}

	/**
	 * 判断日期是否相等, 如果都为null,返回true. 如果都不为null，则判断dec1是否大于dec2
	 * 
	 * @param dec1
	 * @param dec2
	 * @return
	 */
	public static boolean isThan(BigDecimal dec1, BigDecimal dec2) {
		if (dec1 == null) {
			return dec2 == null;
		} else {
			if (dec2 == null) {
				return true;
			}
			return dec1.compareTo(dec2) > 0;
		}
	}

	/**
	 * 判断String是否为null 是，转化为 ""（空字符串）
	 * 
	 * @param str
	 * @return
	 */
	public static String isNull2Blank(String str) {
		if (str == null || str.length() == 0) {
			return "";
		}
		return str;

	}

	/**
	 * 产生账户校验位 相当于BPPSYACCV.
	 * 
	 * @param sourceAcct
	 * @param targetLen
	 * @return
	 */
	public static String generateAcct(String sourceAcct, int targetLen) {
		int[] weigth_factors = { 11, 13, 17, 19, 23, 31, 37, 41, 47, 51 };
		int i, j = 0;
		long sum = 0, sum1 = 0;
		char[] self_check_digits = new char[2];
		char[] acct_no;
		char[] char_source;
		String TargetAcct = "";
		char_source = sourceAcct.toCharArray();

		if (targetLen > 32) {
			return null;
		}
		acct_no = new char[sourceAcct.trim().length() + 2];

		for (int y = 0; y < sourceAcct.trim().length(); y++) {
			acct_no[y] = char_source[y];
		}

		for (i = 0; i < targetLen - 3; i += 2) {
			sum = sum + ((acct_no[i + 1] - '0') * 10 + (acct_no[i] - '0')) * weigth_factors[j];
			j++;
		}

		sum1 = sum % 97;
		if (sum1 == 0L)
			sum1++;

		self_check_digits = String.valueOf(sum1).toCharArray();

		if (self_check_digits.length == 1) {
			char[] cs = new char[2];
			cs[1] = self_check_digits[0];
			cs[0] = '0';
			self_check_digits = cs;
		}

		if (self_check_digits[0] == self_check_digits[1]) {
			sum1 = (self_check_digits[0] - '0') + (self_check_digits[1] - '0');
			self_check_digits = String.valueOf(sum1).toCharArray();
		}

		if (self_check_digits.length == 1) {
			char[] cs = new char[2];
			cs[1] = self_check_digits[0];
			cs[0] = '0';
			self_check_digits = cs;
		}

		acct_no[targetLen - 2] = self_check_digits[0];
		acct_no[targetLen - 1] = self_check_digits[1];

		TargetAcct = String.valueOf(acct_no);

		return TargetAcct;
	}

	/**
	 * 字符串前面补0.
	 * 
	 * @param source
	 *            需要补0的字符串
	 * @param sourceLen
	 *            补多少位0(但包含字符串的长度，如：sourceLen=5，source.length=3，则source前会补给2个0)
	 * @return
	 */
	public static String setPrvEvZero(String source, int sourceLen) {
		if (source == null || sourceLen <= source.trim().length()) {
			return " ";
		}
		String f_str = "";
		int f_i = sourceLen - source.trim().length();
		for (int i = 0; i < f_i; i++) {
			f_str = f_str.trim() + '0';
		}
		f_str = f_str.trim() + source.trim();
		return f_str;
	}

	/**
	 * 取摸.
	 * 
	 * @param i
	 * @param mod
	 * @return
	 */
	public static int rem(int i, int mod) {
		return i % mod;
	}

	/**
	 * 取摸.
	 * 
	 * i = 4 ,2 = 0
	 * 
	 * 
	 * @param i
	 * @param mod
	 *            模数
	 * @return
	 */
	public static int rem(double i, double mod) {
		return (int) ((long) i % (long) mod);
	}

	/**
	 * 两个数相除.
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double divide(double d1, double d2, int scale) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return divide(b1, b2, scale);
	}

	/**
	 * 两个数相除.直接截取.
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double divideDown(BigDecimal b1, BigDecimal b2, int scale) {
		if (b1 == null || b2 == null) {
			return 0;
		}
		BigDecimal divide = b1.divide(b2, scale, RoundingMode.DOWN);
		return divide.doubleValue();
	}

	/**
	 * 两个数相除.四舍五入
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double divide(BigDecimal b1, BigDecimal b2, int scale) {
		if (b1 == null || b2 == null) {
			return 0;
		}
		BigDecimal divide = b1.divide(b2, scale, RoundingMode.HALF_UP);
		return divide.doubleValue();
	}

	/**
	 * 两个数相乘.
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double multiply(double d1, double d2) {
		return d1 * d2;
	}

	/**
	 * 两个数相乘. 四舍五入
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double multiply(BigDecimal b1, BigDecimal b2, int scale) {
		if (b1 == null || b2 == null) {
			return 0;
		}
		return b1.multiply(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 两个数相乘. 直接截取.
	 * 
	 * @param b1
	 * @param b2
	 * @param scale
	 * @return
	 */
	public static double multiplyDown(BigDecimal b1, BigDecimal b2, int scale) {
		if (b1 == null || b2 == null) {
			return 0;
		}
		return b1.multiply(b2).setScale(scale, RoundingMode.DOWN).doubleValue();
	}

	/**
	 * 两个数相加 四舍五入.
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double add(BigDecimal b1, BigDecimal b2, int scale) {
		if (b1 == null || b2 == null) {
			return 0;
		}
		return b1.add(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 两个数相加.直接截取.
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double addDown(BigDecimal b1, BigDecimal b2, int scale) {
		if (b1 == null || b2 == null) {
			return 0;
		}
		return b1.add(b2).setScale(scale, RoundingMode.DOWN).doubleValue();
	}

	/**
	 * 两个数相减.四舍五入
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double subtract(BigDecimal b1, BigDecimal b2, int scale) {
		if (b1 == null || b2 == null) {
			return 0;
		}
		return b1.subtract(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * 两个数相减.直接截取.
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 * @return
	 */
	public static double subtractDown(BigDecimal b1, BigDecimal b2, int scale) {
		if (b1 == null || b2 == null) {
			return 0;
		}
		return b1.subtract(b2).setScale(scale, RoundingMode.DOWN).doubleValue();
	}

	/**
	 * 按位，长度替换赋值.
	 * 
	 * abc,1,10,1232 = 1232
	 * 
	 * @param source
	 *            原字符串
	 * @param offset
	 *            位置
	 * @param len
	 *            长度
	 * @param target
	 * @return
	 */
	public static String replaceLocValue(String source, int offset, int len, String target) {
		if (source == null) {
			source = "";
		}
		if (target == null) {
			target = "";
		}
		StringBuffer buffer = new StringBuffer();
		if (source.length() < offset - 1) {
			buffer.append(source);
			for (int i = 0; i < offset - 1 - source.length(); i++) {
				buffer.append(' ');
			}
			for (int i = 0; i < len; i++) {
				if (target.length() > i) {
					buffer.append(target.charAt(i));
				} else {
					buffer.append(' ');
				}
			}
		} else {
			for (int i = 0; i < offset - 1; i++) {
				buffer.append(source.charAt(i));
			}
			for (int i = 0; i < len; i++) {
				if (target.length() > i) {
					buffer.append(target.charAt(i));
				} else {
					buffer.append(' ');
				}
			}
			for (int i = offset - 1 + len; i < source.length(); i++) {
				buffer.append(source.charAt(i));
			}
		}
		return buffer.toString();
	}

	/**
	 * 返回str填充的字符串.
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String fullAllString(String str, int len) {
		if (str == null) {
			str = "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < len;) {
			buffer.append(str);
			i += str.length();
		}
		if (buffer.length() > len) {
			buffer.delete(len, buffer.length());
		}
		return buffer.toString();
	}

	/**
	 * 获取字符串所在第一位置.
	 * 
	 * @param str
	 * @param scan
	 * @return 为找到返回-1, 找到返回位置
	 */
	public static int scan(String str, String scan) {
		if (str == null || scan == null) {
			return -1;
		}
		int indexOf = str.indexOf(scan);
		if (indexOf < 0) {
			return indexOf;
		} else {
			return indexOf + 1;
		}
	}

	/**
	 * 查找数组中数据的下标.
	 * 
	 * @param sourc
	 *            要查找的数据
	 * @param array
	 *            被查找的数组
	 * @return
	 */
	public static int lookup(String sourc, String[] array) {
		int tot = array.length;

		for (int i = 0; i < tot; i++) {
			if (isEquals(sourc, array[i])) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 查找数组中数据的下标.
	 * 
	 * @param sourc
	 *            要查找的数据
	 * @param array
	 *            被查找的数组
	 * @return
	 */
	public static int lookup(int sourc, int[] array) {
		int tot = array.length;
		for (int i = 0; i < tot; i++) {
			if (sourc == array[i]) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * <br>
	 * 替换字符串里最后一次出现的 子字符串
	 * 
	 * @param string
	 *            原生字符串
	 * @param match
	 *            匹配的字符串
	 * @param replace
	 *            要替换的字符串
	 * @return
	 * @author hwhaocool
	 * @since 2018-08-18
	 */
	public static String replaceLast(String string, String match, String replace) {
		if (string == null || null == replace) {
			// 参数不合法，原样返回
			return string;
		}

		StringBuilder sBuilder = new StringBuilder(string);
		int lastIndexOf = sBuilder.lastIndexOf(match);
		if (-1 == lastIndexOf) {
			return string;
		}

		return sBuilder.replace(lastIndexOf, lastIndexOf + match.length(), replace).toString();
	}
	//
	// public static void main(String[] args) {
	// // Date plus = plus(new Date(), 2, "*DAYS");
	// // String plus1 = plus("20140501", 2, "*DAYS");
	// // String replaceLocValue = replaceLocValue("abcefg", 10, 4, "1234");
	// // System.out.println(replaceLocValue);
	// // String[] array = toArray("abcefg1", 2);
	// // System.out.println(array);
	// // String fullAllString = fullAllString("12", 3);
	// // System.out.println(fullAllString);
	// // System.out.println(isEquals(1.0, 1));
	// //
	// // System.out.println(Integer.MAX_VALUE);
	// // System.out.println(Double.MAX_VALUE);
	//
	// //
	// System.out.println("------------------------------------------------");
	// // String[] array2 = toArray("123", new int[] { 1, 2, 3 });
	// // System.out.println(array2.toString());
	// //
	// System.out.println("------------------------------------------------");
	// // System.out.println("----[" + isEquals(0.000000083333, 0) +
	// // "]--------");
	// // System.out.println(subtract(new BigDecimal("122222"), new
	// // BigDecimal("15155.531"), 2));
	// // String xlate = xlate("DC", "CD", "D 2 C");
	// // System.out.println(xlate);
	// // double ss = 20140101d;
	// // ss = ss + 0.00;
	// // System.out.println(trimStringDecimal(ss));
	// // System.out.println(Math.pow(20, 3));
	//
	// String tmp = "system/tenant/list.do";
	//
	// System.out.println(CommonUtil.subst("json_test", 1, 5));
	// }
}
