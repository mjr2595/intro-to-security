import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Comparator;


public class Huffman {

	private Huffman() {}

	private static class Node {
		char c;
		double weight;
		Node left;
		Node right;


		Node(char c, double frequency, Node left, Node right) {
			this.c = c;
			this.weight = frequency;
			this.left = left;
			this.right = right;
		}
	}

	private static class HuffComparator implements Comparator<Node> {
		@Override
		
		public int compare(Node node1, Node node2) {
			return ((int)node1.weight) - ((int)node2.weight);
		}
	}

	public static Map<Character, String> getEncryptionCodes(Map<Character, Double> charFreq) {
		return generateCodes(charFreq.keySet(), makeTree(charFreq));
	}

	private static Map<Character, String> generateCodes(Set<Character> chars, Node node) {
		final Map<Character, String> result = new HashMap<Character, String>();
		
		generate(node, "", result);
		return result;
	}

	private static void generate(Node node, String s, Map<Character, String> result) {
		if (node.left == null && node.right == null) {
			result.put(node.c, s);
			return;
		}    
		generate(node.left, s + '0', result);
		generate(node.right, s + '1', result);
	}

	private static Node makeTree(Map<Character, Double> map) {
		final Queue<Node> nodeQueue = makeQueue(map);

		while (nodeQueue.size() > 1) {
			final Node node1 = nodeQueue.remove();
			final Node node2 = nodeQueue.remove();
			// create arbitrary node with null char
			Node node = new Node('\0', node1.weight + node2.weight, node1, node2);
			nodeQueue.add(node);
		}
		return nodeQueue.remove();
	}	

	private static Queue<Node> makeQueue(Map<Character, Double> map) {
		final Queue<Node> pq = new PriorityQueue<Node>(10, new HuffComparator());
		
		for (Entry<Character, Double> entry : map.entrySet()) {
			pq.add(new Node(entry.getKey(), entry.getValue(), null, null));
		}
		return pq;
	}
}