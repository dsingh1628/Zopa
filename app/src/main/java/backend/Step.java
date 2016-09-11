package backend;

import java.util.HashMap;

public class Step {
	
	
	private String name;
	private String command;
	private boolean printble = false;
	private int sleep = 0;
	private HashMap<String, Object> arguments;
	
	public Step() {
		
	}
	
	public Step(String name, String command, HashMap<String, Object> arguments,boolean printble,int sleep) {
		super();
		this.name = name;
		this.command = command;
		this.arguments = arguments;
		this.printble = printble;
		this.sleep = sleep;
		
	}
	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	public boolean getPrintble() {
		return printble;
	}

	public void setPrintble(boolean printble) {
		this.printble = printble;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public HashMap<String, Object> getArguments() {
		return arguments;
	}
	public void setArguments(HashMap<String, Object> arguments) {
		this.arguments = arguments;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name: "+ name
				+"\ncommand "+ command
				+"\nprintble "+ printble
				+"\nsleep "+ sleep
				+"\narguments: "+ arguments;
	}
	
	
	
	
	
	
	

}
