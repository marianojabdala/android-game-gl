/**
 * 
 */
package org.battleship.controller;

import java.util.Map;

import org.battleship.model.Constants;
import org.battleship.model.NotificationString;
import org.battleship.model.User;
import org.battleship.utils.ClientRest;
import org.battleship.utils.JsonUtils;

import android.util.Log;


/**
 * @author songoku
 *
 */
public class LoginManager {
	
	private static final String CLASSTAG = LoginManager.class
			.getSimpleName();
	
	private static LoginManager mInstance;
	
	public User mCurrentUser;
	public String mErrors;
	
	private LoginManager(){
		
	}
	
	public static LoginManager getInstance(){
		if ( mInstance == null ){
			mInstance = new LoginManager();
		}
		return mInstance;
	}
	
	public boolean login(String username, String password){
		mErrors = null;
		Log.v(CLASSTAG, "NickName: " + username );
		Log.v(CLASSTAG, "Password: " + password );
		
		mCurrentUser = new User();
		mCurrentUser.username = username;
		mCurrentUser.password = password;
		
		String url = Constants.BASE_REST_URL + "/"+ Constants.LOGIN+ "/"
				+ username + "/" + password; 
		NotificationString notif = null;
		
		try {
			Map<String, String> response = ClientRest.execute( url );
			int responseCode = Integer.valueOf( response.get("responseCode") );

			if ( responseCode == 200 ){
				String data = response.get("response");
				notif = (NotificationString)JsonUtils.getInstance().parseResponse( data ,NotificationString.class );	
					mCurrentUser.token = notif.data;
				if ( !notif.status.equalsIgnoreCase("SUCCESS") ){
					mErrors = notif.errorMsg;
				}
			}			
			return mErrors == null;
		
		}catch (Exception e) {
			Log.e(CLASSTAG, e.getMessage(),e);
		}
		
		return false;
	}
}
