package com.ossjk.asset.base.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @Author chair
 * @Version 1.0, 2017年8月20日
 * @See
 * @Since com.jk.bestbaby.util
 * @Description: TODO
 */
public final class StringUtil {
	/**
	 * 把字符串(字母)的首位字母转为大写
	 * 
	 * @param s
	 * @return
	 */
	public static String getFirstUpper(String s) {
		if (s != null && s.length() > 0) {
			char charAt = s.charAt(0);
			if (charAt <= 'z' && charAt >= 'a') {
				charAt -= 32;
			}
			s = charAt + s.substring(1);

		}
		return s;
	}

	/**
	 * 对象转换字符串
	 * 
	 * @param Object
	 *            source
	 * @return
	 */
	public static String getNotNullString(Object source) {
		return source == null ? "" : source.toString(); //$NON-NLS-1$
	}

	/**
	 * 对象转换字符串
	 * 
	 * @param byte[]
	 *            source
	 * @return
	 */
	public static String getNotNullString(byte[] source) {
		return source == null ? "" : new String(source); //$NON-NLS-1$
	}

	/**
	 * 删除字符串前后的空格
	 * 
	 * @param source
	 * @return
	 */
	public static String getNotNullStringAndTrim(Object source) {
		return getNotNullString(source).trim();
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static String[] getNotNullStrings(String[] source) {
		String[] newStrin = new String[source.length];
		for (int i = 0; i < source.length; i++) {
			String string = source[i];
			newStrin[i] = getNotNullString(string);
		}
		return newStrin;
	}

	/**
	 * String转Integer
	 * 
	 * @param source
	 * @return
	 */
	public static Integer getNotNullInteger(String source) {
		int i = 0;
		if (source != null && source.length() > 0) {
			try {
				i = Integer.parseInt(source);
			} catch (Exception e) {
				// do nothing.
			}
		}
		return i;
	}

	/**
	 * 能转换String的Integer 如String为null则输出0
	 * 
	 * @param source
	 * @return
	 */
	public static String getNotNullIntegerStr(String source) {
		int i = 0;
		if (source != null && source.length() > 0) {
			try {
				i = Integer.parseInt(source);
			} catch (Exception e) {
				// do nothing.
			}
		}
		return "" + i; //$NON-NLS-1$
	}

	/**
	 * 将字符串转为boolean
	 * 
	 * @param bv
	 * @return
	 */
	public static Boolean getNotNullBoolean(String bv) {
		boolean bvb = false;
		if (bv != null && !bv.equals("")) { //$NON-NLS-1$
			try {
				bvb = Boolean.valueOf(bv).booleanValue();
			} catch (Exception e) {
				// do nothing.
			}
		}
		return bvb;
	}

	/**
	 * String长度大于50，大于50以后的字符用“...”代替
	 * 
	 * @param s
	 * @return
	 */
	public static String getDotString(String s) {
		String dot = ""; //$NON-NLS-1$
		if (s.length() > 50) {
			dot = s.substring(0, 50);
			dot += "..."; //$NON-NLS-1$
		} else {
			dot = s;
		}
		return dot;
	}

	/**
	 * 只判断第一位数字的大小，进行排序
	 * 
	 * @param list
	 * @return
	 */
	public static String[] pasteSortedArray(Collection<String> list) {
		if (list != null) {
			String[] panels = new String[list.size()];
			int i = 0;
			for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				panels[i] = string;
				i++;
			}
			Arrays.sort(panels);
			return panels;
		} else {
			return new String[0];
		}
	}

	/**
	 * 将str长度增加到length，一样的长度（在后面添加" "空格），如果str的长度比length长则返回str
	 * 
	 * @param str
	 *            需要增加长度的字符串
	 * @param length
	 *            需要增加的长度
	 * @return
	 */
	public static String fillEndBlank(String str, int length) {
		int length2 = str.length();
		if (length > length2) {
			for (int i = 0; i < length - length2; i++) {
				str = str + " "; //$NON-NLS-1$
			}
		}
		return str;
	}

	/**
	 * 将str长度增加到length，一样的长度（在前面添加" "空格），如果str的长度比length长则返回str
	 * 
	 * @param str
	 *            需要增加长度的字符串
	 * @param length
	 *            需要增加的长度
	 * @return
	 */
	public static String fillStartBlank(String str, int length) {
		int length2 = str.length();
		if (length > length2) {
			for (int i = 0; i < length - length2; i++) {
				str = " " + str; //$NON-NLS-1$
			}
		}
		return str;
	}

