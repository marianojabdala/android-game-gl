/**
 * 
 */
package org.battleship.providers;

import java.util.HashMap;

import org.battleship.model.Participant;

import android.app.ListActivity;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * @author songoku
 *
 */
public class BattleShipProvider extends ContentProvider {
	
	private static final String TAG="BattleShipProvider";

	public static final Uri CONTENT_URI = Uri.parse("content://"
            + BattleShipProvider.AUTHORITY );
	
	private static final String DATABASE_NAME = "battleship.db";
	 
    private static final int DATABASE_VERSION = 1;
 
    private static final String PARTICIPANTS_TABLE_NAME = "participants";
 
    public static final String AUTHORITY = "org.battleship.providers.battleshipprovider";
 
    private static final UriMatcher sUriMatcher;
 
    private static final int PARTICIPANTS = 1;
 
    private static HashMap<String, String> participantsProjectionMap;
    
    public static ListActivity mParticipantsList;
    
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, PARTICIPANTS_TABLE_NAME, PARTICIPANTS);
 
        participantsProjectionMap = new HashMap<String, String>();
        participantsProjectionMap.put(Participant.ID, Participant.ID);
        participantsProjectionMap.put(Participant.USERNAME, Participant.USERNAME);
        participantsProjectionMap.put(Participant.TOKEN, Participant.TOKEN);
 
    }
    private static class DatabaseHelper extends SQLiteOpenHelper {
		 
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
 
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + PARTICIPANTS_TABLE_NAME + " (" + Participant.ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + Participant.USERNAME + " VARCHAR(255)," + Participant.TOKEN
                    + " VARCHAR(255)" + ");");
        }
 
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + PARTICIPANTS_TABLE_NAME);
            onCreate(db);
        }
    }
 
    private DatabaseHelper dbHelper;
    
	/* (non-Javadoc)
	 * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		  SQLiteDatabase db = dbHelper.getWritableDatabase();
	        int count;
	        switch (sUriMatcher.match(uri)) {
	            case PARTICIPANTS:
	                count = db.delete(PARTICIPANTS_TABLE_NAME, where, whereArgs);
	                break;
	 
	            default:
	                throw new IllegalArgumentException("Unknown URI " + uri);
	        }
	 
	        getContext().getContentResolver().notifyChange(uri, null);
	        return count;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		 switch (sUriMatcher.match(uri)) {
         case PARTICIPANTS:
             return Participant.CONTENT_TYPE;

         default:
         throw new IllegalArgumentException("Unknown URI " + uri);
     }
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		 if (sUriMatcher.match(uri) != PARTICIPANTS) { throw new IllegalArgumentException("Unknown URI " + uri); }
		 
	        ContentValues values;
	        if (initialValues != null) {
	            values = new ContentValues(initialValues);
	        } else {
	            values = new ContentValues();
	        }
	 
	        SQLiteDatabase db = dbHelper.getWritableDatabase();
     long rowId = db.insert(PARTICIPANTS_TABLE_NAME, Participant.USERNAME, values);
	        if (rowId > 0) {
	            Uri noteUri = ContentUris.withAppendedId(Participant.CONTENT_URI, rowId);
	            getContext().getContentResolver().notifyChange(noteUri, null);
	            return noteUri;
	        }
	 
	        throw new SQLException("Failed to insert row into " + uri);
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		 dbHelper = new DatabaseHelper(getContext());
		return true;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		 
	        switch (sUriMatcher.match(uri)) {
	            case PARTICIPANTS:
	                qb.setTables(PARTICIPANTS_TABLE_NAME);
	                qb.setProjectionMap(participantsProjectionMap);
	                break;
	 
	            default:
	                throw new IllegalArgumentException("Unknown URI " + uri);
	        }
	 
	        SQLiteDatabase db = dbHelper.getReadableDatabase();
	        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
	 
	        c.setNotificationUri(getContext().getContentResolver(), uri);
	        return c;
	}

	/* (non-Javadoc)
	 * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		  SQLiteDatabase db = dbHelper.getWritableDatabase();
	        int count;
	        switch (sUriMatcher.match(uri)) {
	            case PARTICIPANTS:
	                count = db.update(PARTICIPANTS_TABLE_NAME, values, where, whereArgs);
	                break;
	 
	            default:
	                throw new IllegalArgumentException("Unknown URI " + uri);
	        }
	 
	        getContext().getContentResolver().notifyChange(uri, null);
	        return count;
	}

}
