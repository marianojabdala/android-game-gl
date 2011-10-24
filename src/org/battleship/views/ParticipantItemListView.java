package org.battleship.views;

import org.battleship.R;
import org.battleship.controller.BattleShipAssetsManager;
import org.battleship.model.Constants;
import org.battleship.model.Participant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author zeta
 *
 */
public class ParticipantItemListView extends LinearLayout {
	private TextView userNameTV;
	private ImageView userIcon;
	private Button declareWarButton;
	
	public ParticipantItemListView(Context context, Participant participant) {

		super(context);
		setOrientation(LinearLayout.HORIZONTAL);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 3, 5, 0);
		params.height = 50;

		this.userIcon = new ImageView(context);
		this.userIcon.setImageBitmap(BattleShipAssetsManager.getInstance().getBiMapImage(Constants.USER_IMAGE));
    	this.addView(userIcon);
		
		this.userNameTV = new TextView(context);
		this.userNameTV.setText(participant.username);
		this.userNameTV.setTextSize(16f);
		this.userNameTV.setTextColor(Color.GREEN);
		this.addView(this.userNameTV, params);
		
		this.declareWarButton = new Button(context);
		this.declareWarButton.setText(R.string.declare_war);
		this.declareWarButton.setWidth(150);
		this.declareWarButton.setHeight(50);
		this.declareWarButton.setOnClickListener(declare_war_button_click_listener);
		this.addView(declareWarButton);
		this.setClickable(true); 
		this.setFocusable(true); 
	}
	
	private OnClickListener declare_war_button_click_listener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent  = new Intent(Constants.INTENT_ACTION_START_WAR);
			v.getContext().startActivity(intent);
		}
	};
	
	
}
