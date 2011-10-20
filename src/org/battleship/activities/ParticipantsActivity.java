package org.battleship.activities;

import java.util.List;

import org.battleship.R;
import org.battleship.controller.ParticipantsAdapter;
import org.battleship.controller.RetrieveParticipants;
import org.battleship.model.Constants;
import org.battleship.model.Participant;
import org.battleship.model.User;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @author zeta
 * 
 */
public class ParticipantsActivity extends ListActivity {

	public static final String CLASSTAG = ParticipantsActivity.class.getName();

	public static final int MENU_GET_PARTICIPANTS = 1;

	private PopupWindow mPw;
	private TextView mEmpty;
	public ProgressDialog mProgressDialog;
	private List<Participant> mParticipants;
	private ParticipantsAdapter mParticipantsAdapter;
	private User mCurrentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mProgressDialog = new ProgressDialog(this);
		setContentView(R.layout.participants);
		this.mEmpty = (TextView) findViewById(R.id.emptyParticipants);
		// This code is used to show the current user on top of the list
		mCurrentUser = (savedInstanceState == null) ? null
				: (User) savedInstanceState.getSerializable(Constants.USER);
		if (mCurrentUser == null) {
			Bundle extras = getIntent().getExtras();
			mCurrentUser = extras != null ? (User) extras
					.getSerializable(Constants.USER) : null;
		}

		TextView currentUserLogged = (TextView) findViewById(R.id.userName_loged_text);
		String message = "Hello, " + mCurrentUser.username
				+ ", this is the list of your oponents: ";
		currentUserLogged.setText(message);

		ListView listView = getListView();
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setEmptyView(this.mEmpty);
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadParticipants();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(Menu.NONE, ParticipantsActivity.MENU_GET_PARTICIPANTS,
				Menu.NONE, R.string.getParticipants).setIcon(
				android.R.drawable.ic_menu_more);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		switch (item.getItemId()) {
		case MENU_GET_PARTICIPANTS:
			loadParticipants();
			break;
		default:
			break;
		}
		return true;

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		initiatePopupWindow();
	}

	public void loadParticipants() {

		String[] tok = mCurrentUser.token.split("\\n");
		new GetParticipants().execute(tok[0]);
	}

	private void initiatePopupWindow() {
		try {
			// We need to get the instance of the LayoutInflater, use the
			// context of this activity
			LayoutInflater inflater = (LayoutInflater) ParticipantsActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// Inflate the view from a predefined XML layout
			View layout = inflater.inflate(R.layout.accept_decline_popup,
					(ViewGroup) findViewById(R.id.accept_decline_popup));
			// create a 300px width and 470px height PopupWindow
			mPw = new PopupWindow(layout, 300, 470, true);
			// display the popup in the center
			mPw.showAtLocation(layout, Gravity.CENTER, 0, 0);

			Button closeButton = (Button) layout.findViewById(R.id.close_btn);
			closeButton.setOnClickListener(close_button_click_listener);

			Button startWar = (Button) layout.findViewById(R.id.go_to_war_btn);
			startWar.setOnClickListener(start_war_button_click_listener);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private OnClickListener close_button_click_listener = new OnClickListener() {
		public void onClick(View v) {
			mPw.dismiss();
		}
	};
	
	private OnClickListener start_war_button_click_listener = new OnClickListener() {
		public void onClick(View v) {
			//mPw.dismiss();
			Intent intent  = new Intent(Constants.INTENT_ACTION_START_WAR);
			startActivity(intent);
		}
	};

	private class GetParticipants extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			mProgressDialog.setTitle("Working");
			mProgressDialog.setMessage("Retrieving participants");
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			RetrieveParticipants rp = new RetrieveParticipants(Constants.ALL);
			mParticipants = rp.getParticipants(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mProgressDialog.dismiss();
			if (mParticipants == null || mParticipants.isEmpty()) {
				mEmpty.setText("No participants");
			} else {
				mParticipantsAdapter = new ParticipantsAdapter(
						ParticipantsActivity.this, mParticipants);
				setListAdapter(mParticipantsAdapter);
			}
		}

	}

}
