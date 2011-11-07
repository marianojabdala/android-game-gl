/**
 * 
 */
package org.battleship.activities;

import org.battleship.R;
import org.battleship.model.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author songoku
 *
 */
public class GameActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accept_decline_popup);
		Button closeButton = (Button) findViewById(R.id.close_btn);
		closeButton.setOnClickListener(close_button_click_listener);

		Button startWar = (Button) findViewById(R.id.go_to_war_btn);
		startWar.setOnClickListener(start_war_button_click_listener);
	}

	private OnClickListener close_button_click_listener = new OnClickListener() {
		public void onClick(View v) {
		Intent i = new Intent(getBaseContext(),ParticipantsActivity.class);
		startActivity(i);
		}
	};
	
	private OnClickListener start_war_button_click_listener = new OnClickListener() {
		public void onClick(View v) {
			//mPw.dismiss();
			Intent intent  = new Intent(Constants.INTENT_ACTION_START_WAR);
			startActivity(intent);
		}
	};
}
