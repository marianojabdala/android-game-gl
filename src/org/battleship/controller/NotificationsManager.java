/**
 * 
 */
package org.battleship.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.battleship.model.Constants;
import org.battleship.model.Notification;
import org.battleship.model.NotificationString;
import org.battleship.model.WarNotification;
import org.battleship.utils.ClientRest;
import org.battleship.utils.JsonUtils;

import android.util.Log;

/**
 * @author songoku
 *
 */
public class NotificationsManager {
	private static final String CLASSTAG = NotificationsManager.class
			.getSimpleName();
	
	private static NotificationsManager mInstance;
	
	
	private NotificationsManager(){
		
	}
	
	public static NotificationsManager getInstance(){
		if ( mInstance == null ){
			mInstance = new NotificationsManager();
		}
		return mInstance;
	}
	/**
	 * getNotification
	 * URL:"http://glbattleship.appspot.com/rest/getNotification/tokenccceffe6-85a0-433e-a6b2-cac47a88d246
	 * @return
	 */
	public List<String> retrieveWarNotifications( String token )
	{
		List<String> notif = null;
		String url = Constants.BASE_REST_URL + "/"+ Constants.GET_NOTIFICATION+ "/"
				+ token; 
		WarNotification notifications;
		
		try {
			Map<String, String> response = ClientRest.execute( url );
			int responseCode = Integer.valueOf( response.get("responseCode") );

			if ( responseCode == 200 ){
				notif = new ArrayList<String>();
				String data = response.get("response");
				notifications = (WarNotification)JsonUtils.getInstance().parseResponse( data ,WarNotification.class );
				if (notifications.data != null ){
					for (Notification obj : notifications.data) {
						if (obj.notificationType.equalsIgnoreCase(Constants.DECLARED_WAR) ){
							notif.add( obj.senderNick );
						}
					}
				}
			}			
		
		}catch (Exception e) {
			Log.e(CLASSTAG, e.getMessage(),e);
		}
		
		return notif;
	}

	/**
	 * URL:"http://glbattleship.appspot.com/rest/declareWar/tokenab0c5cc8-3088-4059-8579-269c489ec038/britney)
	 * @param token
	 * @param username
	 */
	public void declareWar(String token, String username) {
		String url = Constants.BASE_REST_URL + "/"+ Constants.DECLARE_WAR+ "/"
				+ token + "/" + username; 
		NotificationString notif = null;
		
		try {
			Map<String, String> response = ClientRest.execute( url );
			int responseCode = Integer.valueOf( response.get("responseCode") );

			if ( responseCode == 200 ){
				String data = response.get("response");
				notif = (NotificationString)JsonUtils.getInstance().parseResponse( data ,NotificationString.class );	
			}			
		
		}catch (Exception e) {
			Log.e(CLASSTAG, e.getMessage(),e);
		}
		
	}
}
