package com.uauker.apps.tremrio.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.helpers.PackageHelper;
import com.uauker.apps.tremrio.models.App;

public class OtherAppsAdapter extends ArrayAdapter<App> {

	private List<App> datasource;
	private LayoutInflater inflater;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private Activity ownerActivity;

	public OtherAppsAdapter(Context context, int resource, List<App> objects) {
		super(context, resource, objects);

		this.inflater = LayoutInflater.from(context);
		this.datasource = objects;
		this.ownerActivity = (Activity) context;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		final App app = getOccurrence(position);

		int layout = R.layout.adapter_other_apps;

		rowView = inflater.inflate(layout, parent, false);

		TextView title = (TextView) rowView
				.findViewById(R.id.adapter_other_apps_name);
		title.setText(app.name);

		ImageView tweetImage = (ImageView) rowView
				.findViewById(R.id.adapter_other_apps_icon);
		imageLoader.displayImage(app.icon, tweetImage, options);

		rowView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!PackageHelper.isExists(ownerActivity, app.bundle)) {
					// Google Play
					final Uri uri = Uri.parse("market://details?id="
							+ app.bundle);
					final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW,
							uri);

					ownerActivity.startActivity(rateAppIntent);
				} else {
					// Open App
					PackageManager manager = ownerActivity.getPackageManager();
					Intent intent = manager
							.getLaunchIntentForPackage(app.bundle);
					intent.addCategory(Intent.CATEGORY_LAUNCHER);
					ownerActivity.startActivity(intent);
				}
			}
		});

		return rowView;
	}

	@Override
	public int getCount() {
		return this.datasource.size();
	}

	public App getOccurrence(int position) {
		return this.datasource.get(position);
	}

}
