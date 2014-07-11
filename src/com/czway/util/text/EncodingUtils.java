package com.czway.util.text;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EncodingUtils {

	public static final String UTF8 = "UTF-8";
	public static final String Unicode = "Unicode";
	public static final String UTF16BE = "UTF-16BE";
	public static final String GBK = "GBK";

	public static String stringCoding(String fileName) {
		BufferedInputStream bin = null;
		try {
			bin = new BufferedInputStream(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int p = 0;
		try {
			p = (bin.read() << 8) + bin.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getEncode(p);
	}

	private static String getEncode(int p) {
		String code;
		switch (p) {
		case 0xefbb:
			code = UTF8;
			break;
		case 0xfffe:
			code = Unicode;
			break;
		case 0xfeff:
			code = UTF16BE;
			break;
		default:
			code = GBK;
		}
		return code;
	}

	public static String stringCoding(InputStream is) {
		InputStreamReader isr = new InputStreamReader(is);
		return isr.getEncoding();
	}
	protected static String m(byte first3Bytes[]) {
		String charset = UTF8;
		if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
			charset = "UTF-16LE";
		} else if (first3Bytes[0] == (byte) 0xFE
				&& first3Bytes[1] == (byte) 0xFF) {
			charset = "UTF-16BE";
		} else if (first3Bytes[0] == (byte) 0xEF
				&& first3Bytes[1] == (byte) 0xBB
				&& first3Bytes[2] == (byte) 0xBF) {
			charset = "UTF-8";
		}
		return charset;

	}
	protected static String n(byte[] head) {
		String code = "";
		code = "gb2312";
		if (head[0] == -1 && head[1] == -2)
			code = "UTF-16";
		if (head[0] == -2 && head[1] == -1)
			code = "Unicode";
		if (head[0] == -17 && head[1] == -69 && head[2] == -65)
			code = "UTF-8";
		return code;
	}

}
