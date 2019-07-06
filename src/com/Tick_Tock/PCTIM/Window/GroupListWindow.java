package com.Tick_Tock.PCTIM.Window;

import com.googlecode.lanterna.gui2.*;
import java.util.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.Tick_Tock.PCTIM.*;
import com.Tick_Tock.PCTIM.sdk.*;
import com.Tick_Tock.PCTIM.Utils.*;
import com.googlecode.lanterna.gui2.table.*;
import com.googlecode.lanterna.*;

public class GroupListWindow extends BaseWindow
{

	private Panel contentPanel;

	private Button button;

	private ChatWindow chatwindow;

	private Table<String> table;

	private MainApp app;
	public GroupListWindow(String title,ChatWindow _chatwindow,MainApp _app){
		super(title);
		this.app=_app;
		this.chatwindow=_chatwindow;
		this.setHints(Arrays.asList(Window.Hint.FIXED_SIZE,Window.Hint.NO_POST_RENDERING));
		this.contentPanel = new Panel(new LinearLayout(Direction.VERTICAL)); // can hold multiple sub-components that will be added to a wind
		this.table= new Table<String>("群号码","群名称");
		this.table.setSelectAction(new Runnable() {
				@Override
				public void run() {
					List<String> data = table.getTableModel().getRow(table.getSelectedRow());
					GroupListWindow.this.chatwindow.onupdate(Long.parseLong(data.get(0)),2,data.get(1));
					GroupListWindow.this.app.setvisiblewindow(GroupListWindow.this.chatwindow);
				}
			});
		this.contentPanel.addComponent(this.table);
		this.setComponent(this.contentPanel);
	}

	public static GroupListWindow getconvertobj(Window p1)
	{
		return (GroupListWindow)p1;
	}
	public Table getlisttable()
	{
		return this.table;
	}
	
	@Override public void setsize(TerminalSize size){
		this.setSize(new TerminalSize(size.getColumns()/2-2,size.getRows()/10*9));
	}

	@Override public void setposition(TerminalSize size){
		this.setPosition(new TerminalPosition(size.getColumns()/2,size.getRows()/10));

	}
	
	
	
	
	public void settablesize(TerminalSize size){
		this.table.setPreferredSize(new TerminalSize(this.getSize().getColumns(),size.getRows()));
	}
	public void setgrouplist(QQUser user)
	{
		if(user.friend_list!=null){
			for(Group_List.Group group:user.group_list.getall_group()){
				this.table.getTableModel().addRow(""+group.group_uin,group.group_name);
			}

		}else{

		}
	}
}

