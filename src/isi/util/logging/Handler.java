package isi.util.logging;

import isi.util.Strings;
import isi.util.Throwables;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.LogRecord;
import java.util.logging.LoggingPermission;
import java.util.regex.Pattern;

/**
 *
 * @author Amalia
 */
public class Handler extends java.util.logging.Handler {
	
	///////////////////////////////////////////////////////
	
	public Handler (final Writer w) throws IOException {
		this.w = w;
		
		w.append( ""
				+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
				+ "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">\n"
				+ "<head>\n"
				+ "<meta name=\"generator\" content=\"HTML Tidy for Linux/x86 (vers 1 September 2005), see www.w3.org\" />\n"
				+ "<title>  </title>\n"
				+ "<meta name=\"generator\" content=\"vi\" />\n"
				+ "<meta name=\"author\" content=\"isi\" />\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />\n"
				+ "<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n"
				+ "<meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />\n"
				+ "<!-- link rel=\"stylesheet\" type=\"text/css\" href=\"./style.css\" / -->\n"
				+ "<style type=\"text/css\" media=\"screen\">\n")
		.append(HandlerResources.GetStylesheet())
		.append(""
				+ "</style>\n"
				+ "<!-- script type=\"text/javascript\" src=\"script.js\" --><!-- /script -->\n"
				+ "<script type=\"text/javascript\">\n"
				+ "//<![CDATA[\n")
		.append(HandlerResources.GetScript())
		;
	}
	
	///////////////////////////////////////////////////////
	
	@Override
	public void publish (final LogRecord r) {
		records.add(r);
	}
	
	///////////////////////////////////////////////////////

	@Override
	public void flush () {
	}

	///////////////////////////////////////////////////////
	
	@Override
	public void close () throws SecurityException {
		checkLoggingPermission();

		try {
			WriteJavascriptRecords();
			WriteJsInitialiseFunction(levels);
			w.append("\n"
					+ "//]]>\n"
					+ "</script>\n"
					+ "</head>\n"
					+ "<body onload=\"Initialise();\">\n");
			WriteControls();
			WriteHtmlRecords();
			w.append("</body></html>").close();
		} catch (final IOException ex) {
			ok.SpoilOk(ex);
		}
	}

	private void WriteControls () throws IOException {
		w.append("\t<div id=\"controls\">\n");
		for (final String[] level: levels)
			w.append("\t\t<div id=\"")
					.append(level[0])
					.append("_switch\">\n"
					+ "\t\t\t<span class=\"switch\">\n"
					+ "\t\t\t\t<span class=\"on\">ON&nbsp;</span>\n"
					+ "\t\t\t</span>\n"
					+ "\t\t\t<span class=\"control ")
					.append(level[1])
					.append("\">")
					.append(level[0])
					.append("</span>\n"
					+ "\t\t</div>\n");

		w.append("\t</div> <!-- controls -->\n\n");
	}
	
	private void WriteJsInitialiseFunction (final String[][] levels) throws IOException {
		w.append("\n\n"
				+ "function Initialise () {\n"
				+ "	G.ON_CLASS		= \"on\"		;\n"
				+ "	G.ON_TEXT		= \"ON&nbsp;\"	;\n"
				+ "	G.OFF_CLASS		= \"off\"		;\n"
				+ "	G.OFF_TEXT		= \"OFF\"		;\n"
				+ "	G.RecordLevel	= {}			;\n");
				
		for (final String[] level: levels)
			w.append("	G.RecordLevel.").append(level[0]).append(" = \"").append(level[1]).append("\";\n")
					.append("	G[MakeSwitchPanelId(\"").append(level[0]).append("\")] = $(MakeSwitchPanelId(\"").append(level[0]).append("\"));\n")
					.append("	G[MakeSwitchPanelId(\"").append(level[0]).append("\")].onclick = MakeSwitcher(\"").append(level[0]).append("\");\n");
					
		w.append("}\n");
	}
	
	private void WriteHtmlRecords () throws IOException {
		for (final LogRecord r: records)
			WriteHtmlRecord(r);
	}
	
	private void WriteHtmlRecord (final LogRecord r) throws IOException {
		w.append("<div class=\"record ")
				.append(isi.util.html.Helpers.h(r.getLevel().toString()))
				.append("\"><div class=\"date\">")
				.append(isi.util.html.Helpers.h(new Date(r.getMillis()).toString()))
				.append("</div>\n<div class=\"logger\">")
				.append(isi.util.html.Helpers.h(r.getLoggerName()))
				.append("</div><div class=\"message\">")
				.append(isi.util.html.Helpers.h(r.getMessage()))
				.append("</div></div> <!-- record --> \n");
	}
	
	private void WriteJavascriptRecords () throws IOException {
		w.append("\n\nrecords = [");
		
		for (final LogRecord r: records) {
			final String loggerName = r.getLoggerName();
			final String[] loggerPath = DOT.split(loggerName);
			w.append("\n\t{\n\t\t\"logger\": [");
			for (final String p: loggerPath)
				w.append("\"").append(p).append("\", ");
			
			w
					.append("],\n\t\t  \"level\": \"").append(r.getLevel().toString())
					.append("\",\n\t\t \"message\": \"").append(Strings.Escape(r.getMessage()))
					.append("\",\n\t\t \"millis\": ").append(Long.toString(r.getMillis()))
					.append(",\n\t\t \"date\": \"").append(new Date(r.getMillis()).toString())
					.append("\",\n\t\t \"seq\": ").append(Long.toString(r.getSequenceNumber()))
					.append(",\n\t\t \"thread\": ").append(Integer.toString(r.getThreadID()))
					.append(",\n\t\t \"class\": \"").append(r.getSourceClassName())
					.append("\",\n\t\t \"method\": \"").append(r.getSourceMethodName())
					.append("\",\n\t\t \"parameters\": ");
			
			if (r.getParameters() != null) {
				w.append("[");
				for (final Object param: r.getParameters())
					w.append("\"").append(Strings.Escape(param)).append("\", ");
				w.append("]");
			}
			else 
				w.append("null");
			
			w.append(",\n\t\t \"throwable\": ");
			if (r.getThrown() == null)
				w.append("null");
			else
				w.append("\"").append(Strings.Escape(Throwables.toString(r.getThrown()))).append("\"");
			
			w.append("},");
		}
		
		w.append("];");
	}
	
	private static void checkLoggingPermission () {
		final SecurityManager securityManager = System.getSecurityManager();
		if (securityManager != null)
			securityManager.checkPermission(new LoggingPermission("control", null));
	}
	
	///////////////////////////////////////////////////////
	// private
	private class AllOk {
		private boolean ok = true;
		private Throwable reason;
		
		void SpoilOk (final Throwable reason) {
			if (ok) {
				ok = false;
				this.reason = reason;
			}
			else
				throw new RuntimeException(reason);
		}
		
		boolean IsOk () {
			return ok;
		}
		
		Throwable GetReason () {
			return reason;
		}
	}
	
	///////////////////////////////////////////////////////
	// state
	private final AllOk ok = new AllOk();
	private boolean closed = false;
	private final Writer w;
	private final List<LogRecord> records = new LinkedList<>();
	
	///////////////////////////////////////////////////////
	// common
	private static final Pattern DOT = Pattern.compile("\\.");
	private static final String[][] levels =
			new String[][] {
				new String[] {"finest", "FINEST"},
				new String[] {"info", "INFO"},
				new String[] {"warning", "WARNING"}
			};
}
