package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	  public static final String EMAIL = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$";
	  public static final String EMAIL_OR_PHONE = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$|^1[34578]\\d{9}$";
	  public static final String URL = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})(:[\\d]+)?([\\/\\w\\.-]*)*\\/?$";
	  
	  public static boolean match(String regex, String beTestString)
	  {
	    Pattern pattern = Pattern.compile(regex, 2);
	    Matcher matcher = pattern.matcher(beTestString);
	    return matcher.matches();
	  }
	  
	  public static boolean find(String regex, String beTestString)
	  {
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(beTestString);
	    return matcher.find();
	  }
	  
	  public static String findResult(String regex, String beFoundString)
	  {
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(beFoundString);
	    if (matcher.find()) {
	      return matcher.group();
	    }
	    return null;
	  }

}
