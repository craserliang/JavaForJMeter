package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.lang.String.*;

import com.esms.common.entity.TemplateData;

public class CsvReader {
	private File csv=null;
	private BufferedReader br = null;
	private List<String[]> allString = new ArrayList<>();
	
	public CsvReader(String path){
		
		this.csv=new File(path);
		try
	    {
	        br = new BufferedReader(new FileReader(csv));
	    } catch (FileNotFoundException e)
	    {
	        e.printStackTrace();
	    }
	}
	
	
	public CsvReader DoRaedcsv(){
		String line = "";
	    String everyLine = "";
	    try {
	            while ((line = br.readLine()) != null)  //读取到的内容给line变量
	            {
	                everyLine = line;
	                String eachColum[]=everyLine.split(",");
	                allString.add(eachColum);
	            }
//	            System.out.println("csv表格中所有行数："+allString.size());
	    } catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return this;
	}
	
	public String[] getTitle(){
		return this.allString.get(0);
	}
	
	public List<String[]> getContent(){
		List<String[]> content = new ArrayList<>();
		content.addAll(allString);
		content.remove(0);
		return content;
	}
	
	public int getLineCount(){
		return this.allString.size();
	}
	
	
//	public static void main(String[] args)
//	{
////	    File csv = new File("F:\\临时文件\\日志记录文件\\通讯录5.csv");  // CSV文件路径
//		String path="F:\\临时文件\\日志记录文件\\通讯录5.csv";
//		CsvReader a=new CsvReader(path);
//		System.out.println(a.DoRaedcsv().getTitle());
//		for (int i = 0; i < a.DoRaedcsv().getContent().size(); i++) {
//			for (int j = 0; j < a.DoRaedcsv().getContent().get(i).length; j++) {
//				System.out.print(a.DoRaedcsv().getContent().get(i)[j]);
//			}
//			System.out.println("");
//			
//		}
//
//	}
}
