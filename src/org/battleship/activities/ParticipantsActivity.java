package org.battleship.activities;

import java.io.Serializable;

import org.battleship.R;
import org.battleship.controller.LoginManager;
import org.battleship.providers.BattleShipProvider;
import org.battleship.services.ParticipantsService;
import org.battleship.services.ParticipantsSyncAdapterService;
import org.battleship.tasks.GetParticipants;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author zeta
 * 
 */
public class ParticipantsActivity extends ListActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8415406598794867124L;

	public static final String CLASSTAG = ParticipantsActivity.class.getName();

	public static final int MENU_GET_PARTICIPANTS = 1;

	private TextView mEmpty;
	public ProgressDialog mProgressDialog;
	
	private GetParticipants getPart;//private List<Participant> mParticipants;
	//private ParticipantsAdapter mParticipantsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mProgressDialog = new ProgressDialog(this);
		setContentView(R.layout.participants);
		this.mEmpty = (TextView) findViewById(R.id.emptyParticipants);

		TextView currentUserLogged = (TextView) findViewById(R.id.userName_loged_text);
		String message = "Hello, " + LoginManager.getInstance().mCurrentUser.username
				+ ", this is the list of your oponents: ";
		currentUserLogged.setText(message);

		ListView listView = getListView();
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setEmptyView(this.mEmpty);
		
		Intent service = new Intent( this ,ParticipantsSyncAdapterService.class);
		//bindService(service,mConnection, BIND_AUTO_CREATE) ;
		startService( service );
		
		Intent getParticipantsService = new Intent(this,ParticipantsService.class);
		startService( getParticipantsService );
		BattleShipProvider.mParticipantsList = this;
		
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
		//initiatePopupWindow();
	}

	public void loadParticipants() {
		String tok = LoginManager.getInstance().mCurrentUser.token;
		getPart = new GetParticipants( this,true );
		getPart.execute(tok);
	}
}
