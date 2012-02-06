package isi.util;

import isi.util.logging.AutoLogger;
import isi.util.logging.Loggers;
import isi.util.reflect.Callers;
import java.io.PrintStream;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

public class Runtime {

	///////////////////////////////////////////////////////
	// Singleton
	private static final Deque<Runtime> runtimes = new LinkedList<>();
	private static final IdGenerator idgen = new IdGenerator("runtime_", "");

	public static Runtime GetRuntime () {
		return Objects.requireNonNull(runtimes.peek());
	}

	public static void PushRuntime (final Runtime runtime) {
		runtimes.push(runtime);
	}

	public static void PopRuntime () {
		runtimes.pop();
	}

	///////////////////////////////////////////////////////
	// Util extensions
	public static void PushDefault () {
		PushRuntime(CreateDefault());
	}

	public static void cd (final String relPath) {
		PushRuntime(new Runtime(
				new Cwd(GetCurrentCwd().resolve(relPath)),
				GetCurrentStdout()));
	}

	///////////////////////////////////////////////////////
	// Runtime
	public Cwd GetCwd () {
		return cwd;
	}

	public AutoLogger GetLoggerFor (final Class<?> klass) {
		return Loggers.GetLogger(klass, id);
	}

	public PrintStream GetStdout () {
		return stdout;
	}

	///////////////////////////////////////////////////////
	// Extensions/shortcuts
	public static Cwd GetCurrentCwd () {
		return GetRuntime().GetCwd();
	}

	public static AutoLogger GetCurrentLoggerFor (final Class<?> klass) {
		return GetRuntime().GetLoggerFor(klass);
	}

	public static AutoLogger GetCurrentLogger () {
		return GetCurrentLoggerFor(Callers.DetectCallerClass());
	}

	public static PrintStream GetCurrentStdout () {
		return GetRuntime().GetStdout();
	}

	///////////////////////////////////////////////////////
	// construcors
	public Runtime (final Cwd cwd, final PrintStream stdout) {
		this.cwd = cwd;
		this.stdout = stdout;
	}

	///////////////////////////////////////////////////////
	// Factories
	@SuppressWarnings("UseOfSystemOutOrSystemErr")
	public static Runtime CreateDefault () {
		return new Runtime(
				new Cwd(Objects.toString(System.getProperty("user.dir"), ".")),
				System.out);
	}

	///////////////////////////////////////////////////////
	// state
	private final Cwd cwd;
	private final String id = idgen.next();
	private final PrintStream stdout;
}
