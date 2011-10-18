package org.battleship.controller;

import java.util.ArrayList;

import org.battleship.model.Constants;
import org.battleship.model.Participant;
import org.battleship.utils.ClientRest;
import org.json.JSONArray;

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

			ClientRest clientRest = new ClientRest(
					Constants.BASE_REST_URL + "/"+ Constants.GET_USER_BY_STATE+ "/"
							+ token + "/" + userState);
			clientRest.execute();
			JSONArray jsonArray = new JSONArray(clientRest.response );
			for (int i = 0; i < jsonArray.length(); i++) {
				Participant p = new Participant();
				p.username = jsonArray.getString(i);
				results.add(p);
			}
		} catch (Exception e) {
			Log.e("RetrieveParticipants.CLASSTAG", e.getMessage(),e);
		}
		long duration = System.currentTimeMillis() - startTime;
		Log.v(Constants.LOGTAG, RetrieveParticipants.CLASSTAG + " duration - " + duration);
		return results;
	}
}
