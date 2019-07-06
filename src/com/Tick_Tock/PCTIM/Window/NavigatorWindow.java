package com.Tick_Tock.PCTIM.Window;
import com.googlecode.lanterna.gui2.*;
import java.util.*;
import com.googlecode.lanterna.*;



public class NavigatorWindow extends BaseWindow
{

	private Panel contentPanel;

	private Button button1;

	private Button button2;

	private Button button3;

	private Button button4;

	private Button button5;
	public NavigatorWindow(){
	    super("");
		super.setHints(Arrays.asList(Window.Hint.FIXED_SIZE,Window.Hint.NO_POST_RENDERING));

		this.contentPanel = new Panel(new LinearLayout(Direction.HORIZONTAL)); // can hold multiple sub-components that will be added to a wind
		this.button1=new Button(" 消息列表");
		this.button2=new Button(" 聊天窗口");
		this.button3=new Button(" 联系列表");
		this.button4=new Button(" 插件列表");
		this.button5=new Button(" 日志窗口");
		
		this.contentPanel.addComponent(this.button1);
		this.contentPanel.addComponent(this.button2);
		this.contentPanel.addComponent(this.button3);
		this.contentPanel.addComponent(this.button4);
		this.contentPanel.addComponent(this.button5);
		
		this.setComponent(this.contentPanel);
	}

	public static NavigatorWindow getconvertobj(Window p1)
	{
		return (NavigatorWindow)p1;
	}



	public void setbuttonposition(){
		int width = this.getSize().getColumns();
		this.button1.setPosition(new TerminalPosition(0,0));
		this.button2.setPosition(new TerminalPosition(0,width/5));
		this.button3.setPosition(new TerminalPosition(0,width/5*2));
		this.button4.setPosition(new TerminalPosition(0,width/5*3));
		this.button4.setPosition(new TerminalPosition(0,width/5*4));
		
	}

	

	public Button getfocusedbutton(){

		for (Button button: Arrays.asList(this.button1,this.button2,this.button3,this.button4,this.button5)){
			if(button.isFocused()){
				return button;
			}
		}
		return null;
	}
	
	
	@Override public void setsize(TerminalSize size){
		this.setSize(new TerminalSize(size.getColumns()-2,size.getRows()/10-2));

	}
	
	@Override public void setposition(TerminalSize size){
		this.setPosition(new TerminalPosition(0,0));
		
	}
	
	
}

