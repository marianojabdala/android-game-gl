/**
 * 
 */
package org.battleship.activities;

import org.battleship.R;
import org.battleship.controller.LoginManager;
import org.battleship.model.Constants;

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
	private String mRandomPass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Button play =  (Button) findViewById(R.id.play_btn);
	        play.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					mNickName = (EditText) findViewById(R.id.nickname_text);
					mRandomPass = Math.random() + "";
					EditText mErrorMsgText;
					if ( LoginManager.getInstance().login(mNickName.getText().toString(), mRandomPass) ){
						mErrorMsgText = (EditText) findViewById(R.id.error_message_txt);
						mErrorMsgText.setVisibility(EditText.GONE);
						Intent intent = new Intent(Constants.INTENT_ACTION_VIEW_PARTICIPANTS);
						startActivity(intent);
					}else{
						Log.e(CLASSTAG, LoginManager.getInstance().mErrors);
						mErrorMsgText = (EditText) findViewById(R.id.error_message_txt);
						mErrorMsgText.setText( LoginManager.getInstance().mErrors );
						mErrorMsgText.setVisibility(EditText.VISIBLE);
					}
					
				}
			});
	}
	
}
