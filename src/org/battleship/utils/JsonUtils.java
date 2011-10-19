package org.battleship.utils;

import org.battleship.model.Notification;
import org.battleship.model.NotificationArray;

import com.google.gson.Gson;

public class JsonUtils {
	
	private static JsonUtils instance;
	private Notification notificationString;
	private NotificationArray notificationArray;
	private Gson gson;
	
	private JsonUtils(){
		gson = new Gson();
	}
	
	public static synchronized JsonUtils getInstance(){
		if ( instance == null){
			instance = new JsonUtils();
		}
		return instance;
	}
	
	public Object parseResponse( String response ,String type){
		if ( type.equalsIgnoreCase("array")){
			return notificationArray = gson.fromJson(response, NotificationArray.class);
		}else{
			return notificationString = gson.fromJson(response, Notification.class);
		}
	}
	
}
