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
            // ����һ��MD5���ܼ���ժҪ
            MessageDigest md = MessageDigest.getInstance("MD5");
            // ����md5����
            md.update(original_password.getBytes());
            // digest()���ȷ������md5 hashֵ������ֵΪ8Ϊ�ַ�������Ϊmd5 hashֵ��16λ��hexֵ��ʵ���Ͼ���8λ���ַ�
            // BigInteger������8λ���ַ���ת����16λhexֵ�����ַ�������ʾ���õ��ַ�����ʽ��hashֵ
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
