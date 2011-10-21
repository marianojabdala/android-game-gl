package org.battleship.activities;


import org.battleship.R;
import org.battleship.controller.BattleShipAssetsManager;
import org.battleship.model.Constants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BattleShipActivity extends Activity {
	
	public static final String CLASSTAG = BattleShipActivity.class.getSimpleName();
	private Context context= this;
	AlertDialog alert;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        
        BattleShipAssetsManager.getInstance().setManager( getAssets() );
        
        Button play =  (Button) findViewById(R.id.ok_intro_btn);
        play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				  Intent intent = new Intent(Constants.INTENT_ACTION_LOGIN);
			        startActivity(intent);
			}
		});
        
        //Add an intro  button and it's listenter
        Button intro =  (Button) findViewById(R.id.intro_btn);
        intro.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		
        		AlertDialog.Builder builder = new AlertDialog.Builder( context );
        		builder.setMessage(R.string.intro)
        		       .setCancelable(false)
        		       .setPositiveButton(R.string.back_text, new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		        	   dialog.dismiss();
        		           }
        		       }) ;
        		 alert = builder.create();
        		 alert.show();
        	}
        });
        
        //Add a credits button and it's listenter
        Button credits =  (Button) findViewById(R.id.credits_btn);
        credits.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		AlertDialog.Builder builder = new AlertDialog.Builder( context );
        		builder.setMessage(R.string.credits_large)
        		       .setCancelable(false)
        		       .setPositiveButton(R.string.back_text, new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		        	   dialog.dismiss();
        		           }
        		       }) ;
        		 alert = builder.create();
        		 alert.show();
        	}
        });
        
        
    }
}