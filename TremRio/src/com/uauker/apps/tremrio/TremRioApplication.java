package com.uauker.apps.tremrio;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap.Config;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class TremRioApplication extends Application {

	private static Context globalApplicationContext;

	@Override
	public void onCreate() {
		super.onCreate();

		TremRioApplication.globalApplicationContext = getApplicationContext();

		configureImageLoader();
	}

	private void configureImageLoader() {
		File cacheDir = StorageUtils
				.getCacheDirectory(globalApplicationContext);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(false)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Config.RGB_565).cacheOnDisc(true)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				globalApplicationContext).defaultDisplayImageOptions(options)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new WeakMemoryCache()).threadPoolSize(5)
				.threadPriority(Thread.MIN_PRIORITY + 3)
				.memoryCacheSize(2 * 1024 * 1024)
				.discCache(new UnlimitedDiscCache(cacheDir))
				.discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
				.build();

		ImageLoader.getInstance().init(config);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static Context globalApplicationContext() {
		if (globalApplicationContext == null) {
			throw new IllegalStateException(
					"Application still was initialized. onCreate should be called before.");
		}

		return TremRioApplication.globalApplicationContext;
	}
}
