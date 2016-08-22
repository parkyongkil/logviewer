package tt.std.websocket.logview;

import java.util.HashMap;

import javax.websocket.Session;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

public class WxAppender<E extends ILoggingEvent> extends UnsynchronizedAppenderBase<E> {

	Session session;

	@Override
	protected void append(ILoggingEvent o) {
		try {
			if (session != null && session.isOpen()) {
				String s = String.format("%s %s %s - %s",
						DateFormatUtils.format(o.getTimeStamp(), "yyyy-MM-dd HH:mm:ss.S"), o.getLevel(),
						o.getLoggerName(), o.getFormattedMessage());
				HashMap<String, Object> m = new HashMap<>();
				m.put("Line", s);
				m.put("Timestamp", o.getTimeStamp());
				JSONObject j = new JSONObject(m);
				session.getBasicRemote().sendText(j.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
