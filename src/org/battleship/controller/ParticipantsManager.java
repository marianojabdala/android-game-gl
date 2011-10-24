/**
 * 
 */
package org.battleship.controller;

import java.util.ArrayList;
import java.util.Map;

import org.battleship.model.Constants;
import org.battleship.model.NotificationArray;
import org.battleship.model.Participant;
import org.battleship.utils.ClientRest;
import org.battleship.utils.JsonUtils;

import android.util.Log;

/**
 * @author songoku
 *
 */
public class ParticipantsManager {
	public static String CLASSTAG = ParticipantsManager.class.getSimpleName();

	private static ParticipantsManager mInstance;
	
	public static ParticipantsManager getInstance(){
		if ( mInstance == null ){
			mInstance = new ParticipantsManager();
		}
		return mInstance;
	}
	
	private ParticipantsManager() {
	}

	public ArrayList<Participant> getParticipants(String token,String userState) {
		long startTime = System.currentTimeMillis();
		ArrayList<Participant> results = new ArrayList<Participant>();

		try {

			String url = Constants.BASE_REST_URL + "/"+ Constants.GET_USER_BY_STATE+ "/"
					+ token + "/" + userState; 
			
			Map<String, String> response = ClientRest.execute( url ); 
			
			int responseCode = Integer.valueOf( response.get("responseCode") );
			if ( responseCode == 200 ){
				String data = response.get("response");
				NotificationArray notif = (NotificationArray)JsonUtils.getInstance().parseResponse( data,NotificationArray.class);
				String[] users = notif.data;
			
				for (String user : users) {
					Participant p = new Participant();
					p.username = user;
					results.add(p);	
				}
			}
		} catch (Exception e) {
			Log.e("RetrieveParticipants.CLASSTAG", e.getMessage(),e);
		}
		long duration = System.currentTimeMillis() - startTime;
		Log.v(Constants.LOGTAG, ParticipantsManager.CLASSTAG + " duration - " + duration);
		return results;
	}
}
