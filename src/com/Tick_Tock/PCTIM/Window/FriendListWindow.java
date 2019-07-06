package com.Tick_Tock.PCTIM.Window;
import com.googlecode.lanterna.gui2.*;
import java.util.*;
import com.Tick_Tock.PCTIM.*;
import com.googlecode.lanterna.gui2.table.*;
import com.Tick_Tock.PCTIM.sdk.*;
import com.Tick_Tock.PCTIM.Utils.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.*;

public class FriendListWindow extends BaseWindow
{

	private Panel contentPanel;

	private ChatWindow chatwindow;

	private Button button;

	private Table<String> table;

	private MainApp app;

	
	public FriendListWindow(String title,ChatWindow _chatwindow,MainApp _app){
		super(title);
		this.chatwindow = _chatwindow;
		this.app=_app;
		this.setHints(Arrays.asList(Window.Hint.FIXED_SIZE,Window.Hint.NO_POST_RENDERING));
		this.contentPanel = new Panel(new LinearLayout(Direction.VERTICAL)); // can hold multiple sub-components that will be added to a wind
		this.table= new Table<String>("好友号码","好友名称");
		this.table.setSelectAction(new Runnable() {
				@Override
				public void run() {
					List<String> data = table.getTableModel().getRow(table.getSelectedRow());
					FriendListWindow.this.chatwindow.onupdate(Long.parseLong(data.get(0)),1,data.get(1));
					FriendListWindow.this.app.setvisiblewindow(FriendListWindow.this.chatwindow);
				}
			});
		this.contentPanel.addComponent(this.table);
		this.setComponent(this.contentPanel);
		
	}

	public Table getlisttable()
	{
		return this.table;
	}

	
	@Override public void setsize(TerminalSize size){
		this.setSize(new TerminalSize(size.getColumns()/2-1,size.getRows()/10*9));
	}

	@Override public void setposition(TerminalSize size){
		this.setPosition(new TerminalPosition(0,size.getRows()/10));

	}
	
	public static FriendListWindow getconvertobj(Window p1)
	{
		return (FriendListWindow)p1;
	}
	
	
	public void settablesize(TerminalSize size){
		this.table.setPreferredSize(new TerminalSize(size.getColumns()/2,size.getRows()));
	}
	

	
	
	public void setfriendlist(final QQUser user){
		
		if(user.friend_list!=null){
			for(Friend_List.Friend members:user.friend_list.members){
				this.table.getTableModel().addRow(""+members.friend_uin,members.friend_name);
			}
			
		}else{
			
		}
	
	}


	




	
	




}
