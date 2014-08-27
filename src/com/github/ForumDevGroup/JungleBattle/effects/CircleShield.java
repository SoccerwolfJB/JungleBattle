package com.github.ForumDevGroup.JungleBattle.effects;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CircleShield extends CustomEffect {
	@Override
	public void runEffect(Player p) {
		Location l = p.getEyeLocation().clone(); // Klone EyeLocation des
													// Spielers
		Location startLoc = p.getLocation(); // Location für Startpunkt
		startLoc.setY(startLoc.getY() + 1.8); // deklariere Startlocation
		double factor = 0; // Faktor für Sin / Cos Berechnung
		boolean water = true; // Wasser Boolean Variable
		while (factor <= 1D) { // während Faktor kleiner oder gleich 1 ist,
								// führe Folgendes aus:
			double radians = 2 * Math.PI * factor; // Berechne Radians
			double xfac = Math.sin(radians); // Berechne X-Koordinaten-Faktor
												// des Effekts
			double zfac = Math.cos(radians); // Berechne Z-Koordinaten-Faktor
												// des Effekts
			l.setX(startLoc.getX() + xfac); // Addiere Faktor hinzu
			l.setZ(startLoc.getZ() + zfac); // Ebenso
			for (double y = 0; y < 1.8; y += 0.2) { // 9-malige Ausführung
				l.setY(startLoc.getY() - y); // Subtrahiere y-Faktor
				if (water)
					p.getWorld().playEffect(l, Effect.WATERDRIP, 0); // Selbsterklärend
				else
					p.getWorld().playEffect(l, Effect.LAVADRIP, 0);
			}
			l.setY(startLoc.getY()); // Setze Y wieder zu der des Kopfes
			water = !water; // Drehe water Variable um
			factor = factor + (1D / 60D); // Addiere 1/60 hinzu (61 Spalten von
											// Drops um den Spieler herum)
		}
	}
}