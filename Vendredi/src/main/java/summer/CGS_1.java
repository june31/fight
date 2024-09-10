package summer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tools.Scanner;

public class CGS_1 {
    private static final Gson gson = new Gson();
    public static void trySolution(String mergedFile) {
        System.out.println("\"" + mergedFile + "\"");
    }

    public static void main(String args[]) {
        try (Scanner in = new Scanner(System.in)) {
            trySolution(mergeFiles(
                gson.fromJson(in.nextLine(), new TypeToken<List<String>>(){}.getType())
            ));
        }
    }
    public static String mergeFiles(List<String> fileContents) {
        Comparator<String> comparator = Comparator.comparing(
                (String s) -> s.split("=")[0]
        ).thenComparing(Comparator.naturalOrder());

        Map<String, Set<String>> m = new TreeMap<>(comparator); 
        for (var file : fileContents) {
            for (var s : file.split("\n")) {
            String[] tk = s.split(";");
            //System.err.println("aa " + tk[0]);
            int w = 0;
            for (int i = 0; i < tk.length; i++) {
                if (tk[i].startsWith("Name=")) {
                    w = i;
                }
            }
            var l = m.get(tk[w]);
            if (l == null) {
                l = new TreeSet<>(comparator);
                m.put(tk[w], l);
            } 
            for (int i = 0; i < tk.length; i++) {
                if (i != w) l.add(tk[i]);
            }
            }
        }
        //System.err.println(m);
        StringBuilder sb = new StringBuilder();
        boolean n = false;
        for (var e : m.entrySet()) {
            if (n) sb.append("§");
            n = true;
            sb.append(e.getKey());
            for (var x: e.getValue()) {
                sb.append(";" + x);
            }
        }
        var ss = ("" + sb).replace("\\", "\\\\").replace("§", "\\n");
        //System.err.println(ss.indexOf("00fl6XrONSs1mCTqTy2nYo1EX8Wu4uQ3iXy5RsNybjZ9eHV8QgrSjKl;gotcha="));
        //System.err.println(ss.indexOf("00fl6XrONSs1mCTqTy2nYo1EX8Wu4uQ3iXy5RsNybjZ9eHV8QgrSjKl;gotcha0"));
       
        return ss;
    }
}

