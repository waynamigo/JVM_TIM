package com.Tick_Tock.PCTIM;

import com.Tick_Tock.PCTIM.Socket.*;
import com.Tick_Tock.PCTIM.Utils.*;
import com.Tick_Tock.PCTIM.Window.*;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.terminal.ansi.*;
import javax.net.ssl.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.input.*;
import java.util.concurrent.atomic.*;
import com.googlecode.lanterna.graphics.*;
import com.Tick_Tock.PCTIM.Theme.*;
import java.util.*;

public class MainApp extends Thread
{
	private Terminal term = null;

	private Screen screen = null;

	private TerminalSize size;

	private MultiWindowTextGUI textGUI;

	private ChatWindow chatwindow;

	private FriendListWindow friendlistwindow;

	private GroupListWindow grouplistwindow;

	private OutPutWindow logoutputwindow;

	private listener listener;

	private QQUser user;

	private NavigatorWindow navigatorwindow;

	private ChatListWindow chatlistwindow;

	private List<Window> windows;

	private PluginWindow pluginwindow;
	

	@Override public void run()
	{

		try
		{
			Util.trustAllHttpsCertificates();

			HttpsURLConnection.setDefaultHostnameVerifier(Util.hv);

            // Swing is the default backend on Windows unless explicitly
            // overridden by jexer.Swing.

			this.initterminal();
			this.startloginprocess();
			this.initwindows();
			this.initlistener();
			this.addallllistenertowindow();
			this.setallwindowsize();
			this.addallwindowtoui();
			this.setallwheigetelement();
			this.setallwindowposition();
			this.updateglobalwindow();
			this.setvisiblewindow(this.chatlistwindow);
			this.settheme();
			new qqinfo().setuser(this.user).setfriendwindow(this.friendlistwindow).setgroupwindow(this.grouplistwindow).start();
			Util.output = this.logoutputwindow;
			chatlistwindow.waitUntilClosed();
        }
		catch (Exception e)
		{
            e.printStackTrace();
			System.exit(-1);
        }





	}

	
	public void setvisiblewindow(Window _window)
	{
		for (Window window:this.windows)
		{
			if (window == _window)
			{
				window.setVisible(true);
				this.textGUI.setActiveWindow(window);
			}
			else
			{
				window.setVisible(false);
			}
		}
	}

	public void setvisiblewindow(Window... _window)
	{
		for (Window window:this.windows)
		{
			if (Arrays.asList(_window).contains(window))
			{
				window.setVisible(true);
				this.textGUI.setActiveWindow(window);
			}
			else
			{
				window.setVisible(false);
			}
		}
	}

	private void updateglobalwindow()
	{
		Util.chatwindow = this.chatwindow;
		Util.chatlistwindow = this.chatlistwindow;
	}



	private void settheme()
	{
		this.textGUI.setTheme(new DelegatingTheme(this.textGUI.getTheme()) {
				@Override
				public ThemeDefinition getDefinition(Class<?> clazz)
				{
					ThemeDefinition themeDefinition = super.getDefinition(clazz);
					return new MainTheme(themeDefinition);
				}
			});
	}

	private void setallwheigetelement()
	{
		this.logoutputwindow.setlogsize(this.size, 1);
		this.grouplistwindow.settablesize(this.size);
		this.chatlistwindow.settablesize(this.size);
		this.chatwindow.settextboxsize();
		this.friendlistwindow.settablesize(this.size);
		this.navigatorwindow.setbuttonposition();
		this.pluginwindow.settablesize(this.size);
	}
	
	
	private void addallllistenertowindow()
	{
		this.friendlistwindow.addWindowListener(this.listener);
		this.grouplistwindow.addWindowListener(this.listener);
		this.chatwindow.addWindowListener(this.listener);
		this.navigatorwindow.addWindowListener(this.listener);
		this.chatlistwindow.addWindowListener(this.listener);
		this.logoutputwindow.addWindowListener(this.listener);
		this.pluginwindow.addWindowListener(this.listener);
	}

	private void setallwindowposition()
	{
		this.friendlistwindow.setposition(this.size);
		this.grouplistwindow.setposition(this.size);
		this.chatwindow.setposition(this.size);
		this.logoutputwindow.setposition(this.size);
		this.chatlistwindow.setposition(this.size);
		this.navigatorwindow.setposition(this.size);
		this.pluginwindow.setposition(this.size);
	}

	private void addallwindowtoui()
	{
		this.textGUI.addWindow(this.friendlistwindow);
		this.textGUI.addWindow(this.grouplistwindow);
		this.textGUI.addWindow(this.chatwindow);
		this.textGUI.addWindow(this.logoutputwindow);
		this.textGUI.addWindow(this.navigatorwindow);
		this.textGUI.addWindow(this.chatlistwindow);
		this.textGUI.addWindow(this.pluginwindow);
	}

