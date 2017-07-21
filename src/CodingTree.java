import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Takes a string and generates a map of codes using Huffman's encoding procedure.
 * Then converts string into string of the codes.
 * 
 * @author Pamaldeep Dhillon
 * @version 1.0
 */

public class CodingTree {
	
	public Map<Character, String> codes;
	
	public String bits;
	
	public CodingTree(String theMessage) {
		int[] charFreq = countCharFreq(theMessage);
		PriorityQueue<HuffmanTree> pQueue = createTrees(charFreq);
		HuffmanTree hTree = createFullTree(pQueue);
		codes = new HashMap<Character, String>();
		createCodeMap(hTree.root);
		bits = encodeMessage(theMessage);
	}
	
	private int[] countCharFreq(String theString) {
		int[] freq = new int[256];
		for (int i = 0; i < theString.length(); i++) {
			int idx = (int) theString.charAt(i);
			freq[idx]++;
		}
		return freq;
	}
	
	private PriorityQueue<HuffmanTree> createTrees(int[] theCharFreq) {
		PriorityQueue<HuffmanTree> tQueue = new PriorityQueue<HuffmanTree>();
		for (int i = 0; i < theCharFreq.length; i++) {
			if (theCharFreq[i] > 0) {
				tQueue.offer(new HuffmanTree((char) i, theCharFreq[i]));
			}
		}
		return tQueue;
	}
	
	private HuffmanTree createFullTree(PriorityQueue<HuffmanTree> theQueue) {
		while (theQueue.size() > 1) {
			HuffmanTree tree1 = theQueue.remove();
		    HuffmanTree tree2 = theQueue.remove();
		    theQueue.add(new HuffmanTree(tree1, tree2));
		}
		return theQueue.remove();
	}
	
	private void createCodeMap(HuffmanTree.Node theRoot) {
		if (theRoot.left != null) {
			theRoot.left.code = theRoot.code + '0';
			createCodeMap(theRoot.left);
			theRoot.right.code = theRoot.code + '1';
			createCodeMap(theRoot.right);
		} else {
			codes.put(theRoot.character, theRoot.code);
		}
	}
	
	private String encodeMessage(String theMessage) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < theMessage.length(); i++) {
			char c = theMessage.charAt(i);
			sb.append(codes.get(c));
		}
		return sb.toString();
	}
	
	private class HuffmanTree implements Comparable<HuffmanTree> {
		
		private Node root;
		
		public HuffmanTree(char theChar, int theWeight) {
			root = new Node(theChar, theWeight);
		}
		
		public HuffmanTree(HuffmanTree tree1, HuffmanTree tree2) {
			root = new Node();
			root.left = tree1.root;
			root.right = tree2.root;
			root.weight = tree1.root.weight + tree2.root.weight;
		}
		
		private class Node {
			private char character;
			private int weight;
			private Node left;
			private Node right;
			private String code = "";
			
			public Node() {
			}
			
			public Node(char theChar, int theWeight) {
				character = theChar;
				weight = theWeight;
			}
		}

		@Override
		public int compareTo(HuffmanTree theOther) {
			int result = 0;
			if (root.weight < theOther.root.weight) {
				result = -1;
			} else if (root.weight > theOther.root.weight) {
				result = 1;
			}
			return result;
		}
	}
}
