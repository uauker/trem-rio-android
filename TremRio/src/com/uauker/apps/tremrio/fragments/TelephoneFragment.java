package com.uauker.apps.tremrio.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.adapters.TelephoneAdapter;
import com.uauker.apps.tremrio.helpers.BannerHelper;
import com.uauker.apps.tremrio.models.Telephone;

public class TelephoneFragment extends Fragment implements OnItemClickListener {

	ListView telephonesListView;

	List<Telephone> telephones;

	AdView adView;
	
	private Activity ownerActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		setHasOptionsMenu(true);

		this.ownerActivity.setTitle(R.string.useful_telephones);
		
		View v = (RelativeLayout) inflater.inflate(R.layout.telephone_fragment,
				container, false);

		this.telephonesListView = (ListView) v.findViewById(R.id.telephones);

		this.adView = BannerHelper.setUpAdmob(v);

		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		this.getTelephones();

		TelephoneAdapter telephoneAdapter = new TelephoneAdapter(getActivity(),
				R.layout.cell_telephone, this.telephones);

		this.telephonesListView.setAdapter(telephoneAdapter);
		this.telephonesListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		final Telephone tel = this.telephones.get(arg2);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		String title = String.format(
				getResources().getString(R.string.call_to), tel.name);
		builder.setTitle(title);

		builder.setSingleChoiceItems(tel.toCharSequenceList(), -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int index) {
						Intent intent = new Intent(Intent.ACTION_CALL);

						intent.setData(Uri.parse("tel:" + tel.tels.get(index)));
						getActivity().startActivity(intent);
					}
				});

		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		this.ownerActivity = activity;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    super.onCreateOptionsMenu(menu,inflater);
	    
	    inflater.inflate(R.menu.menu_config, menu);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();

		if (adView != null) {
			adView.destroy();
		}
	}

	public void getTelephones() {
		List<Telephone> telephones = new ArrayList<Telephone>();

		String json = "{}";
		try {
			json = readTelephones();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new Gson();
		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
		JsonArray content = jsonObject.getAsJsonArray("util");

		Iterator<JsonElement> it = content.iterator();

		while (it.hasNext()) {
			JsonElement newsJson = it.next();
			Telephone tel = gson.fromJson(newsJson, Telephone.class);
			telephones.add(tel);
		}

		this.telephones = telephones;
	}

	public String readTelephones() throws IOException {
		InputStream is = getResources().openRawResource(R.raw.telephones);
		BufferedReader r = new BufferedReader(new InputStreamReader(is));

		StringBuilder total = new StringBuilder();

		String line;

		while ((line = r.readLine()) != null) {
			total.append(line);
		}

		return total.toString();
	}

}