	private void setallwindowsize()
	{
		this.friendlistwindow.setsize(this.size);
		this.grouplistwindow.setsize(this.size);
		this.chatwindow.setsize(this.size);
		this.logoutputwindow.setsize(this.size);
		this.chatlistwindow.setsize(this.size);
		this.navigatorwindow.setsize(this.size);
		this.pluginwindow.setsize(this.size);
	}



	private void initlistener()
	{
		this.listener = new listener()
		.setnavigatorwindow(this.navigatorwindow)
		.setlogwindow(this.logoutputwindow)
		.setchatlistwindow(this.chatlistwindow)
		.setchatwindow(this.chatwindow)
		.setfriendwindow(this.friendlistwindow)
		.setgroupwindow(this.grouplistwindow)
		.setpluginwindow(this.pluginwindow)
		.settextgui(this.textGUI)
		.setapp(this);
	}

	private void initwindows()
	{
		this.chatwindow = new ChatWindow("");
		this.friendlistwindow = new FriendListWindow("", chatwindow, this);
		this.grouplistwindow = new GroupListWindow("", chatwindow, this);
		this.chatlistwindow = new ChatListWindow("",chatwindow,this);
		this.logoutputwindow = new OutPutWindow("");
		this.navigatorwindow = new NavigatorWindow();
		this.pluginwindow = new PluginWindow("");
		this.windows =Arrays.asList(this.chatwindow, this.chatlistwindow, this.grouplistwindow, this.friendlistwindow, this.logoutputwindow,this.pluginwindow);
	}

	private void initterminal() throws Exception
	{
		this.term = new UnixTerminal();
		this.screen = new TerminalScreen(term);
		this.screen.startScreen();
		this.textGUI = new MultiWindowTextGUI(screen);
		this.size = screen.getTerminalSize();
	}


	private void startloginprocess()
	{
		String qq =Util.read_property("account");
		String password = Util.read_property("password");
		byte[] passwordmd5=new byte[0];
		LoginManager manager = null ;
		if (qq == null || password == null)
		{
			LoginWindow loginwindow = new LoginWindow("登录");
			textGUI.addWindow(loginwindow);
			loginwindow.waitUntilClosed();
			qq = loginwindow.account();
			passwordmd5 = Util.MD5(loginwindow.password());
			user = new QQUser(Long.parseLong(qq), passwordmd5);
			manager = new LoginManager(Long.parseLong(qq), passwordmd5);

		}
		else
		{
			passwordmd5 = Util.str_to_byte(password);
			user = new QQUser(Long.parseLong(qq), passwordmd5);
			manager = new LoginManager(Long.parseLong(qq), passwordmd5);


		}

		OutPutWindow processingloginoutputwindow = new OutPutWindow("登录日志");
		processingloginoutputwindow.setSize(new TerminalSize(size.getColumns() - 2, size.getRows() - 2));
		processingloginoutputwindow.setlogsize(new TerminalSize(size.getColumns() - 3, size.getRows() - 3));

		this.textGUI.addWindow(processingloginoutputwindow);
		processingloginoutputwindow.setPosition(new TerminalPosition(0, 0));
		Util.output = processingloginoutputwindow;
		manager.st(processingloginoutputwindow);
		processingloginoutputwindow.waitUntilClosed();
		this.textGUI.removeWindow(processingloginoutputwindow);
		this.user = manager.user;
	}


}

class listener implements WindowListener
{

	private ChatWindow chatwindow;

	private GroupListWindow groupwindow;

	private FriendListWindow friendwindow;

	private MultiWindowTextGUI ui;

	private NavigatorWindow navigatorwindow;

	private ChatListWindow chatlistwindow;

	private MainApp app;

	private OutPutWindow logwindow;

	private PluginWindow pluginwindow;

	public listener setapp(MainApp _app)
	{
		this.app = _app;
		return this;
	}

	public listener setchatwindow(ChatWindow _window)
	{
		this.chatwindow = _window;
		return this;
	}
	public listener setgroupwindow(GroupListWindow _window)
	{
		this.groupwindow = _window;
		return this;
	}

	public listener setnavigatorwindow(NavigatorWindow _window)
	{
		this.navigatorwindow = _window;
		return this;
	}
	public listener setchatlistwindow(ChatListWindow _window)
	{
		this.chatlistwindow = _window;
		return this;
	}
	public listener setfriendwindow(FriendListWindow _window)
	{
		this.friendwindow = _window;
		return this;
	}
	public listener setlogwindow(OutPutWindow _window)
	{
		this.logwindow = _window;
		return this;
	}
	public listener setpluginwindow(PluginWindow _window)
	{
		this.pluginwindow = _window;
		return this;
	}
	public listener settextgui(MultiWindowTextGUI _ui)
	{
		this.ui = _ui;
		return this;
	}

