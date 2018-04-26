/*package hello;

public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}*/
package hello;

import java.util.*;
import java.io.File;
import java.io.IOException;


public class Greeting {

	private  Stack<String> ladder;

	public Greeting(String dictionaryFileName,String w1,String w2) {
		try
		{
			this.ladder = ladder(w1, w2, getDictionary(dictionaryFileName));
		}
		catch(Exception e)
		{
			this.ladder=new Stack<String>();
			this.ladder.push("IOException");
		}
        
    }

	public Set<String> getDictionary(String dictionaryFileName) throws IOException {
		Set<String> dictionary = new HashSet<String>();
		String word;
		File myFile = new File(dictionaryFileName);

		Scanner fin = new Scanner(myFile);
		while (fin.hasNext()) {
			word = fin.next();
			dictionary.add(word);
		}
		fin.close();
		return dictionary;

	}

	public Stack<String> ladder(String w1, String w2, Set<String> dictionary)
	//to create a word ladder from w2 back to w1 with the dictionary
	{
		Set<String> used = new HashSet<String>();
		used.add(w1);
		Queue<Stack<String>> q = new LinkedList<Stack<String>>();
		Stack<String> s = new Stack<String>();
		s.push(w1);
		q.offer(s);
		while (!q.isEmpty()) {
			Stack<String> partial = q.poll();
			//dequeue the partial-ladder stack from the front of the queue
			String presentWord = partial.peek();
			int size = presentWord.length();
			Vector<String> neighbors = new Vector<String>();
			String neighborWord = "";
			for (int i = 0; i < size; ++i) {
				for (char c = 'a'; c <= 'z'; ++c) {
					neighborWord = presentWord;
					StringBuilder sb = new StringBuilder(neighborWord);
					sb.replace(i, i + 1, String.valueOf(c));
					neighborWord = sb.toString();
					//Allow word ladder end-points to be outside the dictionary
					if (neighborWord.equals(w2)) {
						partial.push(w2);
						return partial;
					} else if (dictionary.contains(neighborWord) && (!neighborWord.equals(presentWord)))
						neighbors.add(neighborWord);
				}
			}
			//add
			for (int i = 0; i <= size; ++i) {
				for (char c = 'a'; c <= 'z'; ++c) {
					String str = String.valueOf(c);
					neighborWord = presentWord;
					StringBuilder sb = new StringBuilder(neighborWord);
					sb.insert(i, str);
					neighborWord = sb.toString();
					if (dictionary.contains(neighborWord) && (!neighborWord.equals(presentWord)))
						neighbors.add(neighborWord);

				}
			}
			//remove
			for (int i = 0; i < size; ++i) {
				neighborWord = presentWord;
				neighborWord = neighborWord.substring(0, i) + neighborWord.substring(i + 1);
				if (dictionary.contains(neighborWord) && (!neighborWord.equals(presentWord)))
					neighbors.add(neighborWord);
			}

			for (String neighbor : neighbors) {
				if (!used.contains(neighbor)) {
					if (neighbor.equals(w2)) {
						partial.push(w2);
						return partial;
					} else {
						Stack<String> copy = new Stack<String>();
						copy.addAll(partial);
						copy.push(neighbor);
						used.add(neighbor);
						q.offer(copy);
					}
				}
			}
		}
		return s;
	}

	public Stack<String> getLadder() {
		return ladder;
	}

}

