package backend;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;

public class Connect implements Command {

	public static final String META_GET = "get";
	public static final String META_POST = "post";
	public static final String META_URL = "url";
	public static final String META_METHOD = "method";
	public static final String META_DATA = "data";
	public static final String META_TIMEOUT = "timeout";
	private String url;
	private String method;
	private int timeout = 3;
	private HashMap<String, String> data;

	public Connect(String url) {
		super();
		this.url = url;
		data = new HashMap<>();
		method = this.META_GET;

	}
	
	public Connect(Step step) {
		HashMap<String, Object> arguments = step.getArguments();
		url = (String) arguments.get(Connect.META_URL);
		data = (HashMap<String, String>) arguments.get(Connect.META_DATA);



		data = data ==null ? new HashMap<String, String>():data;
		for (String key :
				data.keySet()) {
			String value = data.get(key);
			if( value.startsWith("#") ){
				data.put(key, Global_Param.get(key) );
			}
		}
		method = (String) arguments.get(Connect.META_METHOD);
		timeout = arguments.get(Connect.META_TIMEOUT)!= null?Integer.parseInt((String)arguments.get(Connect.META_TIMEOUT)):timeout;

	}

	public Connect(String url, HashMap<String, String> data) {
		super();
		this.url = url;
		this.data = data;
		method = this.META_GET;
	}

	public Connect(String url, HashMap<String, String> data, String method) {
		super();
		this.url = url;
		this.data = data;
		this.method = method;

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public HashMap<String, String> getData() {
		return data;
	}

	public void setData(HashMap<String, String> data) {
		this.data = data;
	}

	public String geturl() {
		return url;
	}

	public void seturl(String url) {
		this.url = url;
	}

	@Override
	public String execute(Bot bot) throws IOException {
		if (method.equals(Connect.META_GET))
			bot.execute(bot.connect(url).method(Connection.Method.GET).data(data).timeout(timeout*1000));
		else
			bot.execute(bot.connect(url).method(Connection.Method.POST).data(data).timeout(timeout*1000));
		
		return bot.getResponse().parse().text();
		
	}

}
