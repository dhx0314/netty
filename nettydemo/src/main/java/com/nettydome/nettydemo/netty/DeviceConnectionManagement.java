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
public class DeviceConnectionManagement {


	private static HashMap<String, Channel> manager = new HashMap<>();

	public static void put(String senderId, Channel channel) {
		manager.put(senderId, channel);
	}
	
	public static Channel get(String senderId) {
		return manager.get(senderId);
	}


	
	public static void output() {
		for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
			log.info("DtuId: " + entry.getKey()
							+ ", ChannelId: " + entry.getValue().id().asLongText());
		}
	}

	public static void deleteChanel(Channel channel) {


		Iterator<Map.Entry<String, Channel>> it = manager.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Channel> entry = it.next();
			if(channel.id().asLongText().equals(entry.getValue().id().asLongText())){
				log.info("设备id: {} 从hashmap中移除",entry.getKey());
				it.remove();
			}
		}
		// TODO 把用户在线状态置为离线，需要强制注入userservice


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
	}

	public static Integer onlineCount() {

		Integer i = manager.size();
		log.info("当前在线设备数:{}",i);
		return i;
	}

}
