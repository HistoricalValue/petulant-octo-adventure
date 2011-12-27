/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isi.util.streams;

import java.io.StreamTokenizer;

/**
 *
 * @author TURBO_X
 */
public enum StreamTokeniserTokenType {
	EOF(StreamTokenizer.TT_EOF), EOL(StreamTokenizer.TT_EOL), NUMBER(StreamTokenizer.TT_NUMBER), WORD(StreamTokenizer.TT_WORD);
	// state
	private final int val;

	private StreamTokeniserTokenType(final int val) {
		this.val = val;
	}

	public int GetVal() {
		return val;
	}

	public static StreamTokeniserTokenType valueOf(final int val) {
		for (final StreamTokeniserTokenType type : values()) {
			if (type.GetVal() == val) {
				return type;
			}
		}
		throw new RuntimeException("No token type for value " + val);
	}
	
}
