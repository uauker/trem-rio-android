package com.uauker.apps.tremrio.models;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class Ticket {

	public String name;
	public String price;

	public Ticket(String text) {
		Pattern p = Pattern.compile("(.+?)(R\\$[0-9]+,[0-9]{2})");
		Matcher m = p.matcher(text);

		if (m.find()) {
			this.name = m.group(1);
			this.price = m.group(2);
		}
	}

	public boolean isValid() {
		return this.name != null && this.price != null;
	}

	public static List<Ticket> parse(String result) {
		List<Ticket> tickets = new ArrayList<Ticket>();

		try {
			Document parse = Jsoup.parse(result);
			Elements select = parse.select("div#cont_tarifas > ul > li");

			for (Element element : select) {
				Ticket ticket = new Ticket(element.text());

				if (ticket != null && ticket.isValid()) {
					tickets.add(ticket);
				}
			}
		} catch (Exception e) {
			Log.e("Parse Ticket Error", "Erro no parse do ticket");
		}

		return tickets;
	}

}
