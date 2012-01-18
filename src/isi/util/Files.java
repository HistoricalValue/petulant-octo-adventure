package isi.util;

public class Files {

	///////////////////////////////////////////////////////
	//
	public static String GetExtension (final String path) {
		final int dot = path.lastIndexOf('.');
		if (dot < 0)
			return null;
		assert dot <= path.length();
		return path.substring(dot + 1);
	}

	///////////////////////////////////////////////////////
	//
	private Files () {
	}
}
