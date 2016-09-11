package backend;

import java.io.IOException;

public class StepExecutor {

	@SuppressWarnings("unchecked")
	static String  execute(Bot bot, Step step) throws IOException, InterruptedException {

		Command command = null;
		
		if (step.getCommand().toLowerCase().equals(Command.CONNECT)) {
			command = new Connect(step);
			return command.execute(bot);
		}
		if (step.getCommand().toLowerCase().equals(Command.CHECKSTRING)) {
			command = new CheckString(step);
			return command.execute(bot);			
		}

		Thread.sleep(step.getSleep()*1000);
		
		return null;

	}

}
