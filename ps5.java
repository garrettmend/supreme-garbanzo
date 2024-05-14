import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class ps5 {
	public static void addEdges(Graph i, Graph l, Graph r, Graph u, Graph d, ArrayList<ArrayList<Graph>> v) {// l=i-1,
		// r=i+1,
		// u =
		// i-n+1,
		// d= i
		// + n+1
		v.get(i.num).add(l);
		v.get(i.num).add(r);
		v.get(i.num).add(u);
		v.get(i.num).add(d);
		// adjacency list build here, PASS IN THE ADJACENCY LIST
	}

	// bag = stack, make vertices next to traps null when reach them and go to
	// parent, count for gold, set vertex null and unmark every vertex and push prev
	// back to bag when trap in edge
	public static int WFS(Graph s, ArrayList<ArrayList<Graph>> ver, ArrayList<Graph> g) {
		Stack<Graph> bag = new Stack<Graph>();
		Graph prev = null;
		Graph v = null;
		boolean t = false;
		boolean ran = false;
		int goldCount = 0;
		bag.push(s);
		while (bag.size() != 0) {
			t = false;
			if (ran) {// check if deleted
				ran = false;
				if (!v.deleted)
					prev = v;
			}
			v = bag.pop();
			if (v.gold) {
				goldCount++;
				v.gold = false;
			}
			if (!v.marked) {
				ran = true;
				v.mark();
				for (Graph w : ver.get(v.num)) {
					if (w == null || w.deleted)
						continue;
					if (w.trap) {
						t = true;
						if (prev == null) {
							return goldCount;

						}
						g.set(v.num, null);
						ver.set(v.num, null);
						v.deleted = true;
						for (Graph vert : g) {
							if (vert != null)
								if (vert.marked) {
									vert.unmark();
								}
						}
						bag.clear();
						bag.push(prev);
						break;

					}
				}
				if (!t) {
					for (Graph w : ver.get(v.num)) {
						if (w == null || w.deleted)
							continue;
						bag.push(w);
					}
				}
			}
		}

		return goldCount;
	}

	public static void main(String[] args) {
		// read in input as array, convert every index into graph and put in new graph
		// list, then call add on that list
		ArrayList<Graph> g = new ArrayList<Graph>();
		ArrayList<String> input = new ArrayList<String>();
		Scanner in = new Scanner(System.in);
		int count = 1;
		String s = in.nextLine();
		String[] split = s.split(" ");
		while (in.hasNextLine()) {
			String n = in.nextLine();
			input.add(n);
			count++;
			if (count == Integer.parseInt(split[1]) + 1)
				break;
		}
		// g.add(new Graph(n));
		char[] cArray = input.toString().toCharArray();
		ArrayList<Character> list = new ArrayList<Character>();
		for (int i = 1; i <= Integer.parseInt(split[0]); i++) {
			// list.add(null);
		}
		for (int i = 1; i < cArray.length; i++) {
			if ((cArray[i] == ',') || (cArray[i] == ']')) {
				continue;
			}
			if (cArray[i] == ' ') {
				list.add(null);
				continue;
			}
			if (cArray[i] == '#') {
				list.add(null);
				continue;
			}

			list.add(cArray[i]);
		}
		for (int i = 0; i <= Integer.parseInt(split[0]); i++) {
			g.add(null);
		}
		int count2 = 1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				g.add(null);
				continue;
			}
			g.add(new Graph(list.get(i), count2));
			count2++;
		}
		for (int i = 0; i <= Integer.parseInt(split[0]); i++) {
			g.add(null);
		}
		////////// connect edges/////////////////
		ArrayList<ArrayList<Graph>> vertices = new ArrayList<ArrayList<Graph>>();
		ArrayList<Graph> graph = new ArrayList<Graph>();
		for (int i = 0; i < count2; i++) {
			vertices.add(new ArrayList<Graph>());
		}
		graph.add(null);
		for (int i = 0; i < g.size(); i++) {
			if (g.get(i) != null)
				graph.add(g.get(i));
		}
		int start = 0;
		for (int i = 0; i < g.size(); i++) {
			if (g.get(i) == null)
				continue;
			addEdges(g.get(i), g.get(i - 1), g.get(i + 1), g.get(i - (Integer.parseInt(split[0]) + 1)),
					g.get(i + (Integer.parseInt(split[0]) + 1)), vertices);
			if (g.get(i).player) {
				start = g.get(i).num;
			}
		}
		// System.out.println(vertices.size());
//		vertices.get(1).get(1).mark();
		////// WFS/////////////

		System.out.println(WFS(graph.get(start), vertices, graph));
	}

}

class Graph {
	boolean trap;
	boolean marked = false;
	boolean gold;
	boolean wall;
	boolean floor;
	boolean player;
	boolean deleted = false;
	Graph parent;
	int num;

	public Graph() {

	}

	public Graph(char c, int i) {
		if (c == '#') {
			wall = true;
		} else if (c == 'T') {
			trap = true;
		} else if (c == 'P') {
			player = true;
		} else if (c == '.') {
			floor = true;
		} else if (c == 'G') {
			gold = true;
		}
		num = i;
	}

	public void setParent(Graph parent_) {
		parent = parent_;
	}

	public Graph getParent() {
		return parent;
	}

	public void mark() {
		marked = true;
	}

	public void unmark() {
		marked = false;
	}
}
