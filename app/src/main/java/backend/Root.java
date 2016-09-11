package backend;

import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Root {

	/*public static void main(String[] args) throws Exception {

		login("");

	}*/

	public  static String login(String jsonCode) throws Exception {
		Bot bot = new Bot();

		JSONObject json = (JSONObject) (new JSONParser()).parse(jsonCode);

		JSONArray jsonSteps = (JSONArray) json.get("steps");

		Step[] steps = new Step[jsonSteps.size()];

		parseSteps(jsonSteps, steps);

		// System.out.println(steps.length);

		String ret = "";
		for (Step step : steps) {
			 //System.out.println(step);
			if (step.getPrintble())
				System.out.println(StepExecutor.execute(bot, step));
			else {
				ret = StepExecutor.execute(bot, step);
			}
		}

		return  ret;
	}

	private static void parseSteps(JSONArray jsonSteps, Step[] steps) {
		for (int i = 0; i < jsonSteps.size(); i++) {
			JSONObject jsonStep = (JSONObject) jsonSteps.get(i);
			Step step = new Step();
			parseStep(jsonStep, step);
			steps[i] = step;
		}
	}

	private static void parseStep(JSONObject jsonStep, Step step) {

		// System.out.println("\n\ntoJSONString: "+jsonStep.toJSONString());

		String name = (String) jsonStep.get("name");
		String command = (String) jsonStep.get("command");
		Object printble = jsonStep.get("printble");
		Object sleep = jsonStep.get("sleep");
		JSONObject jsonArguments = (JSONObject) jsonStep.get("arguments");

		step.setName(name);
		step.setCommand(command);
		step.setPrintble(printble != null && ((String) printble).toLowerCase().trim().equals("true"));
		step.setSleep( sleep!=null ? Integer.parseInt((String)sleep):0);
		
		Set<String> keys = jsonArguments.keySet();
		HashMap<String, Object> arguments = new HashMap<>();
		for (String key : keys) {
			Object argument = jsonArguments.get(key);
			// System.out.println("argument: "+ jsonStep);
			arguments.put(key.toLowerCase(), parse(argument));
		}
		step.setArguments(arguments);
	}

	private static void parseArray(JSONArray jsonSteps) {
		for (int i = 0; i < jsonSteps.size(); i++) {
			JSONObject jsonStep = (JSONObject) jsonSteps.get(i);
			parseObject(jsonStep);
		}
	}

	private static HashMap<String, Object> parseObject(JSONObject jsonStep) {
		Set<String> keys = jsonStep.keySet();

		

		HashMap<String, Object> arguments = new HashMap<>();
		for (String key : keys) {
			Object argument = jsonStep.get(key);
			arguments.put(key, parse(argument));
		}
		return arguments;
	}

	private static Object parse(Object argument) {
		if (argument instanceof String) {
			// System.out.println(" String:"+argument);
			return (String) argument;
		} else if (argument instanceof JSONObject) {
			// System.out.println(" JSONObject:"+argument);
			return parseObject((JSONObject) argument);
		}
		/*
		 * else if( argument instanceof JSONArray ){ //System.out.println(
		 * " JSONArray:"+argument); return parseArray((JSONArray)argument); }
		 */
		return null;
	}

}
