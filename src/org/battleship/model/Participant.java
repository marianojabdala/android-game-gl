package org.battleship.model;

import java.io.Serializable;

import org.battleship.providers.BattleShipProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * As a general rule on Android applications we need to set all the fields as public,
 * this is for optimizations
 * 
 * */
public class Participant implements Serializable,BaseColumns{

	/**
	 * 
	 */
	private static final long serialVersionUID = -534406004123314787L;
	
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.btlship.participants";
	public static final Uri CONTENT_URI = Uri.parse("content://"
            + BattleShipProvider.AUTHORITY + "/participants");
	public static final String TOKEN = "token";
	public static final String USERNAME = "username";
	public static final String ID = "_id";
	public String username;
	
}
