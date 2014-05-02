package ilearnrw.textclassification.english;

import java.util.ArrayList;

public class CharacterInfo {
	private static CharacterInfo instance = null;
	private static ArrayList<String> cDigraphs 	= new ArrayList<String>();
	private static ArrayList<String> cTrigraphs	= new ArrayList<String>();
	
	protected CharacterInfo() {
	}
	
	public static CharacterInfo getInstance(){
		if(instance == null){
			instance = new CharacterInfo();
			createLists();
		}
		return instance;
	}
	
	private static void createLists(){
		/**
    	 * Consonant digraphs
    	 * 	- http://www.enchantedlearning.com/consonantblends/
    	 */
    	getDigraphs().add("bl"); getDigraphs().add("br"); getDigraphs().add("ch"); getDigraphs().add("ck"); getDigraphs().add("cl");
    	getDigraphs().add("cr"); getDigraphs().add("dr"); getDigraphs().add("fl"); getDigraphs().add("fr"); getDigraphs().add("gh");
    	getDigraphs().add("gl"); getDigraphs().add("gr"); getDigraphs().add("ng"); getDigraphs().add("ph"); getDigraphs().add("pl");
    	getDigraphs().add("pr"); getDigraphs().add("qu"); getDigraphs().add("sc"); getDigraphs().add("sh"); getDigraphs().add("sk");
    	getDigraphs().add("sl"); getDigraphs().add("sm"); getDigraphs().add("sn"); getDigraphs().add("sp"); getDigraphs().add("st");
    	getDigraphs().add("sw"); getDigraphs().add("th"); getDigraphs().add("tr"); getDigraphs().add("tw"); getDigraphs().add("wh");
    	getDigraphs().add("wr");
    	
    	/**
    	 * Consonant trigraphs
    	 * 	- http://www.enchantedlearning.com/consonantblends/
    	 */
    	
    	getTrigraphs().add("nth"); getTrigraphs().add("sch"); getTrigraphs().add("scr"); getTrigraphs().add("shr"); getTrigraphs().add("spl");
    	getTrigraphs().add("spr"); getTrigraphs().add("squ"); getTrigraphs().add("str"); getTrigraphs().add("tch"); getTrigraphs().add("thr");
	}

	public static ArrayList<String> getTrigraphs() {
		return cTrigraphs;
	}

	public static void setTrigraphs(ArrayList<String> cTrigraphs) {
		CharacterInfo.cTrigraphs = cTrigraphs;
	}

	public static ArrayList<String> getDigraphs() {
		return cDigraphs;
	}

	public static void setDigraphs(ArrayList<String> cDigraphs) {
		CharacterInfo.cDigraphs = cDigraphs;
	}
	
}
