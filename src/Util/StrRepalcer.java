package Util;

public class StrRepalcer {
	
private static final String token="?";
	
	public static String DoRepalce(String OringinStr,String StrToreplace){
		if(OringinStr.trim().equals("")||StrToreplace.trim().equals("")){
			return OringinStr;
		}else if(OringinStr.trim().equals("na")||StrToreplace.trim().equals("na")){
			return OringinStr;
		}else{
			return OringinStr.replace(token,StrToreplace);
		}
	}
	
	public static String DoRepalceWithToken(String OringinStr,String StrToreplace,String Token){
		if(OringinStr.trim().equals("")||StrToreplace.trim().equals("")){
			return OringinStr;
		}else if(OringinStr.trim().equals("na")||StrToreplace.trim().equals("na")){
			return OringinStr;
		}else{
			return OringinStr.replace(Token,StrToreplace);
		}
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
