package isi.util.html;

public class Helpers {
	
	///////////////////////////////////////////////////////
	//
	public static String h (final String str) {
		return str.replaceAll("\\\"", "&quot;");
	}
	
	///////////////////////////////////////////////////////
	//
	private Helpers () {
	}
}
