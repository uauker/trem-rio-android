package com.uauker.apps.tremrio.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Station implements Serializable {

	private static final long serialVersionUID = 6306380655721155991L;

	@SerializedName("name")
	public String name;
	
	@SerializedName("background_color")
	public String backgroundColor;
	
	@SerializedName("start_direction")
	public String startDirection;
	
	@SerializedName("end_direction")
	public String endDirection;
	
	@SerializedName("start_direction_status")
	public String startDirectionStatus;

	@SerializedName("end_direction_status")
	public String endDirectionStatus;
	
}
