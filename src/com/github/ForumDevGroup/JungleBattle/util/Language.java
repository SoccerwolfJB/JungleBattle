package com.github.ForumDevGroup.JungleBattle.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.github.ForumDevGroup.JungleBattle.Main;

public enum Language {
	AFRIKAANS("af_ZA", "Afrikaans (ZA)"),
	ARABIAN("ar_SA", "العربية (العالم العربي)"),
	BULGARIAN("bg_BG", "Български (BG)"),
	CATALAN("ca_ES", "Català (CAT)"),
	CZECH("cs_CZ", "Čeština (CZ)"),
	WELSH("cy_GB", "Cymraeg (Cymru)"),
	DANSK("da_DK", "Dansk (DK)"),
	GERMAN("de_DE", "Deutsch (Deutschland)"),
	GREEK("el_GR", "Ελληνικά (El)"),
	AUSTRALIAN_ENGLISH("en_AU", "Australian English (Australia)"),
	CANADIAN_ENGLISH("en_CA", "Canadian English (CA)"),
	ENGLISH("en_GB", "English (UK)"),
	PIRATE_ENGLISH("en_PT", "Pirate Speak (PIRATE)"),
	ESPERANTO("eo_UY", "Esperanto (Mondo)"),
	TAGALOG("fil_PH", "Tagalog"),
	ARGENTINEAN_SPANISH("es_AR", "Español (Argentina)"),
	SPANISH("es_ES", "Español (España)"),
	MEXICAN_SPANISH("es_MX", "Español (México)"),
	URUGUAYAN_SPANISH("es_UY", "Español (Uruguay)"),
	VENEZUELAN_SPANISH("es_VE", "Español (Venezuela)"),
	ESTONIAN("et_EE", "Eesti (ET)"),
	ARMENIAN("hy_AM", "Հայերեն (Հայաստան)"),
	BASQUE("eu_ES", "Euskara (EH)"),
	FINNISH("fi_FI", "suomi (FI)"),
	FRENCH("fr_FR", "Français (France)"),
	CANADIAN_FRENCH("fr_CA", "Français (CA)"),
	IRISH("ga_IE", "Gaeilge (Éire)"),
	GALICIAN("gl_ES", "Galego (Galicia)"),
	HEBREW("he_IL", "אנגלית (IL)"),
	HINDU("hi_IN", "अंग्रेज़ी (भारत)"),
	INDIAN("hr_HR", "Hrvatski (HR)"),
	HUNGARIAN("hu_HU", "Magyar (HU)"),
	INDONESIAN("id_ID", "Bahasa Indonesia (ID)"),
	LUXEMBOURGISH("lb_LU", "Lëtzebuergesch (Lëtzebuerg)"),
	ICELANDIC("is_IS", "Íslenska (IS)"),
	ITALIAN("it_IT", "Italiano (Italia)"),
	JAPANESE("ja_JP", "日本語 (日本)"),
	GEORGIAN("ka_GE", "ქართული (საქართველო)"),
	SOUT_KOREAN("ko_KR", "한국어 (KR)"),
	CORNISH("kw_GB", "Kernowek (Cornwall)"),
	LATIN("la_LA", "Lingua Latina (Vatican)"),
	LITHUANIAN("lt_LT", "Lietuvių (Lietuva)"),
	LATVIAN("lv_LV", "Latviešu (Latvija)"),
	MALAYSIAN("ms_MY", "Bahasa Melayu (MS)"),
	MALTESE("mt_MT", "Malti (MT)"),
	DUTCH("nl_NL", "Nederlands (Nederland)"),
	NEW_NORWEGIAN("nn_NO", "Norsk nynorsk (Noreg)"),
	NORWEGIAN("no_NO", "Norsk (NO)"),
	POLISH("pl_PL", "Polski (Polska)"),
	BRAZILIAN_PORTUGUESE("pt_BR", "Português (BR)"),
	PORTUGUESE("pt_PT", "Português (Portugal)"),
	QUENYA("qya_AA", "Quenya (Arda)"),
	ROMANIAN("ro_RO", "Română (RO)"),
	RUSSIAN("ru_RU", "Русский (Россия)"),
	SLOVAK("sk_SK", "Slovenčina (SK)"),
	SLOVENIAN("sl_SI", "Slovenščina (SLO)"),
	SERBIAN("sr_SP", "Српски (Србија)"),
	SWEDISH("sv_SE", "Svenska (Sverige)"),
	THAI("th_TH", "ภาษาไทย (TH)"),
	KLINGON("tlh_AA", "tlhIngan Hol (US)"),
	TURKISH("tr_TR", "Türkçe (TR)"),
	UKRAINIAN("uk_UA", "Українська (Україна)"),
	VIETNAMESE("vi_VN", "Tiếng Việt (VN)"),
	SIMPLIFIED_CHINESE("zh_CN", "简体中文 (中国)"),
	TRADITIONAL_CHINESE("zh_TW", "繁體中文 (台灣)");

	private String shortn;
	private String longn;
	private HashMap<String, String> translations = new HashMap<String, String>();
	private final static HashMap<String, Language> BY_NAME = new HashMap<String, Language>();

	// private final static HashMap<String, Language> BY_ENUM_NAME = new
	// HashMap<String, Language>();

	private Language(String shortn, String longn) {
		this.shortn = shortn;
		this.longn = longn;
	}

	public static void initTranslations() {
		try {
			MySQL sql = Main.getMySQL();
			ResultSet rs = sql.query("SELECT * FROM Languages");
			while (rs.next()) {
				HashMap<String, String> translations = Language.getByShortName(
						rs.getString("lang")).getTranslations();
				translations.put(rs.getString("name"),
						rs.getString("translation"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void reloadTranslations() {
		translations.clear();
		try {
			MySQL sql = Main.getMySQL();
			ResultSet rs = sql.query("SELECT * FROM Languages WHERE lang = '"
					+ shortn + "'");
			while (rs.next()) {
				HashMap<String, String> translations = Language.getByShortName(
						rs.getString("lang")).getTranslations();
				translations.put(rs.getString("name"),
						rs.getString("translation"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getPercentCompleted() {
		return (int) (translations.size() / GERMAN.getTranslations().size() * 100);
	}

	public static boolean isSupported(String shortn) {
		return Language.getByShortName(shortn) != null;
	}

	public String getShortName() {
		return this.shortn;
	}

	public HashMap<String, String> getTranslations() {
		return translations;
	}

	public String getTranslation(String name) {
		String translation = translations.get(name);
		if (this == GERMAN && translation == null) {
			try {
				Main.getMySQL()
						.query("INSERT INTO Languages VALUES('" + shortn
								+ "', '" + name + "', 'Default Value'");
				translations.put(name, "Default Value");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (translation == null
				&& !this.getShortName().equals(GERMAN.getShortName())) {
			return GERMAN.getTranslation(name);
		}
		return translation;
	}

	public String getLongName() {
		return this.longn;
	}

	/*
	 * public static Language getByEnumName(String enumname) { return
	 * BY_ENUM_NAME.get(enumname.toLowerCase()); }
	 */

	public static Language getByShortName(String shortn) {
		return BY_NAME.get(shortn);
	}

	static {
		for (Language lang : values()) {
			BY_NAME.put(String.valueOf(lang.getShortName()), lang);
			// BY_ENUM_NAME.put(String.valueOf(lang.name().toLowerCase()),
			// lang);
		}
	}

}