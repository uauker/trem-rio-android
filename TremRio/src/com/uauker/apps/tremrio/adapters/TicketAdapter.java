package com.uauker.apps.tremrio.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.models.Ticket;

public class TicketAdapter extends ArrayAdapter<Ticket> {

	private List<Ticket> datasource;
	private LayoutInflater inflater;

	public TicketAdapter(Context context, int textViewResourceId,
			List<Ticket> objects) {
		super(context, textViewResourceId);

		this.datasource = objects;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		rowView = inflater.inflate(R.layout.cell_ticket, parent, false);

		Ticket ticket = this.get(position);
		
		TextView nameView = (TextView) rowView
				.findViewById(R.id.ticket_name);
		nameView.setText(ticket.name);
		
		TextView priceView = (TextView) rowView
				.findViewById(R.id.ticket_price);
		priceView.setText(ticket.price);
		
		return rowView;
	}
	
	@Override
	public int getCount() {
		return this.datasource.size();
	}

	public Ticket get(int position) {
		return this.datasource.get(position);
	}
}
