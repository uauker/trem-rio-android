package com.uauker.apps.tremrio.fragments;

import java.util.ArrayList;

import org.afinal.simplecache.ACache;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.adapters.RamalStationAdapter;
import com.uauker.apps.tremrio.helpers.BannerHelper;
import com.uauker.apps.tremrio.helpers.TryAgainHelper;
import com.uauker.apps.tremrio.helpers.TryAgainHelper.OnClickToTryAgain;
import com.uauker.apps.tremrio.models.Station;
import com.uauker.apps.tremrio.models.StationHtml;

public class RamalFragment extends Fragment implements OnClickToTryAgain {

	public static final String SUPERVIA_RAMAL_URL = "http://www.supervia.com.br/ping_obj3ipRamalUpdate.php";

	public static final int CACHE_TIME = 5 * 60;

	ViewStub messageViewStub;

	ListView stationsListView;

	AdView adView;

	ArrayList<Station> traffics = new ArrayList<Station>();

	AsyncHttpClient client = new AsyncHttpClient();

	ViewStub loadingViewStub;
	ViewStub internetFailureViewStub;
	View emptyView;

	ACache cache;

	private Activity ownerActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		setHasOptionsMenu(true);

		this.ownerActivity.setTitle(R.string.app_name);

		this.cache = ACache.get(this.ownerActivity);

		View view = (RelativeLayout) inflater.inflate(R.layout.ramal_fragment,
				container, false);

		this.stationsListView = (ListView) view
				.findViewById(R.id.ramal_stations);

		this.messageViewStub = (ViewStub) view.findViewById(R.id.ramal_info);

		this.loadingViewStub = (ViewStub) view.findViewById(R.id.ramal_loading);

		this.loadingViewStub.setLayoutResource(R.layout.loading);

		this.internetFailureViewStub = (ViewStub) view
				.findViewById(R.id.ramal_internet_failure);
		this.internetFailureViewStub
				.setLayoutResource(R.layout.internet_failure);
		TryAgainHelper tryAgainView = (TryAgainHelper) this.internetFailureViewStub
				.inflate();
		tryAgainView.setOnClickToTryAgain(this);

		this.loadStations();

		this.adView = BannerHelper.setUpAdmob(view);

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

	@Override
	public void tryAgain() {
		this.loadStations();
	}

	private void loadStations() {
		this.traffics = ((ArrayList<Station>) cache
				.getAsObject(SUPERVIA_RAMAL_URL));

		InternUrlAsyncTask internUrlAsyncTask = new InternUrlAsyncTask();

		if (this.traffics == null || this.traffics.size() == 0) {
			traffics = new ArrayList<Station>();

			client.addHeader("X-Requested-With", "XMLHttpRequest");
			client.addHeader("Referer", "http://supervia.com.br/");

			client.get(SUPERVIA_RAMAL_URL, internUrlAsyncTask);
		} else {
			setupListView();
		}
	}

	private void setupListView() {
		RamalStationAdapter adapter = new RamalStationAdapter(
				RamalFragment.this.ownerActivity, R.layout.cell_ramal, traffics);

		stationsListView.setAdapter(adapter);
	}

	class InternUrlAsyncTask extends AsyncHttpResponseHandler {
		@Override
		public void onStart() {
			super.onStart();

			RamalFragment.this.loadingViewStub.setVisibility(View.VISIBLE);
			RamalFragment.this.internetFailureViewStub.setVisibility(View.GONE);
			RamalFragment.this.stationsListView.setVisibility(View.GONE);
		}

		@Override
		public void onFailure(Throwable arg0, String arg1) {
			super.onFailure(arg0, arg1);

			RamalFragment.this.loadingViewStub.setVisibility(View.GONE);
			RamalFragment.this.internetFailureViewStub
					.setVisibility(View.VISIBLE);
			RamalFragment.this.stationsListView.setVisibility(View.GONE);
		}

		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);

			try {
				Gson gson = new Gson();
				JsonObject content = new JsonParser().parse(result)
						.getAsJsonObject();
				StationHtml stationHTML = gson.fromJson(content, StationHtml.class);

				RamalFragment.this.traffics.clear();

				RamalFragment.this.traffics = (ArrayList<Station>) Station
						.parse(stationHTML.txt);

				RamalFragment.this.setupListView();

				RamalFragment.this.loadingViewStub.setVisibility(View.GONE);
				RamalFragment.this.internetFailureViewStub
						.setVisibility(View.GONE);
				RamalFragment.this.stationsListView.setVisibility(View.VISIBLE);
			} catch (Exception e) {
				this.onFailure(e, "");
			}
		}
	}
}
