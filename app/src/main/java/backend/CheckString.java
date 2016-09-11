package backend;

import java.io.IOException;
import java.util.HashMap;

public class CheckString implements Command {


	public static final String META_LOCATOR = "locator";
	public static final String META_VALUE = "value";
	public static final String META_SUCCESSMESSAGE = "successmessage";
	public static final String META_FAILUREMESSAGE = "failuremessage";
	public static final String META_EXACTLY = "exactly";
	public static final String META_IGNORECASE = "ignorecase";


	private String locator;
	private String value;
	private String successMessage;
	private String failureMessage;	
	private boolean exactly;	
	private boolean ignoreCase;	




	public CheckString(Step step){

		HashMap<String, Object> argumants = step.getArguments();

		this.locator = (String)argumants.get(this.META_LOCATOR);
		this.value = (String)argumants.get(this.META_VALUE);
		this.successMessage = (String)argumants.get(this.META_SUCCESSMESSAGE);		
		this.failureMessage = (String)argumants.get(this.META_FAILUREMESSAGE);
		this.exactly = ((String)argumants.get(this.META_EXACTLY)).toLowerCase().trim().equals("true");
		this.ignoreCase = ((String)argumants.get(this.META_IGNORECASE)).toLowerCase().trim().equals("true");

	}




	@Override
	public String execute(Bot bot) throws IOException {

		String text =  bot.response.parse().select(this.locator).text();	
		text = ignoreCase?text.toLowerCase():text;
		value = ignoreCase?value.toLowerCase():value;
		successMessage = successMessage.startsWith("@") ?	bot.response.parse().select(successMessage.substring(1)).text():successMessage;
		failureMessage = failureMessage.startsWith("@") ?	bot.response.parse().select(failureMessage.substring(1)).text():failureMessage;
		
		return exactly ?text.trim().equals(value) ?successMessage : failureMessage :text.contains(value)? successMessage : failureMessage;


	}

}
