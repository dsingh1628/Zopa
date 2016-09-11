package backend;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class Bot {
	
	Connection connection;
	Response response;
	
	
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Connection connect(String url){
		connection = Jsoup.connect(url);
		return connection;
	}
	
	public Response getResponse() {
		return response;
	}
	public Response execute(Connection connection) throws IOException{
		
		this.connection = connection;		
		if( response!= null ){
			connection.cookies(response.cookies());
		}		
		
		response = this.connection.execute();
		return response;
	}

	
	

}
