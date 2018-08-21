package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.xml.soap.SOAPElement;

import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;

import test.ShellSample;
import test.Sms_Test;

public class ShellSampleGui extends AbstractSamplerGui{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabeledTextField IPTextField = new JLabeledTextField("IP");
	private JLabeledTextField UserTextField = new JLabeledTextField("USER");
	private JLabeledTextField PasswordTextField = new JLabeledTextField("PASSWORD");
	private JLabeledTextField ComandTextField = new JLabeledTextField("Comand"); 

	public ShellSampleGui() {
		super();
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout()); // 设置布局为JMeter内置的BorderLayout
		setBorder(makeBorder()); // 设置边框为默认边框
		add(makeTitlePanel(),BorderLayout.NORTH); // 添加JMeter内置的第一栏名称、注解部分
		JPanel ShellConfigurePanel = new JPanel(new BorderLayout()); // 创建一个Panel
		ShellConfigurePanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Configure")); // 设置Panel的边框为带有蚀刻风格的边框线，并且带有名称
		VerticalPanel configureVerticalPanel = new VerticalPanel(5, VerticalPanel.LEFT_ALIGNMENT); // 创建垂直对齐且左对齐的panel
		JPanel configurePanel = new HorizontalPanel(); // 创建水平对齐的Panel
		configurePanel.add(IPTextField);
		configurePanel.add(UserTextField);
		configurePanel.add(PasswordTextField);
		configureVerticalPanel.add(configurePanel); // 将"xml file"输入框添加到configureVerticalPanel中
		configureVerticalPanel.add(ComandTextField); // 将"xml file"输入框添加到configureVerticalPanel中
		ShellConfigurePanel.add(configureVerticalPanel); // 将configureVerticalPanel添加到soapConfigurePanel中
		add(ShellConfigurePanel,BorderLayout.CENTER); // 将soapConfigurePanel添加到界面上
	}

	@Override
	public TestElement createTestElement() {
		// TODO Auto-generated method stub
		ShellSample sampler=new ShellSample();
		modifyTestElement(sampler);
		return sampler;
	}

	@Override
	public String getLabelResource() {
		// TODO Auto-generated method stub
		return "Shell Sample";
	}
	
	@Override
	public String getStaticLabel() {
		return "Shell Sample";
	}
	
	@Override
	public void modifyTestElement(TestElement arg0) {
		// TODO Auto-generated method stub
		super.configureTestElement(arg0);
		ShellSample sampler=(ShellSample) arg0;
		sampler.setIp(IPTextField.getText());
		sampler.setUsername(UserTextField.getText());
		sampler.setPassword(PasswordTextField.getText());
		sampler.setCommand(ComandTextField.getText());
	}
	
	@Override
	public void configure(TestElement te) {
		super.configure(te); // 不可缺少，否则将会出现名字丢失
		ShellSample sampler = (ShellSample)te;
		IPTextField.setText(sampler.getIp());
		UserTextField.setText(sampler.getUsername());
		PasswordTextField.setText(sampler.getPassword());
		ComandTextField.setText(sampler.getCommand());
	}
	
	@Override
	public void clearGui() {
		super.clearGui();
		IPTextField.setText("");
		UserTextField.setText("");
		PasswordTextField.setText("");
		ComandTextField.setText("");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
