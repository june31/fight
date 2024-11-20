package currentCG;

import tools.collections.map.Mssi;
import tools.collections.multi.LLs;
import tools.collections.string.Ls;
import tools.collections.string.Ss;
import tools.scanner.Scan;
import tools.strings.S;

public class CGS_Current {
	private static final String[] DATA = """
			English                                           
			Danish          q    z           æ å  ø                      
			Estonian    cf  q wxy        šž         õä  öü               
			Finnish    b f  q wx                     ä  ö                
			French                     ç      œ       ëï üàè  ùâêîôû é   
			German                  ß                ä  öü               
			Irish         jkqvwxyz                                  áéíóú
			Italian       jk  wxy                         àèìòù      é   
			Portuguese     k  w        ç           ãõ     à    âê ô áéíóú
			Spanish        k  w       ñ                  ü          áéíóú
			Swedish         q w                å     ä  ö                
			Turkish         q wx     ğ çş       İı      öü               
			Welsh         jkqv x z         ŵŷ                  âêîôû     
			""".split("\n");

	public static void main(String[] args) {
		Mssi possible = new Mssi();
		LLs valid = new LLs();
		for (String line : DATA) {
			line += "                                            ";
			String country = line.substring(0, 10).strip();
			for (int c = 'a'; c <= 'z'; c++) possible.add(country, c);
			for (char c : line.substring(10, 23).toCharArray()) if (c != ' ') possible.remove(country, c);
			for (char c : line.substring(23).toCharArray()) if (c != ' ') possible.add(country, c);
		}
		for (int z = 0; z < 13; z++) {
			Ss possibleCountries = new Ss(possible.keySet());
			for (char c: Scan.readLine().toCharArray()) {
				if (!Character.isLetter(c)) continue;
				c = Character.toLowerCase(c);
				int fc = c;
				possibleCountries.removeIf(country -> !possible.get(country).contains(fc));
			}
			valid.add(new Ls(possibleCountries));
		}
		
		Ls unique = dfs(new Ls(), valid);
		S.o(unique.join("\n"));
	}

	private static Ls dfs(Ls choice, LLs valid) {
		if (valid.isEmpty()) return choice;
		LLs copy = valid.deepCopy();
		for (String s: copy.remove(0)) {
			LLs copy2 = copy.deepCopy();
			for (Ls ls: copy2) ls.remove(s);
			Ls choice2 = new Ls(choice);
			choice2.add(s);
			Ls res = dfs(choice2, copy2);
			if (res != null) return res;
		}
		return null;
	}
}
