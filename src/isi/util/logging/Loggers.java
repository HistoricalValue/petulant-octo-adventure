package isi.util.logging;

import isi.util.charstreams.Encodings;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import isi.util.Runtime;
import isi.util.reflect.Callers;

public class Loggers {
	
	private Loggers () {
	}

	///////////////////////////////////////////////////////
	public static AutoLogger L () {
		return GetLogger(Callers.DetectCallerClass());
	}

	public static AutoLogger GetLogger (final String name) {
		return GetLogger(name, "");
	}
	
	public static AutoLogger GetLogger (final String name, final String instance) {
		final String loggerId = CreateLoggerId(name, instance);
		
		AutoLogger autologger = loggers.get(loggerId);
		
		if (autologger == null) {
			autologger = new AutoLogger(Logger.getLogger(loggerId));
			final Object previous = loggers.put(loggerId, autologger);
			assert previous == null;
			
			autologger.GetLogger().addHandler(handler);
		}
		
		return autologger;
	}

	public static AutoLogger GetLogger (final Class<?> klass, final String instance) {
		return GetLogger(ClassToLoggerName(klass), instance);
	}
	
	public static AutoLogger GetLogger (final Class<?> klass) {
		return GetLogger(klass, "");
	}

	///////////////////////////////////////////////////////

	public static void Initialise () throws SecurityException, IOException {
		LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(".handlers =\n.level = FINEST\n".getBytes(Encodings.UTF8)));

		final Path outpath = Runtime.GetCurrentCwd().resolve("out.html");
		Files.deleteIfExists(outpath);
		handler = new Handler(Files.newBufferedWriter(outpath, Encodings.UTF8));
	}

	///////////////////////////////////////////////////////

	public static void CleanUp () {
	//	handler.close();
	}
	
	///////////////////////////////////////////////////////
	// utils
	private static String ClassToLoggerName (final Class<?> klass) {
		return klass.getCanonicalName();
	}
	
	private static String CreateLoggerId (final String name, final String instance) {
		return name + "." + instance;
	}


	///////////////////////////////////////////////////////
	// state
	private static final Map<String, AutoLogger> loggers = new HashMap<>(1000);
	private static Handler handler;
}
