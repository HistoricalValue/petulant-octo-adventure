package isi.util.html;

public class Helpers {

	///////////////////////////////////////////////////////
	//
	public static String h (final String str) {
		return str
				.replaceAll("\\&", "&amp;")
				.replaceAll("\\\"", "&quot;")
				.replaceAll("\\'", "&#039;")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	///////////////////////////////////////////////////////
	//
	private Helpers () {
	}
}
