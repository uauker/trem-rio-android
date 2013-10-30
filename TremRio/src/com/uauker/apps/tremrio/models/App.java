package com.uauker.apps.tremrio.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class App implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7576670826079800766L;

	@SerializedName("name")
	public String name;

	@SerializedName("icon")
	public String icon;

	@SerializedName("bundle")
	public String bundle;

	@SerializedName("url")
	public String url;

	@SerializedName("active")
	public boolean active;

	public static List<App> actived(final List<App> apps,
			final String currentBundle) {
		List<App> activedApps = new ArrayList<App>();

		for (App app : apps) {
			if (app.active && !app.bundle.equalsIgnoreCase(currentBundle)) {
				activedApps.add(app);
			}
		}

		return activedApps;
	}
}
