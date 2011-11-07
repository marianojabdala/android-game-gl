/**
 * 
 */
package org.battleship.tasks;

import java.util.List;

import org.battleship.R;
import org.battleship.activities.GameActivity;
import org.battleship.controller.LoginManager;
import org.battleship.controller.NotificationsManager;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author songoku
 *
 */
public class GetNotifications extends AsyncTask<String, Void, Void> {

	private Context mContext;
	private List<String> warNotifications;
	
	/**
	 * @param mContext
	 */
	public GetNotifications(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public GetNotifications(){
		
	}
	
	@Override
	protected void onPreExecute() {
	}

	@Override
	protected Void doInBackground(String... params) {
		String token = LoginManager.getInstance().mCurrentUser.token;
		warNotifications = NotificationsManager.getInstance().retrieveWarNotifications(token);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if ( warNotifications != null && !warNotifications.isEmpty() ){
			showToast();
			showNotification();
		}
	}
	 /**
     * The toast pops up a quick message to the user showing what could be
     * the text of an incoming message.  It uses a custom view to do so.
     */
    protected void showToast() {
        // create the view
        View view = inflateView(R.layout.incoming_message_panel);

        // set the text in the view
        TextView tv = (TextView)view.findViewById(R.id.message);
        tv.setText("Someone declared you the war");

        // show the toast
        Toast toast = new Toast(mContext);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    
    private View inflateView(int resource) {
    	
        LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return vi.inflate(resource, null);
    }
    
    /**
     * The notification is the icon and associated expanded entry in the
     * status bar.
     */
    protected void showNotification() {
        // look up the notification manager service
        NotificationManager nm = (NotificationManager)mContext.getSystemService(Activity.NOTIFICATION_SERVICE);
        if ( warNotifications==null) return;
        for (String participant : warNotifications) {
	        // The details of our fake message
	        CharSequence from = participant;
	        CharSequence message = "I'm avalilable to play";

	        // The PendingIntent to launch our activity if the user selects this notification
	        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
	                new Intent(mContext, GameActivity.class), 0);

	        // The ticker text, this uses a formatted string so our message could be localized
	        String tickerText =mContext.getString(R.string.imcoming_message_ticker_text, message);

	        // construct the Notification object.
	        Notification notif = new Notification(R.drawable.stat_sample, tickerText,
	                System.currentTimeMillis());

	        // Set the info for the views that show in the notification panel.
	        notif.setLatestEventInfo(mContext, from, message, contentIntent);
	        
	        nm.notify(participant.hashCode(), notif);

		}
    }

}
