package Util.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import Util.PassWordUtil;

public class Encryption extends AbstractFunction {

	private static final List<String> desc = new LinkedList<String>();
	static {
		desc.add("An encryption of username");
		desc.add("An encryption of password");
	}
	private static final String KEY = "__Encryption";
	private Object[] values;

    
	@Override
	public String execute(SampleResult arg0, Sampler arg1)
			throws InvalidVariableException {
		// TODO Auto-generated method stub
		try{
			  PassWordUtil encryption=new PassWordUtil(((CompoundVariable)values[0]).execute().trim(),((CompoundVariable)values[1]).execute().trim());
			  return encryption.getbase64();
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public String getReferenceKey() {
		// TODO Auto-generated method stub
		return KEY;
	}

	@Override
	public void setParameters(Collection<CompoundVariable> arg0)
			throws InvalidVariableException {
		// TODO Auto-generated method stub
		checkParameterCount(arg0,2 ); //检查参数的个数是否正确
		values = arg0.toArray(); //将值存入类变量中
	}

	@Override
	public List<String> getArgumentDesc() {
		// TODO Auto-generated method stub
		return desc;
	}

}
