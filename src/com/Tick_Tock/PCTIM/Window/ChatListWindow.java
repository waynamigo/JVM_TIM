package com.Tick_Tock.PCTIM.Window;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.table.*;
import java.util.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.*;
import com.Tick_Tock.PCTIM.sdk.*;
import com.Tick_Tock.PCTIM.Utils.*;
import com.Tick_Tock.PCTIM.*;

public class ChatListWindow extends BaseWindow
{


	private Panel contentPanel;

	private Table<String> table;
	private ChatWindow chatwindow;

	private MainApp app;
	public ChatListWindow(String title,ChatWindow _chatwindow,MainApp _app)
	{
		super(title);
		this.chatwindow=_chatwindow;
		this.app=_app;
		super.setHints(Arrays.asList(Window.Hint.FIXED_SIZE, Window.Hint.NO_POST_RENDERING));
		this.contentPanel = new Panel(new LinearLayout(Direction.VERTICAL)); // can hold multiple sub-components that will be added to a wind
		this.table = new Table<String>("类型","QQ", "名称", "最后消息");
		this.table.setSelectAction(new Runnable() {
				@Override
				public void run() {
					if(table.getTableModel().getRowCount()==0){
						return;
					}
					List<String> data = table.getTableModel().getRow(table.getSelectedRow());
					if(data.get(0).equals("好友")){
						ChatListWindow.this.chatwindow.onupdate(Long.parseLong(data.get(1)),1,data.get(2));
					}else{
						ChatListWindow.this.chatwindow.onupdate(Long.parseLong(data.get(1)),2,data.get(2));		
					}
					ChatListWindow.this.app.setvisiblewindow(ChatListWindow.this.chatwindow);
				}
			});
		this.contentPanel.addComponent(this.table);
		this.setComponent(this.contentPanel);

	}

	public void onmessage(QQMessage qqmessage)
	{
		if (qqmessage.Group_uin == 0)
		{
			if (!this.ischatexist(qqmessage.Sender_Uin))
			{
				this.table.getTableModel().addRow("好友",""+qqmessage.Sender_Uin,Util.getFriendnamebyuin(qqmessage.Sender_Uin),qqmessage.Message);
			}else{
				this.updatemessage(qqmessage.Sender_Uin,qqmessage.Message);
			}
		}else{
			if (!this.ischatexist(qqmessage.Group_uin))
			{
				this.table.getTableModel().addRow("群组",""+qqmessage.Group_uin,Util.getGroupnamebyuin(qqmessage.Group_uin),qqmessage.SendName+":"+qqmessage.Message);
			}else{
				this.updatemessage(qqmessage.Group_uin,qqmessage.SendName+":"+qqmessage.Message);
			}
		}
	}

	private void updatemessage(long uin, String message)
	{
		int index =0;
		List<List<String>> rows = this.table.getTableModel().getRows();
		for (List<String> row:rows)
		{
			if (Long.parseLong(row.get(1)) == uin)
			{
				this.table.getTableModel().setCell(3,index,message);
			}
			index+=1;
		}
		
	}

	


	private boolean ischatexist(long uin)
	{

		List<List<String>> rows = this.table.getTableModel().getRows();
		for (List<String> row:rows)
		{
			if (Long.parseLong(row.get(1)) == uin)
			{
				return true;
			}
		}
		return false;
	}


	public Table getlisttable()
	{
		return this.table;
	}

	@Override public void setsize(TerminalSize size)
	{
		this.setSize(new TerminalSize(size.getColumns()-2, size.getRows() / 10 * 9 - 2));
	}

	@Override public void setposition(TerminalSize size)
	{
		this.setPosition(new TerminalPosition(0, size.getRows() / 10));

	}

	public void settablesize(TerminalSize size)
	{
		this.table.setPreferredSize(new TerminalSize(size.getColumns() - 2, size.getRows() / 10 * 9 - 2));

	}





	public static ChatListWindow convertobj(Object p1)
	{
		return (ChatListWindow)p1;
	}
}
