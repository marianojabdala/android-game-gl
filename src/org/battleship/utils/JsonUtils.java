package org.battleship.utils;

import org.battleship.model.BaseNotification;

import com.google.gson.Gson;

public class JsonUtils {
	
	private static JsonUtils instance;
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
	
	public  BaseNotification parseResponse( String response, Class<? extends BaseNotification> clazz){
			return  gson.fromJson(response, clazz);
		}
}
