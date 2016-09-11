package backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Console {

	static BufferedReader console;

	static {
		console = new BufferedReader(new InputStreamReader(System.in));
	}

	static Integer readInteger() throws Exception {
		return Integer.parseInt(readLine());
	}

	static Long readLong() throws Exception {
		return Long.parseLong(readLine());
	}

	static Float readFloat() throws Exception {
		return Float.parseFloat(readLine());
	}

	static Double readDouble() throws Exception {
		return Double.parseDouble(readLine());
	}

	static String readLine() throws Exception {
		return console.readLine();
	}

	static String[] readLine(String del) throws Exception {
		return console.readLine().split(" ");
	}

	static Integer[] readIntegers(String del) throws Exception {
		String[] buffer = readLine().split(del);
		Integer[] carton = new Integer[buffer.length];
		int i = 0;
		for (String pack : buffer) {
			carton[i++] = Integer.parseInt(pack);
		}
		return carton;
	}

	static int[] readInts(String del) throws Exception {
		String[] buffer = readLine().split(del);
		int[] carton = new int[buffer.length];
		int i = 0;
		for (String pack : buffer) {
			carton[i++] = Integer.parseInt(pack);
		}
		return carton;
	}

	static long[] readLongs(String del) throws Exception {
		String[] buffer = readLine().split(del);
		long[] carton = new long[buffer.length];
		int i = 0;
		for (String pack : buffer) {
			carton[i++] = Long.parseLong(pack);
		}
		return carton;
	}

	static float[] readFloats(String del) throws Exception {
		String[] buffer = readLine().split(del);
		float[] carton = new float[buffer.length];
		int i = 0;
		for (String pack : buffer) {
			carton[i++] = Float.parseFloat(pack);
		}
		return carton;
	}

	static double[] readDoubles(String del) throws Exception {
		String[] buffer = readLine().split(del);
		double[] carton = new double[buffer.length];
		int i = 0;
		for (String pack : buffer) {
			carton[i++] = Double.parseDouble(pack);
		}
		return carton;
	}

	static void printArray(Object[] buffer, String del, boolean isForward) {
		int start = isForward ? 0 : buffer.length - 1;
		int end = isForward ? buffer.length : -1;
		int offset = isForward ? 1 : -1;
		for (int i = start; i != end; i += offset) {
			System.out.print(buffer[i] + del);
		}
	}


	static void printArray(Object[] buffer) {
		printArray(buffer,",",true);
	}

	static void printArray(Object[] buffer, String del) {
		printArray(buffer,del,true);
	}

	static void printArray(Object[] buffer, boolean isForward) {
		printArray(buffer,",",isForward);
	}




}
