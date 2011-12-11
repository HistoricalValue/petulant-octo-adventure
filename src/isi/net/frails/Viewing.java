package isi.net.frails;

import java.io.IOException;
import java.io.Writer;

public interface Viewing {
	void WriteTo (Writer w) throws IOException;
}
