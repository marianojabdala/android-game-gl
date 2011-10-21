package org.battleship.controller;

import java.util.ArrayList;
import java.util.Map;

import org.battleship.model.Constants;
import org.battleship.model.NotificationArray;
import org.battleship.model.Participant;
import org.battleship.utils.ClientRest;
import org.battleship.utils.JsonUtils;

import android.util.Log;

public class RetrieveParticipants {
	public static String CLASSTAG = RetrieveParticipants.class.getSimpleName();

	public String userState;

	public RetrieveParticipants(String userState) {
		this.userState = userState;
	}

	public ArrayList<Participant> getParticipants(String token) {
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
		Log.v(Constants.LOGTAG, RetrieveParticipants.CLASSTAG + " duration - " + duration);
		return results;
	}
}
