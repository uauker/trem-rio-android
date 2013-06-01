package com.uauker.apps.tremrio.helpers;

import android.view.View;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.uauker.apps.tremrio.R;

public class BannerHelper {

	public static AdView setUpAdmob(View v) {
		AdView adView = (AdView)v.findViewById(R.id.adView);
		
		AdRequest request = new AdRequest();
		request.addTestDevice("47764E7BF45BF0D60DD4D7185D18B260");
		
		adView.loadAd(request);
		
		return adView;
	}
	
}
