package br.com.ssp.ematricula.util;

public class InsertParser {
	public static int StrToInt(String value, int standard) {
		try {
			return Integer.valueOf(value);
		} catch(NumberFormatException e) {
			return standard;
		}
	}
	
	public static Double StrToDouble(String value, double standard) {
		char[] c = value.toCharArray();
		for(int i = 0; i < value.length(); i++) {
			if(c[i] == ',')
				c[i] = '.';
		}
		value = String.valueOf(c);
		try {
			return Double.valueOf(value);
		} catch(NumberFormatException e) {
			return standard;
		}
	}
}