	/**
	 * 将str长度增加到length，一样的长度（在前面添加c），如果str的长度比length长则返回str
	 * 
	 * @param str
	 *            需要增加长度的字符串
	 * @param length
	 *            需要增加的长度
	 * @return
	 */
	public static String fillStartBlank(String str, int length, char c) {
		int length2 = str.length();
		if (length > length2) {
			for (int i = 0; i < length - length2; i++) {
				str = c + str; // $NON-NLS-1$
			}
		}
		return str;
	}

	/**
	 * 将str长度增加到length，一样的长度（在后面添加c），如果str的长度比length长则返回str
	 * 
	 * @param str
	 *            需要增加长度的字符串
	 * @param length
	 *            需要增加的长度
	 * @return
	 */
	public static String fillEndChar(String str, int length, char c) {
		int length2 = str.length();
		if (length > length2) {
			for (int i = 0; i < length - length2; i++) {
				str = str + c;
			}
		}
		return str;
	}

	/**
	 * StringBuffer添加空格，到指定长度
	 * 
	 * @param str
	 * @param length
	 */
	public static void fillAfterBlank(StringBuffer str, int length) {
		int length2 = str.length();
		if (length > length2) {
			for (int i = 0; i < length - length2; i++) {
				str.append(" "); //$NON-NLS-1$
			}
		}
	}

	/**
	 * 随机出指定长度的随机码（大小写字母和数字组合）
	 * 
	 * @param length
	 * @return
	 */
	public static String getAsciiRandomString(int length) {
		Random random = new Random();
		int cnt = 0;
		StringBuffer buffer = new StringBuffer();
		char ch;
		int end = 'z' + 1;
		int start = ' ';
		while (cnt < length) {
			ch = (char) (random.nextInt(end - start) + start);
			if (Character.isLetterOrDigit(ch)) {
				buffer.append(ch);
				cnt++;
			}
		}
		return buffer.toString();
	}

