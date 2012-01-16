package isi.util;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

public class Runtime {
	
	///////////////////////////////////////////////////////
	// Singleton
	private static Deque<Runtime> runtimes = new LinkedList<>();
	
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
	// Runtime
	public Cwd GetCwd () {
		return cwd;
	}
	
	///////////////////////////////////////////////////////
	// Extensions/shortcuts
	public static Cwd GetCurrentCwd () {
		return GetRuntime().GetCwd();
	}

	
	///////////////////////////////////////////////////////
	// construcors
	public Runtime (final Cwd cwd) {
		this.cwd = cwd;
	}
	
	///////////////////////////////////////////////////////
	// state
	private final Cwd cwd;
	
}
