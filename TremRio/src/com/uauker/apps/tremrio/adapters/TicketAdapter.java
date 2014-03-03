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
		ViewHolder holder;

		final Ticket ticket = this.get(position);
		final int layout = R.layout.cell_ticket;

		if (convertView != null && convertView.findViewById(layout) != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = inflater.inflate(layout, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		holder.setTicket(ticket);

		return convertView;
	}

	@Override
	public int getCount() {
		return this.datasource.size();
	}

	public Ticket get(int position) {
		return this.datasource.get(position);
	}

	static class ViewHolder {

		TextView nameView;
		TextView priceView;

		public ViewHolder(View view) {
			nameView = (TextView) view.findViewById(R.id.ticket_name);
			priceView = (TextView) view.findViewById(R.id.ticket_price);
		}

		public void setTicket(final Ticket ticket) {
			nameView.setText(ticket.name);
			priceView.setText(ticket.price);
		}
	}
}
