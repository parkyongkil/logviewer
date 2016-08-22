package tt.std.websocket.logview;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;

@ServerEndpoint("/logview/{loggerName}")
public class LogviewController {

	private static long NO;
	Session session;
	String loggerName;
	String appenderName;
	String charset = "UTF-8";

	WxAppender<ILoggingEvent> appender;
	Logger logger;

	@OnOpen
	public void handleOpen(Session session, @PathParam("loggerName") String loggerName) throws IOException {
		System.out.println("client is now connected...");
		this.session = session;
		this.loggerName = loggerName;
		this.appenderName = String.format("logCaptureAppender_%d", NO++);

		String sid = session.getId();
		System.out.println(String.format("SESSIONID = %s", sid));
		this.startLog();
	}

	@OnMessage
	public String handleMessage(String message) {
		System.out.println("receive from client : " + message);
		String replymessage = "echo " + message;
		System.out.println("send to client : " + replymessage);
		return replymessage;
	}

	@OnClose
	public void handleClose() {
		System.out.println("client is now disconnected...");
		this.stopLog();
	}

	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
		this.stopLog();
	}

	public void startLog() throws IOException {

		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(context);
		encoder.setCharset(Charset.forName(charset));
		encoder.setPattern("%d{HH:mm:ss} %-5level %logger{36} - %msg%n");
		encoder.setOutputPatternAsHeader(true);
		encoder.setImmediateFlush(true);
		encoder.start();

		appender = new WxAppender<>();
		appender.setContext(context);
		appender.setName(appenderName);
		// appender.setEncoder(encoder);
		appender.setSession(session);
		appender.start();

		logger = context.getLogger(loggerName);
		logger.addAppender(appender);
		logger.info(String.format("START LOG-CAPTURE: %s, %s", loggerName, appenderName));
	}

	public void stopLog() {
		if (logger != null) {
			logger.info(String.format("END LOG-CAPTURE: %s, %s", loggerName, appenderName));
			if (logger.isAttached(appender))
				logger.detachAppender(appender);
		}
	}
}
