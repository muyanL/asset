//package com.ossjk.asset.base.util;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// *
// * @author Phonnie
// *
// */
//public class SerialNumberUtil {
//	private static SimpleDateFormat sdf = new SimpleDateFormat("YY");
//
//	/**
//	 * 初始化设备流水号
//	 * 
//	 * @return
//	 */
//	public static String deviceSerialNumber() {
//		String prfix = "S" + sdf.format(new Date());
//		return prfix + String.format("%04d", 1);
//	}
//
//	/**
//	 * 在原始的流水号自增
//	 * 
//	 * @param sn
//	 * @return
//	 */
//	public static String deviceSerialNumber(String sn) {
//		String prfix = "S" + sdf.format(new Date());
//		String numberStr = sn.substring(sn.indexOf(prfix) + prfix.length());
//		if (CommonUtil.isNumeric(numberStr)) {
//			return prfix + String.format("%04d", (CommonUtil.int2(numberStr) + 1));
//		} else {
//			System.out.println("sn格式错误");
//			return null;
//		}
//
//	}
//
//	public static String deviceDjSerialNumber() {
//		String prfix = "SDJ" + sdf.format(new Date());
//		return prfix + String.format("%04d", 1);
//	}
//
//	public static String deviceDjSerialNumber(String sn) {
//		String prfix = "SDJ" + sdf.format(new Date());
//		String numberStr = sn.substring(sn.indexOf(prfix) + prfix.length());
//		if (CommonUtil.isNumeric(numberStr)) {
//			return prfix + String.format("%04d", (CommonUtil.int2(numberStr) + 1));
//		} else {
//			System.out.println("sn格式错误");
//			return null;
//		}
//
//	}
//
//}