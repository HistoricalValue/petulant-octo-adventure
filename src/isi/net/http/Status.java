package isi.net.http;

public enum Status {
	OK(200), NotFound(404);
	
	public int GetCode () {
		return code;
	}
	
	///////////////////////////////////////////////////////
	// state
	private final int code;
	
	private Status (final int code) {
		this.code = code;
	}
}
