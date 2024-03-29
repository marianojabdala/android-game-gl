/**
 * 
 */
package org.battleship.model;

import org.battleship.providers.NotesContentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author songoku
 *
 */
public class Note {
	public Note() {
			    }
			 
			    public static final class Notes implements BaseColumns {
			        private Notes() {
			        }
			 
			        public static final Uri CONTENT_URI = Uri.parse("content://"
			                + NotesContentProvider.AUTHORITY + "/notes");
			 
			        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.notes";
			 
			        public static final String NOTE_ID = "_id";
			 
			        public static final String TITLE = "title";
			 
			        public static final String TEXT = "text";
			    }
}