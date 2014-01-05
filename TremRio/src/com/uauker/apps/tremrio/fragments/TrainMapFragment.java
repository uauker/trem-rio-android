package com.uauker.apps.tremrio.fragments;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.ads.AdView;
import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.helpers.BannerHelper;

public class TrainMapFragment extends Fragment {

	ImageView mImageView;
	PhotoViewAttacher mAttacher;

	Activity ownerActivity;

	AdView adView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.ownerActivity.setTitle(R.string.train_maps);
		
		View view = (RelativeLayout) inflater.inflate(
				R.layout.train_map_fragment, container, false);

		// Any implementation of ImageView can be used!
		mImageView = (ImageView) view.findViewById(R.id.iv_photo);

		// Set the Drawable displayed
		Drawable bitmap = getResources().getDrawable(R.drawable.diagrama_de_rede);
		mImageView.setImageDrawable(bitmap);

		// Attach a PhotoViewAttacher, which takes care of all of the zooming
		// functionality.
		mAttacher = new PhotoViewAttacher(mImageView);
		
		this.adView = BannerHelper.setUpAdmob(view);
		
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		this.ownerActivity = activity;
	}

}
