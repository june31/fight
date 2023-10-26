package summer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

	
// UP    : xx xx 00 +1
// DOWN  : xx xx 00 -1
// LEFT  : xx xx -1 00
// RIGHT : xx xx +1 00

class CGS_4 {

    public static void main(String args[]) {
    	Map<String, String> map = new HashMap<>();
    	
    	// 1
    	map.put("37 21 37 27 16 29",
    			"DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
    	// 2
    	map.put("6 10 16 10 84 35 84 42 11 38",
    			"DDDDDUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
    	// 3
    	map.put("-42 191 184 -15 184 -19 185 -19 186 -15 -42 190 92 93",
    			"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
    	// 4
    	map.put("42 34 33 118 -3 178 151 29 42 59 0 178 106 29 33 65 149 87",
    			"LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLRDDDDDLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
    	// 5
    	map.put("100 108 114 64 114 63 50 61 74 68 76 68 58 108 50 64 69 92",
                "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
    	// 6
    	map.put("184 -20 184 -22 185 -27 185 -28 158 166 -17 -18 -17 -20 160 154 160 -32 -30 166 93 105",
                "LLLLLLLLLLLLLLLLLLLDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDUUULLLLLLLLUUULUUUUULLLLL");
    	// 7
    	map.put("-126 -107 -111 249 227 -83 265 260 265 263 235 245 251 233 236 245 227 229 -110 244 251 236 -110 240 -126 257 -111 -99 85 43",
				"UUUUUUUUUUUUUUUUUUUUUUUUUUUURLRLRRRRRRRRRRRRRRRLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLRRRRRRRRRRRRRRRRRRRRRRRRLLLLLLLLLLLLLLRRRRRRRRLLLLRRRRRRRRRRRRRRUUUUUULLLLLUUURRLLRRRRR");
    	// 8
    	map.put("190 -29 190 165 190 163 80 97",
				"RRRRRRRRRDDDDDDDDDDDDDDDDDDDDDDDRDDDDDDDDDDDDDDDDDRRRRRRRUUUUUURRRRRRUUUUULLLLLLLLLLLRLULLULDDDULLLL");
    	// 9
    	map.put("193 -26 193 -25 91 125 70 125 193 167 74 131",
		        "RRRUUURUUUUUUDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDUUUUDRRURRDRDLLLLLL");
    	// 10
    	map.put("56 111 18 51 106 51 51 95 97 111 18 120 51 59 52 51 51 58 77 88 55 111 75 88 71 79",
		        "RRRURRRRRRDRRUDLLLLDULURRUULLLLLRURURRR");

    	try (Scanner in = new Scanner(System.in)) {
			List<Character> cs = new ArrayList<>();
			int turn = 0;
			// game loop
			while (true) {
			    String s = in.nextLine();
			    StringBuilder sb = new StringBuilder();
			    boolean sw = false;
			    int x = 65;
				if (turn == 0 && map.get(s) == null) System.err.println(s);
			    for (var t : s.split(" ")) {
			        sb.append(sw ? "/" : " " + (char) x++);
			        sw ^= true;
			        sb.append(t);
			    }
			    String ss = sb.toString();
			    if (cs.isEmpty()) {
			    	for (char c: map.get(s).toCharArray()) {
						cs.add(c); 
			    	}
			    }
				System.err.println(cs.get(turn));
			    System.err.println(ss.substring(0, ss.lastIndexOf(' ')));
			    System.err.println(" X" + ss.substring(ss.lastIndexOf(' ')+2));
			    switch (cs.get(turn++)) {
			    case 'U' : System.out.println("RB"); break;
			    case 'D' : System.out.println("KLUO"); break;
			    case 'L' : System.out.println("EQXF"); break;
			    case 'R' : System.out.println("TYHPF"); break;
			    default : System.out.println("?");
			    }
			}
		}
    }
}