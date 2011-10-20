package org.battleship.views;

import org.battleship.R;
import org.battleship.model.Participant;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
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
	
	public ParticipantItemListView(Context context, Participant participant) {

		super(context);
		setOrientation(LinearLayout.HORIZONTAL);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 3, 5, 0);
		params.height = 50;

		this.userIcon = new ImageView(context);
    	this.userIcon.setImageDrawable(getResources().getDrawable(R.drawable.user));
		this.addView(userIcon);
		
		this.userNameTV = new TextView(context);
		this.userNameTV.setText(participant.username);
		this.userNameTV.setTextSize(16f);
		this.userNameTV.setTextColor(Color.GREEN);
		this.addView(this.userNameTV, params);
		
	}
}
