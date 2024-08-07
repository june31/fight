package spring24;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tools.Scanner;
import tools.strings.S;

class CGS_9 {

    /**
     * @param codes The list of binary codes in the table
     * @return The shortest and smallest possible ambiguous sequence. If no such sequence exists, return "X"
     */
	
	static record Z(String history, String current) {}

    public static String crashDecode(List<String> codes) {
    	Queue<Z> lz = new PriorityQueue<>((z1, z2) -> S.stringLengthComparator().compare(z1.history + z1.current, z2.history + z2.current));
    	for (String code: codes) lz.add(new Z("", code));
    	while (!lz.isEmpty()) {
    		Z z = lz.poll();
    		if (z.history.length() > 33371) return "X";
    		for (String code: codes) {
    			if (z.current.equals(code))
    				if (z.history.length() == 0) continue;
    				else return z.history + code;
    			if (code.startsWith(z.current)) lz.add(new Z(z.history + z.current, code.substring(z.current.length())));
    			if (z.current.startsWith(code)) lz.add(new Z(z.history + code, z.current.substring(code.length())));
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




