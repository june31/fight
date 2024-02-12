package tools.math.list;

import java.util.Map;

import tools.collections.string.Ls;
import tools.math.Num;

public class NumLs {
	
    public static Ls min(Map<String, Integer> m) {
    	int min = Num.min(m.values(), v -> v).o;
    	Ls l = new Ls();
    	for (Map.Entry<String, Integer> e: m.entrySet()) if (e.getValue() == min) l.add(e.getKey());
    	return l;
    }
    
    public static Ls max(Map<String, Integer> m) {
    	int max = Num.max(m.values(), v -> v).o;
    	Ls l = new Ls();
    	for (Map.Entry<String, Integer> e: m.entrySet()) if (e.getValue() == max) l.add(e.getKey());
    	return l;
    }
}
