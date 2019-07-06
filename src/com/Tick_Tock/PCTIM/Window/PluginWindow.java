package com.Tick_Tock.PCTIM.Window;
import java.util.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.*;
import com.googlecode.lanterna.*;
import java.io.*;
import java.net.*;
import com.Tick_Tock.PCTIM.Utils.*;
import com.Tick_Tock.PCTIM.sdk.*;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

public class PluginWindow extends BaseWindow
{

	private Panel contentPanel;

	private Table<String> table;

	private Button button;
	
	public PluginWindow(String title)
	{
		super(title);
		super.setHints(Arrays.asList(Window.Hint.FIXED_SIZE, Window.Hint.NO_POST_RENDERING));
		this.contentPanel = new Panel(new LinearLayout(Direction.VERTICAL)); // can hold multiple sub-components that will be added to a wind
		this.table = new Table<String>("类型","文件名", "插件名", "是否开启");
		this.button = new Button(" 刷新列表",new Runnable(){
				@Override public void run(){
					int rowcount = PluginWindow.this.table.getTableModel().getRowCount();
					for(int i =0;i<rowcount;i+=1){
						PluginWindow.this.table.getTableModel().removeRow(0);
					}
					
					PluginWindow.this.displaypluginlist();
				}
			});
		this.contentPanel.addComponent(this.button);
		this.contentPanel.addComponent(this.table);
		this.displaypluginlist();
		this.table.setSelectAction(new Runnable(){
			@Override public void run(){
				if(table.getTableModel().getRowCount()==0){
					return;
				}
				List<String> data = table.getTableModel().getRow(table.getSelectedRow());
				if(data.get(0).equals("java")){
					if(data.get(3).equals("否")){
						if(Util.javarobot!=null){
						    Util.javarobot.plug(data.get(1));
							Util.setactivatedplugin(data.get(2));
							table.getTableModel().setCell(3,table.getSelectedRow(),"是");
						}
					}else if(data.get(3).equals("是")){
						if(Util.javarobot!=null){
						    Util.javarobot.unplug(data.get(2));
						    Util.setdeactivatedplugin(data.get(2));
						    table.getTableModel().setCell(3,table.getSelectedRow(),"否");
						}
					}
				}else if(data.get(0).equals("lua")){
					if(data.get(3).equals("否")){
						if(Util.luarobot!=null){
						    Util.luarobot.plug(data.get(1));
							Util.setactivatedplugin(data.get(2));
							table.getTableModel().setCell(3,table.getSelectedRow(),"是");
						}
					}else if(data.get(3).equals("是")){
						if(Util.luarobot!=null){
						    Util.luarobot.unplug(data.get(2));
						    Util.setdeactivatedplugin(data.get(2));
						    table.getTableModel().setCell(3,table.getSelectedRow(),"否");
						}
					}
				}
				
			}
		});
		
		this.setComponent(this.contentPanel);

	}

	private void displaypluginlist()
	{
		try
		{
			String exact_directory  = new File("").getCanonicalPath();
			File plugin_path = new File(exact_directory + "/plugin");
			String[] plugin_list = plugin_path.list();
			if (plugin_list != null)
			{
				List<String> list = Arrays.asList(plugin_list);
				for (String file: list)
				{
					if (file.endsWith(".jar"))
					{

						ClassLoader loader = new URLClassLoader(new URL[]{new URL("file://" + exact_directory + "/plugin/" + file)});
						Class<?> pluginCls = loader.loadClass("com.robot.Main");
						Plugin plugin = (Plugin)pluginCls.getDeclaredConstructor().newInstance();
						if (plugin.name() != null)
						{
							if (Util.isactivatedplugin(plugin.name()))
							{
								this.table.getTableModel().addRow("java",file,plugin.name(),"是");
							}else{
								this.table.getTableModel().addRow("java",file,plugin.name(),"否");
							}
						}

					}
				}
			}

			plugin_path = new File(exact_directory + "/plugin-lua");
			plugin_list = plugin_path.list();

			if (plugin_list != null)
			{
				List<String> list = Arrays.asList(plugin_list);
				Globals globals = JsePlatform.standardGlobals();
				for (String file: list)
				{
					if (file.endsWith(".lua"))
					{
						String script_path=exact_directory + "/plugin-lua/" + file;
						LuaValue plugin = globals.loadfile(script_path).call();
						final LuaValue name = plugin.get(LuaValue.valueOf("name"));
						if (name.call() != null)
						{
							if (Util.isactivatedplugin(name.call().toString()))
							{
								this.table.getTableModel().addRow("lua",file,name.call().toString(),"是");
							}else{
								this.table.getTableModel().addRow("lua",file,name.call().toString(),"否");
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
	
	@Override public void setsize(TerminalSize size)
	{
		this.setSize(new TerminalSize(size.getColumns()-2, size.getRows() / 10 * 9 - 2));
	}

	@Override public void setposition(TerminalSize size)
	{
		this.setPosition(new TerminalPosition(0, size.getRows() / 10));

	}
	public Button getrefreshbutton()
	{
		return this.button;
	}
	
	public void settablesize(TerminalSize size)
	{
		this.table.setPreferredSize(new TerminalSize(size.getColumns() - 2, size.getRows() / 10 * 9 - 2));

	}
	
	public static PluginWindow convertobj(Object p1)
	{
		return (PluginWindow)p1;
	}
	
}
