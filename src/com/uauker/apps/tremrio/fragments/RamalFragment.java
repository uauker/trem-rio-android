package com.uauker.apps.tremrio.fragments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.java.com.github.kevinsawicki.http.HttpRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.adapters.RamalStationAdapter;
import com.uauker.apps.tremrio.helpers.BannerHelper;
import com.uauker.apps.tremrio.helpers.NetworkHelper;
import com.uauker.apps.tremrio.models.Station;

public class RamalFragment extends Fragment implements View.OnClickListener {

	ViewStub messageViewStub;

	ListView stationsListView;

	RamalAsyncTask task;

	AdView adView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		View view = (RelativeLayout) inflater.inflate(R.layout.ramal_fragment,
				container, false);

		this.stationsListView = (ListView) view
				.findViewById(R.id.ramal_stations);

		this.messageViewStub = (ViewStub) view
				.findViewById(R.id.ramal_stations_info);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (!NetworkHelper.isConnectingToInternet(getActivity())) {
			this.messageViewStub.setLayoutResource(R.layout.without_internet);
			this.messageViewStub.setVisibility(View.VISIBLE);

			Button buttonWithoutInternet = (Button) getActivity().findViewById(
					R.id.without_internet_button_try_again);

			buttonWithoutInternet.setOnClickListener(this);

			return;
		}

		setUpRamal(view);
	}

	private void setUpRamal(View v) {
		this.adView = BannerHelper.setUpAdmob(v);

		this.task = new RamalAsyncTask();
		this.task.execute();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (task == null) {
			task.cancel(true);
		}
	}

	class RamalAsyncTask extends AsyncTask<Void, Void, List<Station>> {

		private static final String HEROKU_RAMAL = "http://www.uauker.com/api/tremrio/v1/ramal";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			RamalFragment.this.messageViewStub
					.setLayoutResource(R.layout.loading);
			RamalFragment.this.messageViewStub.setVisibility(View.VISIBLE);

			RamalFragment.this.stationsListView.setVisibility(View.GONE);
		}

		@Override
		protected List<Station> doInBackground(Void... params) {
			List<Station> stations = new ArrayList<Station>();

			String json = HttpRequest.get(HEROKU_RAMAL).body();

			Gson gson = new Gson();
			JsonArray content = new JsonParser().parse(json).getAsJsonArray();

			Iterator<JsonElement> it = content.iterator();

			while (it.hasNext()) {
				JsonElement newsJson = it.next();
				Station station = gson.fromJson(newsJson, Station.class);
				stations.add(station);
			}

			return stations;
		}

		@Override
		protected void onPostExecute(List<Station> result) {
			super.onPostExecute(result);

			RamalFragment.this.messageViewStub.setVisibility(View.GONE);
			RamalFragment.this.stationsListView.setVisibility(View.VISIBLE);

			if (isCancelled()) {
				return;
			}

			RamalStationAdapter newsGeolocalizedAdapter = new RamalStationAdapter(
					getActivity(), R.layout.cell_ramal, result);

			RamalFragment.this.stationsListView.setDivider(null);
			RamalFragment.this.stationsListView.setDividerHeight(0);
			RamalFragment.this.stationsListView
					.setAdapter(newsGeolocalizedAdapter);
		}

	}

	@Override
	public void onClick(View v) {
		if (NetworkHelper.isConnectingToInternet(getActivity())) {
			setUpRamal(this.getView());
		}
	}
}
