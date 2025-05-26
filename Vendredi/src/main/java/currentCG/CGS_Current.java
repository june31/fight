package currentCG;

import tools.collections.multi.Ldd;
import tools.scanner.Scan;
import tools.scanner.list.ScanLdd;
import tools.strings.S;
import tools.tuple.DD;

public class CGS_Current {
    public static void main(String args[]) {
    	double s = Scan.readDouble();
    	double h = Scan.readDouble();
    	double flow = Scan.readDouble() * 1000 / 60;
    	Ldd l = ScanLdd.read().mapped(dd -> new DD(dd.a, dd.b * 1000 / 60)).sortedUp();
    	
    	double t = 0;
    	double z = 0;
    	for (DD dd : l) {
			t += s * (dd.a - z) / flow;
			flow -= dd.b;
			z = dd.a;
			if (flow <= 0) {
				S.o("Impossible, " + ((int) z) + " cm.");
				return;
			}
		}

    	t += s * (h - z) / flow;
    	String hours = ("" + (100 + ((int) t) / 3600)).substring(1);
    	String mins = ("" + (100 + (((int) t) % 3600) / 60)).substring(1);;
    	String secs = ("" + (100 + (((int) t) % 60))).substring(1); 
    	S.o(hours + ":" + mins + ":" + secs);
    }
}
