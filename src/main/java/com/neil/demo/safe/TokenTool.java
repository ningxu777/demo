package com.neil.demo.safe;


import com.neil.demo.safe.SafeUtil;

public class TokenTool {

//	public static String key = "aslkdnff!;lfnw;45aslkdnff!;lfnw;45aslkdnff!;lfnw;45aslkdnff!;lfnw;45aslkdnff!;lfnw;45aslkdnff!;lfnw;45aslkdnff!;lfnw;45123456789";
	public static String key = "aslkdnff!;lfnw;4";

	/**
	 *
	 */
	public static String encode(Integer id){
		String plainText = "userIduserIdus_"+id.toString();
		String token = SafeUtil.aesEncode(plainText,key);
		return token;
	}

	public static Integer decode(String token){
		String plainText = SafeUtil.aesDecode(token,key);
		Integer id = Integer.valueOf(plainText.split("_")[1]);
		return id;
	}

}
