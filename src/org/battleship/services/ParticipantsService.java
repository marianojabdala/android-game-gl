/**
 * 
 */
package org.battleship.services;

import org.battleship.R;
import org.battleship.controller.LoginManager;
import org.battleship.providers.BattleShipProvider;
import org.battleship.tasks.GetParticipants;

import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * @author songoku
 *
 */
public class ParticipantsService extends Service {
	ListActivity list;
	
	/* (non-Javadoc)
	 * @see android.app.Service#onStart(android.content.Intent, int)
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		list = BattleShipProvider.mParticipantsList;
	}

	private NotificationManager mNM;
	 private ConditionVariable mCondition;
		
	    /**
	     * Class for clients to access.  Because we know this service always
	     * runs in the same process as its clients, we don't need to deal with
	     * IPC.
	     */
	    public class LocalBinder extends Binder {
	    	public ParticipantsService getService() {
	            return ParticipantsService.this;
	        }
	    }
	    private Runnable mTask = new Runnable() {
	    	public void run() {
	    		while(!mCondition.block(30 * 1000 )){
	    			String tok = LoginManager.getInstance().mCurrentUser.token;
	    			new GetParticipants( BattleShipProvider.mParticipantsList,false ).execute(tok);
	    		}
	        }
	    };

	    @Override
	    public void onCreate() {
	        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	        Thread notifyingThread = new Thread(null, mTask, "ParticipantsService");
	   	 	mCondition = new ConditionVariable(false);
        	notifyingThread.start();
	    }

	    @Override
	    public int onStartCommand(Intent intent, int flags, int startId) {
	        Log.i("LocalService", "Received start id " + startId + ": " + intent);
	        // We want this service to continue running until it is explicitly
	        // stopped, so return sticky.
	        return START_STICKY;
	    }

	    @Override
	    public void onDestroy() {
	        // Tell the user we stopped.
	        Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
	        // Cancel the persistent notification.
	        mNM.cancel(R.layout.incoming_message_info);
	        // Stop the thread from generating further notifications
	        mCondition.open();
	        ParticipantsService.this.stopSelf();
	    }

	    @Override
	    public IBinder onBind(Intent intent) {
	        return mBinder;
	    }

	    // This is the object that receives interactions from clients.  See
	    // RemoteService for a more complete example.
	    private final IBinder mBinder = new LocalBinder();

}
