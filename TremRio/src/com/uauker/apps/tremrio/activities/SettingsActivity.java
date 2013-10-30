package com.uauker.apps.tremrio.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.adapters.SettingsAdapter;

public class SettingsActivity extends ActionBarActivity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		final ActionBar ab = getSupportActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(0xfff06f2f));
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(true);

		this.listView = (ListView) findViewById(R.id.listview_settings);

		SettingsAdapter adapter = new SettingsAdapter(this, this.listView);
		this.listView.setAdapter(adapter);
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

}