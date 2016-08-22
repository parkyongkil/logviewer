package tt.std.websocket.logview;

import javax.websocket.Session;

import ch.qos.logback.core.OutputStreamAppender;

public class WsOutputStreamAppender<E> extends OutputStreamAppender<E> {

	Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
