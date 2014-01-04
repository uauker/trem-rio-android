package com.uauker.apps.tremrio.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

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

	public static List<Station> parse(String result) {
		List<Station> stations = new ArrayList<Station>();

		try {
			Document parse = Jsoup.parse(result);
			Elements select = parse.select("div.box_ramal");

			for (Element element : select) {
				Element name = element.select("span.title_ramal").first();
				Elements directions = element.select("div.box_sentido");
				Elements status = element.select("div.box_situacao");

				Station station = new Station();
				station.name = name.text();
				station.backgroundColor = Station
						.backgroundColorFromSpanStyle(name.attr("style"));

				station.startDirection = directions.first().text();
				station.startDirectionStatus = status.first().text();

				station.endDirection = directions.last().text();
				station.endDirectionStatus = status.last().text();

				stations.add(station);
			}
		} catch (Exception e) {
			Log.e("Error parse station",
					"Ocorreu um erro no momento do parse das estacoes");
		}

		return stations;
	}

	// exemplo: background-color:#fbca01
	public static String backgroundColorFromSpanStyle(String text) {
		String color = "#000000";

		Pattern p = Pattern.compile("^background\\-color:(#.{6})$");
		Matcher m = p.matcher(text);

		if (m.find()) {
			color = m.group(1);
		}

		return color;
	}

}
