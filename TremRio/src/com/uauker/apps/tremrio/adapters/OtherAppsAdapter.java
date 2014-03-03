package com.uauker.apps.tremrio.adapters;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	protected static Activity ownerActivity;

	public OtherAppsAdapter(Context context, int resource, List<App> objects) {
		super(context, resource, objects);

		this.inflater = LayoutInflater.from(context);
		this.datasource = objects;
		ownerActivity = (Activity) context;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final App app = getOccurrence(position);

		int layout = R.layout.adapter_other_apps;

		if (convertView != null && convertView.findViewById(layout) != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = inflater.inflate(layout, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		holder.setApp(app);

		return convertView;
	}

	@Override
	public int getCount() {
		return this.datasource.size();
	}

	public App getOccurrence(int position) {
		return this.datasource.get(position);
	}

	static class ViewHolder implements OnClickListener {

		TextView title;
		ImageView tweetImage;

		static ImageLoader imageLoader = ImageLoader.getInstance();
		static DisplayImageOptions options;

		App app;
		View view;

		public ViewHolder(View view) {
			this.view = view;

			title = (TextView) view.findViewById(R.id.adapter_other_apps_name);

			tweetImage = (ImageView) view
					.findViewById(R.id.adapter_other_apps_icon);
		}

		public void setApp(final App app) {
			this.app = app;

			title.setText(app.name);

			imageLoader.displayImage(app.icon, tweetImage, options);

			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (!PackageHelper.isExists(ownerActivity, app.bundle)) {
				this.goToGooglePlay();

				return;
			}

			this.goToApplication();
		}

		public void goToGooglePlay() {
			try {
				final Uri uri = Uri.parse("market://details?id=" + app.bundle);
				final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);

				ownerActivity.startActivity(rateAppIntent);
			} catch (Exception e) {
				String message = String.format(
						ownerActivity.getResources().getString(
								(R.string.other_apps_google_play_not_found)),
						this.app.name);

				AlertDialog alertDialog = new AlertDialog.Builder(ownerActivity)
						.create();
				alertDialog.setMessage(message);
				alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				alertDialog.show();
			}
		}

		public void goToApplication() {
			PackageManager manager = ownerActivity.getPackageManager();
			Intent intent = manager.getLaunchIntentForPackage(app.bundle);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ownerActivity.startActivity(intent);
		}
	}
}
