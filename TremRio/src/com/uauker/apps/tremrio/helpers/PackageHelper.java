package com.uauker.apps.tremrio.helpers;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageHelper {

	public static boolean isExists(Activity activity, String targetPackage) {
		PackageManager pm = activity.getPackageManager();

		try {
			pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			return false;
		}

		return true;
	}
}
