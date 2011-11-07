/**
 * 
 */
package org.battleship.tasks;

import java.util.List;

import org.battleship.controller.ParticipantsAdapter;
import org.battleship.controller.ParticipantsManager;
import org.battleship.model.Constants;
import org.battleship.model.Participant;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

/**
 * @author songoku
 *
 */
public class GetParticipants extends AsyncTask<String,Void,Void> {

	private TextView mEmpty;
	private List<Participant> mParticipants;
	private ParticipantsAdapter mParticipantsAdapter;
	private ProgressDialog mProgressDialog;
	private boolean mShowDialog = false;
	private ListActivity mListActivity= null;
	
	public GetParticipants(){
		
	}
	
	/**
	 * @param mEmpty
	 * @param mParticipants
	 * @param mParticipantsAdapter
	 */
	public GetParticipants(	ListActivity mListActivity,boolean mShowDialog) {
		super();
		this.mShowDialog = mShowDialog;
		this.mListActivity = mListActivity;
	}

	
	@Override
	protected void onPreExecute() {
		if ( mShowDialog){
			mProgressDialog = new ProgressDialog( this.mListActivity );
			mProgressDialog.setTitle("Working");
			mProgressDialog.setMessage("Retrieving participants");
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}
	}

	@Override
	protected Void doInBackground(String... params) {
		mParticipants = ParticipantsManager.getInstance().getParticipants(params[0], Constants.AVAILABLE ); 
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if ( mShowDialog ){
			mProgressDialog.dismiss();
		}

		if (mParticipants == null || mParticipants.isEmpty()) {
			mEmpty = new TextView(this.mListActivity.getBaseContext());
			mEmpty.setText("No participants");
		} else {
			mParticipantsAdapter = new ParticipantsAdapter(
					mListActivity, mParticipants);
			mListActivity.setListAdapter(mParticipantsAdapter);
		}
	}

}