	/**
	 * 将此 String 与另一个 String 进行比较，不考虑大小写。
	 * 
	 * @param list
	 * @param str
	 * @return
	 */
	public static boolean containIgnoreCase(Collection<String> list, String str) {
		for (String string : list) {
			if (string.trim().equalsIgnoreCase(str.trim())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 全部字母转为大写
	 * 
	 * @param s
	 * @return
	 */
	public static String formatFrom400(Object s) {
		if (s != null) {
			return s.toString().trim().toUpperCase();
		}
		return null;
	}

	/**
	 * 去除前后空格后，将此 String 与另一个 String 进行比较，不考虑大小写。
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equalsFrom400(String s1, String s2) {
		if (s1 != null) {
			if (s2 != null) {
				return s1.trim().equalsIgnoreCase(s2.trim());
			} else {
				return false;
			}
		} else {
			return s2 == null;
		}
	}

	/**
	 * 将此 String 与另一个 String 进行比较，不考虑大小写。
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equalsFrom(String s1, String s2) {
		if (s1 != null) {
			if (s2 != null) {
				return s1.equalsIgnoreCase(s2);
			} else {
				return false;
			}
		} else {
			return s2 == null;
		}
	}

	/**
	 * 字符串添加"''"
	 * 
	 * @param s
	 * @return
	 */
	public static String addSimpleQuote(String s) {
		if (!s.endsWith("'")) { //$NON-NLS-1$
			s = s + "'"; //$NON-NLS-1$
		}
		if (!s.startsWith("'")) { //$NON-NLS-1$
			s = "'" + s; //$NON-NLS-1$
		}
		return s;
	}

	// public static String getCurDate() {
	//// return DateFormatUtils.format(new Date(), "yyyyMMdd HH:mm:ss");
	// //$NON-NLS-1$
	// }

	// public static String getMemeberPath(AbsFile file, String absMember) {
	// QSYSObjectPathName name = new QSYSObjectPathName(file.getLibrary().getName(),
	// file.getName(), absMember, "MBR"); //$NON-NLS-1$
	// return name.getPath();
	// }
	/**
	 * 去除前面空格后将字母转为大写
	 * 
	 * @param s
	 * @return
	 */
	public static String trimPrefix(String s) {

		int len = s.length();
		int st = 0;
		char[] val = s.toCharArray();

		while ((st < len) && (val[st] <= ' ')) {
			st++;
		}
		return ((st > 0) && (st < len)) ? s.substring(st, s.length()).toUpperCase() : s.toUpperCase();

	}

	public static String trimString(Object str) {
		if (str != null) {
			return trimSuffix(str.toString());
		}
		return null;
	}

	public static String trimStringDecimal(Object str) {
		if (str instanceof Date) {
			SimpleDateFormat iso0Format = new SimpleDateFormat("yyyyMMdd");
			return iso0Format.format((Date) str);
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
	 * 去除字符串后面的空格
	 * 
	 * @param s
	 * @return
	 */
	public static String trimSuffix(String s) {

		int len = s.length();
		char[] val = s.toCharArray();
		while (len > 0 && (val[len - 1] <= ' ' || val[len - 1] == '　')) {
			len--;
		}
		return ((len > 0) && len <= s.length()) ? s.substring(0, len) : s;
	}

	/**
	 * 将字符串按知道长度拆分成字符串集合
	 * 
	 * @param rpgString
	 *            指定字符串
	 * @param perLength
	 *            指定长度
	 * @return
	 */
	public static List<String> splitRpgString(String rpgString, int perLength) {
		List<String> splitRpgList = new ArrayList<String>();

		if (rpgString != null && rpgString.length() > 0 && perLength > 0) {
			StringBuilder sb = new StringBuilder();
			int rpgLength = 0;
			boolean lastChar = false;
			char[] rpgChar = rpgString.toCharArray();
			for (int i = 0; i < rpgChar.length; i++) {
				char c = rpgChar[i];
				boolean isch = isChinese(c);
				if (isch) {
					rpgLength += 2;
				} else {
					rpgLength += 1;
				}
				if (lastChar != isch) {
					rpgLength += 1;
				}
				lastChar = isch;
				if (rpgLength <= perLength) {
					sb.append(c);
					if (i == rpgChar.length - 1) {
						splitRpgList.add(sb.toString());
					}
				} else {
					splitRpgList.add(sb.toString());
					rpgLength = 0;
					lastChar = false;
					sb = new StringBuilder();
					isch = isChinese(c);
					if (isch) {
						rpgLength += 2;
					} else {
						rpgLength += 1;
					}
					if (lastChar != isch) {
						rpgLength += 1;
					}
					lastChar = isch;
					if (rpgLength <= perLength) {
						sb.append(c);
					}
				}
			}
		}

		return splitRpgList;
	}

	/**
	 * 按指定长度截取字符串
	 * 
	 * @param rpgString
	 * @param length
	 * @return
	 */
	public static String subRpgString(String rpgString, int length) {
		StringBuilder sb = new StringBuilder();
		if (rpgString != null && rpgString.length() > 0 && length > 0) {
			int rpgLength = 0;
			boolean lastChar = false;
			char[] rpgChar = rpgString.toCharArray();
			for (int i = 0; i < rpgChar.length; i++) {
				char c = rpgChar[i];
				boolean isch = isChinese(c);
				if (isch) {
					rpgLength += 2;
				} else {
					rpgLength += 1;
				}
				if (lastChar != isch) {
					rpgLength += 1;
				}
				lastChar = isch;
				if (rpgLength < length) {
					sb.append(c);
				} else if (rpgLength == length) {
					if (isch) {
						break;
					} else {
						sb.append(c);
					}
				} else {
					break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 计算字符长度, 如果第一个字符为中文, 长度加1, 中文和英文相连时, 长度加1
	 * 
	 * @param rpgString
	 * @return
	 */
	public static int countRpgLength(String rpgString) {
		int rpgLength = 0;

		if (rpgString != null && rpgString.length() > 0) {
			boolean lastChar = false;
			char[] rpgChar = rpgString.toCharArray();
			for (int i = 0; i < rpgChar.length; i++) {
				boolean isch = isChinese(rpgChar[i]);
				if (isch) {
					rpgLength += 2;
				} else {
					rpgLength += 1;
				}
				if (lastChar != isch) {
					rpgLength += 1;
				}
				lastChar = isch;
			}
		}

		return rpgLength;
	}

	/**
	 * 计算字符长度, 如果第一个字符为中文, 长度加1, 中文和英文相连时, 长度加1, 检测最后一个字符, 如果是中文, 长度将加1
	 * 
	 * @param rpgString
	 * @return
	 */
	public static int countRpgLengthCheckLastChar(String rpgString) {
		int rpgLength = 0;

		if (rpgString != null && rpgString.length() > 0) {
			boolean lastChar = false;
			char[] rpgChar = rpgString.toCharArray();
			int sLength = rpgChar.length;
			for (int i = 0; i < sLength; i++) {
				boolean isch = isChinese(rpgChar[i]);
				if (isch) {
					rpgLength += 2;
				} else {
					rpgLength += 1;
				}
				if (lastChar != isch) {
					rpgLength += 1;
				}
				lastChar = isch;
			}
			if (isChinese(rpgChar[sLength - 1])) {
				rpgLength += 1;
			}
		}

		return rpgLength;
	}

	/**
	 * 在英文和中文之间减去一个空格
	 * 
	 * @param content
	 * @return
	 */
	public static String cutRpgContent(String content) {
		StringBuilder sb = new StringBuilder();
		if (content != null && content.length() > 0) {
			boolean lastChar = false;
			char[] rpgChar = content.toCharArray();
			int sLength = rpgChar.length;
			for (int i = 0; i < sLength; i++) {
				char c = rpgChar[i];
				boolean isch = isChinese(c);
				if (lastChar != isch) {
					if (isch) {
						if ((i - 1) >= 0 && rpgChar[i - 1] == ' ') {
							sb.setCharAt((sb.length() - 1), c);
						} else {
							sb.append(c);
						}
					} else {
						if (rpgChar[i] != ' ') {
							sb.append(c);
						}
					}
				} else {
					sb.append(c);
				}
				lastChar = isch;
			}
		}

		return sb.toString();
	}

	/**
	 * 在英文和中文之间插入一个空格字符
	 * 
	 * @param content
	 * @return
	 */
	public static String processRpgContent(String content) {
		StringBuilder sb = new StringBuilder();
		if (content != null && content.length() > 0) {
			boolean lastChar = false;
			char[] rpgChar = content.toCharArray();
			int sLength = rpgChar.length;
			for (int i = 0; i < sLength; i++) {
				boolean isch = isChinese(rpgChar[i]);
				if (lastChar != isch) {
					sb.append(' ');
				}
				sb.append(rpgChar[i]);
				lastChar = isch;
			}
		}

		return sb.toString();
	}

	/**
	 * 判断是否中文字符，是返回true
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否含有中文字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	public static String cutStringTrimByLength(String source, int length) {
		if (source == null || length <= 0) {
			return "";
		}

		if (source.length() <= length) {
			return source;
		}

		return source.substring(0, length);
	}

	/**
	 * 判断输入的是否为纯数字
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDigital(String s) {
		Pattern pattern = Pattern.compile("[0-9]|[0-9][0-9]*");
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}

	/**
	 * 从1开始
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDigitalFromOne(String s) {
		Pattern pattern = Pattern.compile("[1-9]|[1-9][0-9]*");
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}

	/**
	 * 判断string是否在String[] ss数组中
	 * 
	 * @param string
	 * @param ss
	 * @return
	 */
	public static boolean isAmongArrays(String string, String[] ss) {
		if (string != null && ss != null && ss.length > 0) {
			boolean in = false;
			for (String s : ss) {
				if (string.trim().equalsIgnoreCase(s)) {
					in = true;
				}
			}
			return in;
		}
		return true;
	}

	/**
	 * 截取指定字符串（包含指定的字符）以后的字符删除
	 * 
	 * @param name
	 * @param divide
	 * @return
	 */
	public static String removeLastDecLine(String name, String divide) {
		if (name.contains(divide)) {
			int lastDec = name.lastIndexOf(divide);

			return name.substring(0, lastDec);
		}

		return name;
	}

	
	// public static void main(String[] args) {
	// // QSYSObjectPathName name = new
	// // QSYSObjectPathName("/QSYS.LIB/ZQLIB.LIB/ZQSRC1.FILE/TSTMBR.MBR");
	// // System.out.println(name.getPath());
	// // System.out.println("F:" + trimSuffix(" w ") + ":");
	// String s = "2012-08-31-00.00.00.000000";
	// System.out.println("s[" + s.substring(0, 10) + " "
	// + s.substring(11, 13) + ":" + s.substring(14, 16) + ":"
	// + s.substring(17, 19) + ":" + s.substring(20) + "]");
	//
	// }

}
