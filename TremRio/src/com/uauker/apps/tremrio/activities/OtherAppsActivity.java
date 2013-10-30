package com.uauker.apps.tremrio.activities;

import java.util.ArrayList;
import java.util.Iterator;

import org.afinal.simplecache.ACache;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.adapters.OtherAppsAdapter;
import com.uauker.apps.tremrio.helpers.TryAgainHelper;
import com.uauker.apps.tremrio.helpers.TryAgainHelper.OnClickToTryAgain;
import com.uauker.apps.tremrio.models.App;

public class OtherAppsActivity extends ActionBarActivity implements
		OnClickToTryAgain {

	private static final int CACHE_TIME = 3 * 60 * 60;

	private static final String APPS_ANDROID = "http://www.uauker.com/api/apps/v1/android";

	ArrayList<App> apps = new ArrayList<App>();

	AsyncHttpClient client = new AsyncHttpClient();

	ACache cache;

	ViewStub loadingViewStub;

	ViewStub internetFailureViewStub;

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_apps);

		this.cache = ACache.get(this);

		final ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(0xfff06f2f));
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(true);

		this.listView = (ListView) findViewById(R.id.apps_listview);
		this.listView.setBackgroundColor(getResources().getColor(
				R.color.apps_background));
		this.listView.setDivider(null);

		this.loadingViewStub = (ViewStub) findViewById(R.id.apps_loading);
		this.loadingViewStub.setLayoutResource(R.layout.loading);

		this.internetFailureViewStub = (ViewStub) findViewById(R.id.apps_internet_failure);
		this.internetFailureViewStub
				.setLayoutResource(R.layout.internet_failure);
		TryAgainHelper tryAgainView = (TryAgainHelper) this.internetFailureViewStub
				.inflate();
		tryAgainView.setOnClickToTryAgain(this);

		this.tryAgain();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		this.client.cancelRequests(this, true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class AppsAsyncTask extends AsyncHttpResponseHandler {

		@Override
		public void onStart() {
			super.onStart();

			loadingViewStub.setVisibility(View.VISIBLE);
			internetFailureViewStub.setVisibility(View.GONE);
			listView.setVisibility(View.GONE);
		}

		@Override
		public void onFailure(Throwable arg0, String arg1) {
			super.onFailure(arg0, arg1);

			loadingViewStub.setVisibility(View.GONE);
			internetFailureViewStub.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}

		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);

			try {
				Gson gson = new Gson();
				JsonArray content = new JsonParser().parse(result)
						.getAsJsonObject().getAsJsonArray("apps");

				Iterator<JsonElement> it = content.iterator();

				apps.clear();

				while (it.hasNext()) {
					JsonElement appJson = it.next();
					App app = gson.fromJson(appJson, App.class);
					apps.add(app);
				}

				apps = new ArrayList<App>(App.actived(apps,
						getApplicationContext().getPackageName()));

				cache.put(APPS_ANDROID, apps, CACHE_TIME);

				setupListView();

				loadingViewStub.setVisibility(View.GONE);
				internetFailureViewStub.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
			} catch (Exception e) {
				onFailure(e, "");
			}
		}
	}

	public void setupListView() {
		OtherAppsAdapter otherAppsAdapter = new OtherAppsAdapter(
				OtherAppsActivity.this, R.layout.adapter_other_apps, apps);

		this.listView.setAdapter(otherAppsAdapter);
	}

	@SuppressWarnings("unchecked")
	public void loadApps() {
		this.apps = ((ArrayList<App>) cache.getAsObject(APPS_ANDROID));

		AppsAsyncTask appsAsyncTask = new AppsAsyncTask();

		if (this.apps == null || this.apps.size() == 0) {
			this.apps = new ArrayList<App>();

			this.client.get(APPS_ANDROID, appsAsyncTask);
		} else {
			this.setupListView();
		}
	}

	@Override
	public void tryAgain() {
		this.loadApps();
	}
}
