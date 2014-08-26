package com.github.ForumDevGroup.JungleBattle.network;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.github.ForumDevGroup.JungleBattle.Main;

public class LanguageAdapter extends PacketAdapter {

	public LanguageAdapter() {
		super(Main.instance(), PacketType.Play.Client.SETTINGS);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		String language = event.getPacket().getStrings().read(0);
		//do some really heavy shit with it
	}

}
