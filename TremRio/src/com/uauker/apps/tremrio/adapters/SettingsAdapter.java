package com.uauker.apps.tremrio.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.activities.OtherAppsActivity;

public class SettingsAdapter extends ArrayAdapter<String> {

	private List<String> datasource = new ArrayList<String>();
	private Activity ownerActivity;
	private LayoutInflater inflater;

	public SettingsAdapter(Context context) {
		super(context, 0);
	}
	
	public final static List<Integer> NAMES = Arrays.asList(new Integer[] {
			R.string.menu_config_others_apps,
			R.string.menu_config_share_app_for_android,
			R.string.menu_config_review, });

	@SuppressWarnings("rawtypes")
	public final static List<Class> CLASSES = Arrays.asList(new Class[] {
			OtherAppsActivity.class, null, null });

	public SettingsAdapter(Context context, ListView listView) {
		super(context, R.id.listview_settings);

		this.ownerActivity = (Activity) context;
		this.inflater = LayoutInflater.from(context);

		buildListNameInDataSource();
	}

	private void buildListNameInDataSource() {
		for (Integer nameInRFile : NAMES) {
			String name = ownerActivity.getResources().getString(nameInRFile);

			this.datasource.add(name);
		}
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		rowView = inflater.inflate(R.layout.adapter_settings, parent, false);

		String settingsName = getSettingsName(position);

		TextView settingsIndicator = (TextView) rowView
				.findViewById(R.id.adapter_settings_indicator);
		settingsIndicator.setBackgroundColor(ownerActivity.getResources()
				.getColor(R.color.home));

		TextView settingsTitle = (TextView) rowView
				.findViewById(R.id.adapter_settings_title);
		settingsTitle.setText(settingsName);

		rowView.setOnClickListener(new ItemOnClick(position));

		notifyDataSetChanged();

		return rowView;
	}

	@Override
	public int getCount() {
		return this.datasource.size();
	}

	public String getSettingsName(int position) {
		return this.datasource.get(position);
	}

	class ItemOnClick implements View.OnClickListener {

		int position;

		public ItemOnClick(final int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			if (position >= CLASSES.size()) {
				return;
			}

			if (CLASSES.get(position) == null) {
				this.startDynamicActivity(v);
				return;
			}

			Intent intent = new Intent(ownerActivity, CLASSES.get(position));
			ownerActivity.startActivity(intent);
		}

		private void startDynamicActivity(View v) {
			switch (NAMES.get(position)) {
			case R.string.menu_config_review:
				this.reviewThisApp();
				break;
			case R.string.menu_config_share_app_for_android:
				this.shareThisApp();
				break;
			}
		}

		private void shareThisApp() {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			intent.setType("text/plain");

			final String text = String.format(ownerActivity.getResources()
					.getString(R.string.email_app_text),
					"https://play.google.com/store/apps/details?id="
							+ ownerActivity.getPackageName());

			intent.putExtra(Intent.EXTRA_SUBJECT, ownerActivity.getResources()
					.getString(R.string.email_app_subject));
			intent.putExtra(Intent.EXTRA_TEXT, text);

			ownerActivity.startActivity(intent);
		}

		private void reviewThisApp() {
			final Uri uri = Uri.parse("market://details?id="
					+ ownerActivity.getPackageName());
			final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);

			ownerActivity.startActivity(rateAppIntent);
		}
	}

}