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
		String words = "biscoito, seguro de moto, seguro de carro, supermercados, sabonetes, desodorantes, sabão em pó, shampoo, celular, futebol, seguro viagem, universidade, profissionalizante, curso de ingles, curso de informatica, sky, net, refrigerante, tv, promocao, cartao de credito, microondas";

		Set<String> keywords = new HashSet<String>();

		for (String word : words.split(",")) {
			keywords.add(word);
		}

		return keywords;
	}

}
