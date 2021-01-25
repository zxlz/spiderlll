package spiderpak.utils;

import spiderpak.spider.Server;

public class format {

	public static String toNumber(String str) {
		str = str.trim();
		String str2 = "";
		if (str != null && !"".equals(str)) {
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
					str2 += str.charAt(i);
				}
			}
		}
		if(str2.length()==8){
		return str2;
		}else {
			Server.pushMessage("��ʽ��timeʧ��");
			return "19990101";
		}
	}
	public static String cut(String str,String a){
		try {
			str=str.split(a, 2)[1];
			str=str.split(a)[0];
		} catch (Exception e) {
			Server.pushMessage("cut�ַ���ʧ��");
			return "0";
		}
		
		return str;
	}
	public static String cuttrimtime(String str){
		str=str.trim();
		
		return str.split(" ")[0];
	}
}
