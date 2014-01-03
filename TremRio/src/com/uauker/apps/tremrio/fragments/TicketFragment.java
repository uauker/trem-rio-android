package com.uauker.apps.tremrio.fragments;

import java.util.ArrayList;
import java.util.List;

import org.afinal.simplecache.ACache;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.ads.AdView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.adapters.TicketAdapter;
import com.uauker.apps.tremrio.helpers.BannerHelper;
import com.uauker.apps.tremrio.helpers.TryAgainHelper;
import com.uauker.apps.tremrio.helpers.TryAgainHelper.OnClickToTryAgain;
import com.uauker.apps.tremrio.models.Ticket;

public class TicketFragment extends Fragment implements OnClickToTryAgain {

	public static final String SUPERVIA_TICKET_URL = "http://www.supervia.com.br/mobile/";

	public static final int CACHE_TIME = 5 * 60;
	
	ViewStub messageViewStub;
	
	ListView ticketsListView;

	List<Ticket> tickets;

	AsyncHttpClient client = new AsyncHttpClient();

	AdView adView;

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
		
		this.cache = ACache.get(this.ownerActivity);

		View view = (RelativeLayout) inflater.inflate(R.layout.ticket_fragment,
				container, false);
		
		this.ticketsListView = (ListView) view.findViewById(R.id.tickets);

		this.messageViewStub = (ViewStub) view.findViewById(R.id.ticket_info);

		this.loadingViewStub = (ViewStub) view.findViewById(R.id.ticket_loading);

		this.loadingViewStub.setLayoutResource(R.layout.loading);

		this.internetFailureViewStub = (ViewStub) view
				.findViewById(R.id.ticket_internet_failure);
		this.internetFailureViewStub
				.setLayoutResource(R.layout.internet_failure);
		TryAgainHelper tryAgainView = (TryAgainHelper) this.internetFailureViewStub
				.inflate();
		tryAgainView.setOnClickToTryAgain(this);
		
		this.loadTickets();
		
		this.adView = BannerHelper.setUpAdmob(view);

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		this.ownerActivity = activity;
	}

	@Override
	public void tryAgain() {
		this.loadTickets();
	}

	private void loadTickets() {
		this.tickets = ((ArrayList<Ticket>) cache.getAsObject(SUPERVIA_TICKET_URL));

		InternUrlAsyncTask internUrlAsyncTask = new InternUrlAsyncTask();

		if (this.tickets == null || this.tickets.size() == 0) {
			this.tickets = new ArrayList<Ticket>();

			client.get(SUPERVIA_TICKET_URL, internUrlAsyncTask);
		} else {
			setupListView();
		}		
	}

	public void setupListView() {
		TicketAdapter adapter = new TicketAdapter(
				TicketFragment.this.ownerActivity, R.layout.cell_ticket, this.tickets);

		ticketsListView.setAdapter(adapter);
	}
	
	class InternUrlAsyncTask extends AsyncHttpResponseHandler {
		@Override
		public void onStart() {
			super.onStart();

			TicketFragment.this.loadingViewStub.setVisibility(View.VISIBLE);
			TicketFragment.this.internetFailureViewStub.setVisibility(View.GONE);
			TicketFragment.this.ticketsListView.setVisibility(View.GONE);
		}

		@Override
		public void onFailure(Throwable arg0, String arg1) {
			super.onFailure(arg0, arg1);

			TicketFragment.this.loadingViewStub.setVisibility(View.GONE);
			TicketFragment.this.internetFailureViewStub
					.setVisibility(View.VISIBLE);
			TicketFragment.this.ticketsListView.setVisibility(View.GONE);
		}

		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);

			try {
				TicketFragment.this.tickets.clear();

				TicketFragment.this.tickets = Ticket.parse(result);

				TicketFragment.this.setupListView();
				
				TicketFragment.this.loadingViewStub.setVisibility(View.GONE);
				TicketFragment.this.internetFailureViewStub
						.setVisibility(View.GONE);
				TicketFragment.this.ticketsListView.setVisibility(View.VISIBLE);				
			} catch (Exception e) {
				this.onFailure(e, "");
			}
		}
	}
}
