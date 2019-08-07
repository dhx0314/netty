package com.nettydome.nettydemo.netty;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description: 用户id和channel的关联关系处理
 */
@Slf4j
@Service
//名字应为连接管理
public class UserChannelRel {


	private static HashMap<String, Channel> manager = new HashMap<>();

	public static void put(String senderId, Channel channel) {
		manager.put(senderId, channel);
	}
	
	public static Channel get(String senderId) {
		return manager.get(senderId);
	}


	
	public static void output() {
		for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
			log.info("UserId: " + entry.getKey()
							+ ", ChannelId: " + entry.getValue().id().asLongText());
		}
	}

	public static void deleteChanel(Channel channel) {
		//以下用法会抛出java.util.ConcurrentModificationException
		/*
		for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {

			if(channel.id().asLongText().equals(entry.getValue().id().asLongText())){

				//移除 openid的hashMap，设置数据库中openid的状态为离线
				log.info("openId为{}从hashmap中移除",entry.getKey());
				manager.remove(entry.getKey());

				//强制注入userservice

			}
		}
		*/

		Iterator<Map.Entry<String, Channel>> it = manager.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Channel> entry = it.next();
			if(channel.id().asLongText().equals(entry.getValue().id().asLongText())){
				log.info("openId为{}从hashmap中移除",entry.getKey());
				it.remove();
				//将用户在线状态改成0
//				UserWxDao userWxDao = (UserWxDao) SpringTool.getBean("userWxDao");
//
//				userWxDao.outOnline(entry.getKey());
			}
		}
		// TODO 把用户在线状态置为离线，需要强制注入userservice
	}

	public static Integer onlineCount() {

		Integer i = manager.size();
		log.info("当前在线人数:{}",i);
		return i;
	}

//	public static void sendMessageToOpenId(String openId,String msg){
//		Channel channel = UserChannelRel.get(openId);
//        Gson gson = new Gson();
//
//		if (channel == null) {
//			log.info("Todo离线推送");
//			// TODO channel为空代表用户离线，推送消息（JPush，个推，小米推送）
//		} else {
//			// 当receiverChannel不为空的时候，从ChannelGroup去查找对应的channel是否存在
//			EchoServerHandler findChannel = EchoServerHandler.users.find(channel.id());
//			if (findChannel != null) {
//
//                DataContent content = new DataContent();
//                content.setAction(MsgActionEnum.ONLINEPUSH.getType());
//                ChatMsg chatMsgg = new ChatMsg();
//                //chatMsgg.setMsg("发送消息给"+openId);
//				chatMsgg.setMsg(msg);
//                content.setChatMsg(chatMsgg);
//                content.setExtand(UserChannelRel.onlineCount().toString());
//                channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(content)));
//				// 用户在线
////				channel.writeAndFlush(
////						new TextWebSocketFrame(
////								"发送消息给"+openId));
//			} else {
//				// 用户离线 TODO 推送消息
//				log.info("Todo离线推送");
//			}
//		}
//
//	}

//	public static void notifyAll(String msg){
//        Gson gson = new Gson();
//        DataContent content = new DataContent();
//        content.setAction(MsgActionEnum.ONLINEPUSH.getType());
//        ChatMsg chatMsgg = new ChatMsg();
//        chatMsgg.setMsg(msg);
//        content.setChatMsg(chatMsgg);
//        content.setExtand(UserChannelRel.onlineCount().toString());
//
//        ChatHandler.users.writeAndFlush(new TextWebSocketFrame(gson.toJson(content)));
//		//ChatHandler.users.writeAndFlush(new TextWebSocketFrame(msg));
//
//	}
}
