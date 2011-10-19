/**
 * 
 */
package org.battleship.activities;

import org.battleship.R;
import org.battleship.model.Constants;
import org.battleship.model.Notification;
import org.battleship.model.User;
import org.battleship.utils.ClientRest;
import org.battleship.utils.JsonUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author songoku
 *
 */
public class LoginActivity extends Activity {

	private static final String CLASSTAG = LoginActivity.class
			.getSimpleName();
	
	private EditText mNickName;
	private ClientRest mclientRest;
	private double mRandomPass;
	
	private User mcurrentUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Button play =  (Button) findViewById(R.id.play_btn);
	        play.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					mNickName = (EditText) findViewById(R.id.nickname_text);
					
					mRandomPass = Math.random();
					
					Log.v(CLASSTAG, "NickName: " + mNickName.getText().toString() );
					Log.v(CLASSTAG, "Password: " + mRandomPass );
					
					mcurrentUser = new User();
					mcurrentUser.username = mNickName.getText().toString();
					mcurrentUser.password = Double.toString(mRandomPass);
					
					mclientRest = new ClientRest(
							Constants.BASE_REST_URL + "/"+ Constants.LOGIN+ "/"
									+ mcurrentUser.username + "/" + mRandomPass);

					try {
						mclientRest.execute();
						Notification notif = (Notification)JsonUtils.getInstance().parseResponse( mclientRest.response,"string" );
						mcurrentUser.token = (String)notif.data;
					
					}catch (Exception e) {
						Log.e(LoginActivity.CLASSTAG, e.getMessage(),e);
					}
					
					Intent intent = new Intent(Constants.INTENT_ACTION_VIEW_PARTICIPANTS);
					intent.putExtra(Constants.USER, mcurrentUser);
					startActivity(intent);
				}
			});
	}
	
}
