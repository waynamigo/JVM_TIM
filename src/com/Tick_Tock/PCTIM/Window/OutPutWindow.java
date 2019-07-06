package com.Tick_Tock.PCTIM.Window;
import com.googlecode.lanterna.gui2.*;
import java.util.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.*;

public class OutPutWindow extends BaseWindow
{

	private TextBox textbox;

	private Panel contentPanel;

	private Button button;


	public OutPutWindow(String title){
		super(title);
		this.setHints(Arrays.asList(Window.Hint.FIXED_SIZE,Window.Hint.NO_POST_RENDERING));

		this.contentPanel = new Panel(new LinearLayout(Direction.VERTICAL)); // can hold multiple sub-components that will be added to a window
		this.textbox = new TextBox().setReadOnly(true);
		this.button=new Button(" 标记");
		this.contentPanel.addComponent(this.button);
		this.contentPanel.addComponent(this.textbox);
		com.googlecode.lanterna.gui2.TextBox.TextBoxRenderer tbr = this.textbox.getRenderer();
		com.googlecode.lanterna.gui2.TextBox.DefaultTextBoxRenderer dtbr = (TextBox.DefaultTextBoxRenderer) tbr;
	    dtbr.setHideScrollBars(true);
		
	
		this.setComponent(contentPanel);
		

	}

	public void setlogsize(TerminalSize size, int p1)
	{
		this.textbox.setPreferredSize(new TerminalSize(size.getColumns(),size.getRows()/10*9));
		
	}

	@Override public void setsize(TerminalSize size){
		this.setSize(new TerminalSize(size.getColumns()-2,size.getRows()/10*9));
	}

	@Override public void setposition(TerminalSize size){
		this.setPosition(new TerminalPosition(0,size.getRows()/10));

	}
	
	public static OutPutWindow getconvertobj(Window p1)
	{
		return (OutPutWindow)p1;
	}
	
	public void setlogsize(TerminalSize size){
		this.textbox.setPreferredSize(size);
	}

	public Button getbutton(){
		return this.button;
	}
	
	public void print(String text){
		this.textbox.addLine(text);
		this.textbox.handleInput(new KeyStroke(KeyType.ArrowDown));
	}



}

