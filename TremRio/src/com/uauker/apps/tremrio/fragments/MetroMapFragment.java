package com.uauker.apps.tremrio.fragments;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.ads.AdView;
import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.helpers.BannerHelper;

public class MetroMapFragment extends Fragment {
	ImageView mImageView;
	PhotoViewAttacher mAttacher;

	Activity ownerActivity;

	AdView adView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

		this.ownerActivity.setTitle(R.string.metro_maps);

		View view = (RelativeLayout) inflater.inflate(
				R.layout.train_map_fragment, container, false);

		try {
			mImageView = (ImageView) view.findViewById(R.id.iv_photo);

			Drawable bitmap = getResources().getDrawable(
					R.drawable.diagrama_de_rede_metrorio);
			mImageView.setImageDrawable(bitmap);

			mAttacher = new PhotoViewAttacher(mImageView);

			this.adView = BannerHelper.setUpAdmob(view);
		} catch (OutOfMemoryError e) {
			AlertDialog alertDialog = new AlertDialog.Builder(ownerActivity)
					.create();
			alertDialog.setMessage(ownerActivity.getResources()
					.getString(R.string.out_of_memory));
			alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			alertDialog.show();
		}

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		this.ownerActivity = activity;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.menu_config, menu);
	}

}
