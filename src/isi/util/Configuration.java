package isi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

	///////////////////////////////////////////////////////
	// state
	private final Map<String, String> config = new HashMap<>(30);
	private Path storagePath;
	private Exception failure;

	private void SetFailure (final Exception ex) {
		if (failure != null) {
			final IllegalStateException ilst = new IllegalStateException("There is another failure pending", failure);
			ilst.addSuppressed(ex);
			throw ilst;
		}

		failure = ex;
	}

	///////////////////////////////////////////////////////
	//
	public boolean Load (final InputStream ins) {
		if (!config.isEmpty())
			throw new IllegalStateException("configuration not empty");

		boolean loaded;
		try {
			final ObjectInputStream oins = new ObjectInputStream(ins);
			final int numberOfEntries = oins.readInt();
			for (int i = 0; i < numberOfEntries; ++i) {
				final String name = (String) oins.readObject();
				final String value = (String) oins.readObject();
				config.put(name, value);
			}
			loaded = true;
		}
		catch (final IOException | ClassNotFoundException ex) {
			loaded = false;
			SetFailure(ex);
		}

		return loaded;
	}

	public boolean Load (final ReadableByteChannel rch) {
		return Load(Channels.newInputStream(rch));
	}

	public boolean Load () {
		boolean loaded;
		try (final SeekableByteChannel rch = Files.newByteChannel(storagePath, StandardOpenOption.READ)) {
			Load(rch);
			loaded = true;
		}
		catch (final IOException ex) {
			loaded = false;
			SetFailure(ex);
		}
		return loaded;
	}

	///////////////////////////////////////////////////////
	//
	public void Store (final OutputStream outs) throws IOException {
		final ObjectOutputStream oouts = new ObjectOutputStream(outs);

		oouts.writeInt(config.size());
		for (final Map.Entry<String, String> entry: config.entrySet()) {
			oouts.writeObject(entry.getKey());
			oouts.writeObject(entry.getValue());
		}

		oouts.flush();
	}

	public void Store (final WritableByteChannel wch) throws IOException {
		Store(Channels.newOutputStream(wch));
	}

	public void Store () throws IOException {
		try (final SeekableByteChannel wch = Files.newByteChannel(storagePath, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE_NEW)) {
			Store(wch);
		}
	}

	///////////////////////////////////////////////////////
	//
	public void SetStoragePath (final Path path) {
		storagePath = path;
	}

	public Path GetStoragePath () {
		return storagePath;
	}

	///////////////////////////////////////////////////////
	//
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public Map<String, String> GetConfig () {
		return config;
	}

	/**
	 *
	 * @return loaded?
	 */
	public boolean LoadIFEmpty () {
		boolean loaded = config.isEmpty();

		if (loaded)
			loaded = Load();

		return loaded;
	}

	///////////////////////////////////////////////////////
	//
	public Exception GetFailureReason () {
		return failure;
	}

	public void ResetFailure () {
		failure = null;
	}
}
