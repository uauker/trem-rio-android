package com.uauker.apps.tremrio.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.models.Station;

public class RamalStationAdapter extends ArrayAdapter<String> {

	private List<Station> datasource;
	private LayoutInflater inflater;

	public RamalStationAdapter(Context context, int textViewResourceId,
			List<Station> objects) {
		super(context, textViewResourceId);

		this.datasource = objects;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		int layout = R.layout.cell_ramal;

		final Station station = this.get(position);
		station.backgroundColor = station.backgroundColor.replace("#", "");

		if (convertView != null && convertView.findViewById(layout) != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = inflater.inflate(layout, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		holder.setStation(station);
		
		return convertView;
	}

	@Override
	public int getCount() {
		return this.datasource.size();
	}

	public Station get(int position) {
		return this.datasource.get(position);
	}

	static class ViewHolder {

		TextView stationView;
		TextView footer;
		LinearLayout border;

		TextView startIndicator;
		TextView startDirectionStatus;

		TextView endDirection;
		TextView endDirectionStatus;

		public ViewHolder(final View view) {
			stationView = (TextView) view.findViewById(R.id.ramal_station_name);
			footer = (TextView) view
					.findViewById(R.id.ramal_station_indicator_footer);
			border = (LinearLayout) view
					.findViewById(R.id.ramal_station_indicator_border);

			startIndicator = (TextView) view
					.findViewById(R.id.ramal_start_direction);
			startDirectionStatus = (TextView) view
					.findViewById(R.id.ramal_start_direction_status);

			endDirection = (TextView) view
					.findViewById(R.id.ramal_end_direction);
			endDirectionStatus = (TextView) view
					.findViewById(R.id.ramal_end_direction_status);
		}

		public void setStation(Station station) {
			stationView.setText(station.name);
			stationView.setBackgroundColor(Integer.parseInt(
					station.backgroundColor, 16) + 0xFF000000);

			footer.setBackgroundColor(Integer.parseInt(station.backgroundColor,
					16) + 0xFF000000);

			border.setBackgroundColor(Integer.parseInt(station.backgroundColor,
					16) + 0xFF000000);

			startIndicator.setText(station.startDirection);
			startDirectionStatus.setText(station.startDirectionStatus);

			endDirection.setText(station.endDirection);
			endDirectionStatus.setText(station.endDirectionStatus);
		}
	}
}
