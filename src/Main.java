import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Driver program for Compressed Literature.
 * 
 * @author Pamaldeep Dhillon
 * @version 1.0
 */

public class Main {

	public static void main(String[] theArgs) {
		long startTime = System.currentTimeMillis();
		InputStream inFile = null;
		InputStreamReader inFileReader = null;
		BufferedReader input = null;
		PrintStream output1 = null;
		PrintStream output2 = null;
		StringBuilder sb = new StringBuilder();
		try {
//			File original = new File("WarAndPeace.txt");
//			inFile = new FileInputStream("WarAndPeace.txt");
			File original = new File("MobyDick.txt");
			inFile = new FileInputStream("MobyDick.txt");
			inFileReader = new InputStreamReader(inFile);
			input = new BufferedReader(inFileReader);
			output1 = new PrintStream(new File("codes.txt"));
			output2 = new PrintStream(new File("compressed.txt"));
			int value = 0;
			while ((value = input.read()) != -1) {
				sb.append((char) value);
			}
			CodingTree tree = new CodingTree(sb.toString());
			for (Character key : tree.codes.keySet()) {
				output1.print(key + "=" + tree.codes.get(key) + ", ");
			}
			for (int i = 0; i < tree.bits.length(); i += 8) {
				String s = new String(tree.bits.substring(i, Math.min(tree.bits.length(), i + 8)));
				int b = Integer.parseInt(s, 2);
				output2.print((char) b);
			}
			inFile.close();
			inFileReader.close();
			input.close();
			output1.close();
			output2.close();
			File result = new File("compressed.txt");
			long oLength = original.length();
			long rLength = result.length();
			System.out.println("Original File Size: " + oLength * 8 + " bits");
			System.out.println("Compressed File Size: " + rLength * 8 + " bits");
			double cRatio = ((double)rLength/oLength) * 100;
			System.out.println(String.format("Compression Ratio: %.2f", cRatio) + "%");
		} catch (FileNotFoundException e) {
			System.out.println("Can't open file - " + e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long stopTime = System.currentTimeMillis();
		System.out.println("Running time: " + (stopTime - startTime) + " milliseconds");
	}

}
