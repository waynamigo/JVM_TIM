package com.ignoreos.jvmqq.Robot;
import com.ignoreos.jvmqq.Message.SendMessage;
import com.ignoreos.jvmqq.QQUser;
import com.ignoreos.jvmqq.Socket.Udpsocket;
import com.ignoreos.jvmqq.Utils.Util;
import com.ignoreos.jvmqq.sdk.Group_List;
import com.ignoreos.jvmqq.sdk.API;
import com.ignoreos.jvmqq.sdk.Friend_List;
import com.ignoreos.jvmqq.sdk.MessageFactory;

public class RobotApi implements API
{

	
	


private Udpsocket socket = null;
	private QQUser user = null;
	 
 public RobotApi(Udpsocket _socket, QQUser _user){
   this.user = _user;
   this.socket = _socket;
   Util.api=this;
 }
@Override
	public void SendGroupMessage(MessageFactory factory){

		SendMessage.SendGroupMessage(this.user,this.socket,factory);

	}
	@Override
	public void SendFriendMessage(MessageFactory factory){

		SendMessage.SendFriendMessage(this.user,this.socket,factory);

	}

	@Override
	public Group_List getgrouplist()
	{
		
		return this.user.group_list;
	}

	@Override
	public Friend_List getfriendlist()
	{
		return this.user.friend_list;
	}

	
}
