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

public class Loggers {
	
	private Loggers () {
	}
	
	///////////////////////////////////////////////////////
	public static AutoLogger L () {
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		Class<?> callingClass = null;
		try {
			callingClass = Class.forName(stackTrace[stackTrace.length - 1 - 1].getClassName());
		} catch (final ClassNotFoundException unused) {
		}
		
		return callingClass == null? null : GetAutoLogger(callingClass);
	}
	
	public static AutoLogger GetAutoLogger (final Class<?> klass) {
		AutoLogger autologger = autologgers.get(klass);
		
		if (autologger == null) {
			autologger = new AutoLogger(GetLogger(klass));
			autologgers.put(klass, autologger);
		}
		
		return autologger;
	}
	
	public static Logger GetLogger (final String name) {
		if (loggers.containsKey(name))
			return loggers.get(name);
		
		final Logger logger = Logger.getLogger(name);
		final Object previous = loggers.put(name, logger);
		assert previous == null;
		
		logger.addHandler(handler);
		
		return logger;
	}
	
	public static <T> Logger GetLogger (final Class<T> klass) {
		return GetLogger(klass.getCanonicalName());
	}
	
	///////////////////////////////////////////////////////
	
	public static void Initialise () throws SecurityException, IOException {
		LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(".handlers =\n.level = FINEST\n".getBytes(Encodings.UTF8)));
		
		final Path outpath = Runtime.GetRuntime().GetCwd().resolve("out.html");
		Files.deleteIfExists(outpath);
		handler = new Handler(Files.newBufferedWriter(outpath, Encodings.UTF8));
	}
	
	///////////////////////////////////////////////////////
	
	public static void CleanUp () {
	//	handler.close();
	}
	
	///////////////////////////////////////////////////////
	// state
	private static final Map<Class<?>, AutoLogger> autologgers = new HashMap<>(1000);
	private static final Map<String, Logger> loggers = new HashMap<>(1000);
	private static Handler handler;
}
