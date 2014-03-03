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
		ViewHolder holder;

		final Telephone tel = this.get(position);
		final int layout = R.layout.cell_telephone;

		if (convertView != null && convertView.findViewById(layout) != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = inflater.inflate(layout, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		holder.setTelephone(tel);
		
		return convertView;
	}

	@Override
	public int getCount() {
		return this.datasource.size();
	}

	public Telephone get(int position) {
		return this.datasource.get(position);
	}

	static class ViewHolder {

		TextView telephoneName;

		public ViewHolder(View view) {
			telephoneName = (TextView) view.findViewById(R.id.telephone_name);
		}

		public void setTelephone(final Telephone tel) {
			telephoneName.setText(tel.name);
		}
	}
}
