package com.uauker.apps.tremrio.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

import com.uauker.apps.tremrio.R;
import com.uauker.apps.tremrio.adapters.ViewPagerAdapter;
import com.uauker.apps.tremrio.fragments.RamalFragment;
import com.uauker.apps.tremrio.fragments.TelephoneFragment;

public class MainActivity extends FragmentActivity implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private TabHost mTabHost;
	private ViewPager mViewPager;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, MainActivity.TabInfo>();
	private PagerAdapter mPagerAdapter;

	private class TabInfo {
		private String tag;
		private Class<?> clss;
		private Bundle args;
		private Fragment fragment;

		TabInfo(String tag, Class<?> clazz, Bundle args) {
			this.tag = tag;
			this.clss = clazz;
			this.args = args;
		}
	}

	// Um simples factory que retorna View para o TabHost
	class TabFactory implements TabContentFactory {

		private final Context mContext;

		public TabFactory(Context context) {
			mContext = context;
		}

		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}

	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Infla o layout
		setContentView(R.layout.activity_main);
		// Inicializa o TabHost
		this.initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			// Define a Tab de acordo com o estado salvo
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		// Inicializa o ViewPager
		this.intialiseViewPager();
	}

	protected void onSaveInstanceState(Bundle outState) {
		// salva a Tab selecionada
		outState.putString("tab", mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}

	private void intialiseViewPager() {

		List<Fragment> fragments = new Vector<Fragment>();
		fragments
				.add(Fragment.instantiate(this, RamalFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				TelephoneFragment.class.getName()));

		this.mPagerAdapter = new ViewPagerAdapter(
				super.getSupportFragmentManager(), fragments);
		this.mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
		this.mViewPager.setAdapter(this.mPagerAdapter);
		this.mViewPager.setOnPageChangeListener(this);
	}

	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		this.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab3")
				.setIndicator(getResources().getString(R.string.ramais)),
				(new TabInfo("Tab3", RamalFragment.class, args)));

		this.AddTab(
				this,
				this.mTabHost,
				this.mTabHost.newTabSpec("Tab2").setIndicator(
						getResources().getString(R.string.useful_telephones)),
				(new TabInfo("Tab2", TelephoneFragment.class, args)));

		mTabHost.setOnTabChangedListener(this);
	}

	private void AddTab(MainActivity activity, TabHost tabHost,
			TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
	}

	public void onTabChanged(String tag) {
		int pos = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(pos);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		this.mTabHost.setCurrentTab(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
