package Util;

import java.util.HashMap;
import java.util.Map;

import com.esms.common.entity.TemplateData;
import com.esms.common.util.JsonUtil;

public class JsonUtil_item {
	private String content;
	
	public String getContent(){
		Map<String, TemplateData> templateValue = new HashMap<String, TemplateData>();
		templateValue.put("batchName", new TemplateData("短信http接口测试"));
		templateValue.put("items", new TemplateData("bbb"));
		templateValue.put("content", new TemplateData("短信http接口测试"));
		templateValue.put("msgType", new TemplateData("sms"));
		templateValue.put("bizType", new TemplateData("1"));
		try {
			return content = JsonUtil.serialize(templateValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
//	public static void main(String[] args) 
//	{ // TODO Auto-generated method stub
//		JsonUtil_item a=new JsonUtil_item();
//		System.out.println(a.getContent());
//	}
}
