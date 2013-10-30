package com.uauker.apps.tremrio.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.models.Telephone;

public class TelephoneAdapter extends ArrayAdapter<String> {

	private List<Telephone> datasource;
	private LayoutInflater inflater;

	public TelephoneAdapter(Context context, int textViewResourceId,
			List<Telephone> objects) {
		super(context, textViewResourceId);

		this.datasource = objects;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		rowView = inflater.inflate(R.layout.cell_telephone, parent, false);

		Telephone tel = this.get(position);

		TextView telephoneName = (TextView) rowView
				.findViewById(R.id.telephone_name);
		telephoneName.setText(tel.name);

		return rowView;
	}

	@Override
	public int getCount() {
		return this.datasource.size();
	}

	public Telephone get(int position) {
		return this.datasource.get(position);
	}
}
