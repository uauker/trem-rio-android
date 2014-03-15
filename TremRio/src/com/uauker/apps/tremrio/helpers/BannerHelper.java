package com.uauker.apps.tremrio.helpers;

import java.util.HashSet;
import java.util.Set;

import android.view.View;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.uauker.apps.tremrio.R;

public class BannerHelper {

	public static AdView setUpAdmob(View v) {
		AdView adView = (AdView) v.findViewById(R.id.adView);

		AdRequest request = new AdRequest();
		request.addKeywords(getKeywords());

		if (!ConfigHelper.isProduction) {
			request.addTestDevice("47764E7BF45BF0D60DD4D7185D18B260");
			request.addTestDevice("4D176AF8E94542B892FDDF9140EB3559");
			request.addTestDevice("6AF90EF5508153744266469C93F16CFC");
			request.addTestDevice("8276B4F8BB9A68E508809427509B0663");
			request.addTestDevice("F3B3CE08E6A01144E4A834CE441C7ABC");
			request.addTestDevice(AdRequest.TEST_EMULATOR);
		}

		if (adView != null) {
			adView.loadAd(request);
		}

		return adView;
	}

	private static Set<String> getKeywords() {
		Set<String> keywords = new HashSet<String>();
		keywords.add("seguro carro");
		keywords.add("celulares motorola com 2 chips");
		keywords.add("venda de microondas");
		keywords.add("celulares 3g");
		keywords.add("microondas mabe");
		keywords.add("microondas portatil");
		keywords.add("marcas de geladeiras");
		keywords.add("geladeira bivolt");
		keywords.add("geladeira");
		keywords.add("refrigerador frost free");
		keywords.add("microondas");
		keywords.add("tv internet");
		keywords.add("oferta tv");
		keywords.add("comprar tablet");
		keywords.add("notebook");

		return keywords;
	}

}