	@Override
	public void onInput(Window p1, KeyStroke p2, AtomicBoolean p3)
	{
		if (p1 instanceof NavigatorWindow)
		{
			if (p2.getKeyType() == KeyType.Enter)
			{
				Button button = NavigatorWindow.getconvertobj(p1).getfocusedbutton();
				switch (button.getLabel())
				{
					case " 消息列表":{
							this.app.setvisiblewindow(this.chatlistwindow);
						}
						break;

					case " 联系列表":{
							this.app.setvisiblewindow(this.friendwindow, this.groupwindow);
						}
						break;
					case " 日志窗口":{
							this.app.setvisiblewindow(this.logwindow);
						}
						break;
					case " 聊天窗口":{
							this.app.setvisiblewindow(this.chatwindow);
						}
						break;
					case " 插件列表":{
							this.app.setvisiblewindow(this.pluginwindow);
						}
						break;
				    default:{


						}

				}
			}

		}
		else if (p1 instanceof ChatListWindow)
		{
			if (p2.getKeyType() == KeyType.ArrowUp)
			{
				if (ChatListWindow.convertobj(p1).getlisttable().getTableModel().getRowCount() == 0 || ChatListWindow.convertobj(p1).getlisttable().getSelectedRow() == 0)
				{
					this.ui.setActiveWindow(this.navigatorwindow);
				}
			}
		}
		else if (p1 instanceof ChatWindow)
		{
			ChatWindow chatwd = ChatWindow.getconvertobj(p1);
			TextBox box = chatwd.getinputbox();
			if (p2.getKeyType() == KeyType.ArrowUp)
			{
				if (box.isFocused())
				{
					this.ui.setActiveWindow(this.navigatorwindow);
				}
			}
			else if (p2.getKeyType() == KeyType.Enter)
			{
				if (box.isFocused())
				{
					if (box.getText().equals(""))
					{

					}
					else
					{
						Util.SendMessage(chatwd.chattype, chatwd.uin, box.getText());
						box.setText("");
					}
				}
			}
		}
		else if (p1 instanceof FriendListWindow)
		{
			if (p2.getKeyType() == KeyType.ArrowUp)
			{
				if (FriendListWindow.getconvertobj(p1).getlisttable().getTableModel().getRowCount() == 0 || FriendListWindow.getconvertobj(p1).getlisttable().getSelectedRow() == 0)
				{
					this.ui.setActiveWindow(this.navigatorwindow);
				}
			}
			else if (p2.getKeyType() == KeyType.ArrowRight)
			{
				this.ui.setActiveWindow(this.groupwindow);
			}
		}
		else if (p1 instanceof GroupListWindow)
		{
			if (p2.getKeyType() == KeyType.ArrowUp)
			{
				if (GroupListWindow.getconvertobj(p1).getlisttable().getTableModel().getRowCount() == 0 || GroupListWindow.getconvertobj(p1).getlisttable().getSelectedRow() == 0)
				{
					this.ui.setActiveWindow(this.navigatorwindow);
				}
			}
			else if (p2.getKeyType() == KeyType.ArrowLeft)
			{
				this.ui.setActiveWindow(this.friendwindow);
			}
		}
		
		else if (p1 instanceof PluginWindow)
		{
			if (p2.getKeyType() == KeyType.ArrowUp)
			{
				if (PluginWindow.convertobj(p1).getrefreshbutton().isFocused())
				{
					this.ui.setActiveWindow(this.navigatorwindow);
				}
			}
		}
		else if (p1 instanceof OutPutWindow)
		{
			if (p2.getKeyType() == KeyType.ArrowUp)
			{
				if (OutPutWindow.getconvertobj(p1).getbutton().isFocused())
				{
					this.ui.setActiveWindow(this.navigatorwindow);
				}
			}
		}


	}

	@Override
	public void onUnhandledInput(Window p1, KeyStroke p2, AtomicBoolean p3)
	{

	}

	@Override
	public void onResized(Window p1, TerminalSize p2, TerminalSize p3)
	{

	}

	@Override
	public void onMoved(Window p1, TerminalPosition p2, TerminalPosition p3)
	{

	}






}




class qqinfo extends Thread
{

	private QQUser user;

	private FriendListWindow friendlistwindow;

	private GroupListWindow grouplistwindow;

	public qqinfo setuser(QQUser _user)
	{
		this.user = _user;
		return this;
	}
	public qqinfo setfriendwindow(FriendListWindow window)
	{
		this.friendlistwindow = window;
		return this;
	}

	public qqinfo setgroupwindow(GroupListWindow window)
	{
		this.grouplistwindow = window;
		return this;
	}
	@Override public void run()
	{
		Util.getquncookie(this.user);
		Util.getqunlist(this.user);
		Util.getfriendlist(this.user);
		this.friendlistwindow.setfriendlist(this.user);
		this.grouplistwindow.setgrouplist(this.user);
	}
}

