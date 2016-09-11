package backend;

import java.io.IOException;

public interface Command {
	
	public static final String CONNECT = "connect".toLowerCase();
	public static final String CHECKSTRING = "checkString".toLowerCase();
	
	public String execute(Bot bot)throws IOException;
}
