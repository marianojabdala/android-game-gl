package org.battleship.controller;

import java.util.List;

import org.battleship.model.Participant;
import org.battleship.views.ParticipantItemListView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ParticipantsAdapter extends BaseAdapter {

	private static final String CLASSTAG = ParticipantsAdapter.class
			.getSimpleName();
	private final Context context;
	private final List<Participant> participants;

	public ParticipantsAdapter(Context context, List<Participant> participants) {
		this.context = context;
		this.participants = participants;
		Log.v(ParticipantsAdapter.CLASSTAG, " participants size - "
				+ this.participants.size() );
	}

	public int getCount() {
		return this.participants.size();
	}

	public Object getItem(int position) {
		return this.participants.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Participant participant = this.participants.get(position);
		return new ParticipantItemListView(this.context, participant);
	}

}
