package spring24;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tools.Scanner;

class CGS_9bis {

    public static String crashDecode(List<String> codes) {
		record Seq(String history, String current) {}
    	Comparator<String> cmp = Comparator.comparing(String::length).thenComparing(String::compareTo);
    	Queue<Seq> workQueue = new PriorityQueue<>((s1, s2) -> cmp.compare(s1.history + s1.current, s2.history + s2.current));
    	workQueue.add(new Seq("", ""));
    	while (!workQueue.isEmpty()) {
    		Seq s = workQueue.poll();
    		if (s.history.length() > 33371) return "X";
    		for (String code: codes) {
    			if (s.current.equals(code))
    				if (s.history.isEmpty()) continue;
    				else return s.history + code;
    			if (code.startsWith(s.current)) workQueue.add(new Seq(s.history + s.current, code.substring(s.current.length())));
    			if (s.current.startsWith(code) && !s.history.isEmpty()) workQueue.add(new Seq(s.history + code, s.current.substring(code.length())));
    		}
    	}
        return "X";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /* Ignore and do not change the code below */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Try a solution
     * @param ambiguousSequence The shortest and smallest possible ambiguous sequence. If no such sequence exists, return "X"
     */
    public static void trySolution(String ambiguousSequence) {
        System.out.println("" + gson.toJson(ambiguousSequence));
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(crashDecode(
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType())
            ));
        }
    }
    /* Ignore and do not change the code above */
}




