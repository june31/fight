package currentCG;

import tools.scanner.Scan;
import tools.strings.S;

public class CGS_Current {
    public static void main(String args[]) {
    	long a = Scan.readLong();
    	long c = Scan.readLong();
    	long m = Scan.readLong();
    	long seed = Scan.readLong();
    	long steps = Scan.readLong();

    	long z = seed;
    	for (int i = 0; i < steps; i++) {
			z = (a * z + c) % m;
		}
    	S.o(a*a, a*c + c, z);

    	
        long az = 1, cz = 0;
        for (int i = 0; i < 64; i++) {
        	if ((steps & 1l << i) != 0) {
        		az = a * az;
        		cz = a * cz + c;
        	}
        	c = a * c + c;
        	a = a * a;
        }
        S.o(az, cz, (az * seed + cz) % m);
    }
}
