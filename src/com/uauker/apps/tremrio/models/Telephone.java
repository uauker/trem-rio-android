package com.uauker.apps.tremrio.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Telephone {

	@SerializedName("label")
	public String name;

	@SerializedName("tels")
	public List<String> tels;

	public CharSequence[] toCharSequenceList() {
		return this.tels.toArray(new CharSequence[this.tels.size()]);
	}
}
