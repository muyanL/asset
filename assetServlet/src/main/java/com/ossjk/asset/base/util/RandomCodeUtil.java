package com.ossjk.asset.base.util;

import java.util.Random;

/**
 * java生成一个4位的随机数（验证码）
 * 
 * @author Administrator
 *
 */
public class RandomCodeUtil {

	public static String generate() {

		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {
			char ch = str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);

		}
		return sb.toString();
	}

}