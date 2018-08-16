package Util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import sun.misc.BASE64Encoder;


public class PassWordUtil {
	
	private String username;
	private String original_password;

	public PassWordUtil(String username, String original_password) {
		this.username = username;
		this.original_password = original_password;
	}

	public String getMD5(){
		try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(original_password.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
           e.printStackTrace();
           return null;
        }

	}
	
	
	public String getbase64(){
		byte[] password_byte=null;
		String password=null;
		String original_str_before_base64 = username + ':' + getMD5();
		try {
			password_byte = original_str_before_base64.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(password_byte!=null){
			password=new BASE64Encoder().encode(password_byte);
		}
		return password;

	}
	
	
//  public static void main(String[] args) 
//  { // TODO Auto-generated method stub
//	  PassWordUtil a=new PassWordUtil("craser","123456");
//	  System.out.println(a.getbase64());
//  }
	
}
