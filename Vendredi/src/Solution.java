import java.util.Scanner;

import tools.chrono.Chrono;

class Solution {
    public static void main(String args[]) {
    	Chrono.start();
        //Scanner in = new Scanner(System.in);
        int attack = 96;//in.nextInt();
        int defense = 71;//in.nextInt();
        
        // Single fight odds 
        int win11 = 0, win12 = 0, win21 = 0, win22 = 0, draw22 = 0, win31 = 0, win32 = 0, draw32 = 0;
        for (int a1 = 1; a1 <= 6; a1++) for (int a2 = 1; a2 <= 6; a2++) for (int a3 = 1; a3 <= 6; a3++) for (int d1 = 1; d1 <= 6; d1++) for (int d2 = 1; d2 <= 6; d2++) {
        	int aaa1, aaa2, aa1, aa2, dd1, dd2;
        	if (a1 > a2 && a2 > a3) { aaa1 = a1; aaa2 = a2; }
        	else if (a1 >= a3 && a3 >= a2) { aaa1 = a1; aaa2 = a3; }
        	else if (a2 >= a1 && a1 >= a3) { aaa1 = a2; aaa2 = a1; }
        	else if (a2 >= a3 && a3 >= a1) { aaa1 = a2; aaa2 = a3; }
        	else if (a3 >= a1 && a1 >= a2) { aaa1 = a3; aaa2 = a1; }
        	else { aaa1 = a3; aaa2 = a2; }
        	if (a1 > a2) { aa1 = a1; aa2 = a2; } else { aa1 = a2; aa2 = a1; } 
        	if (d1 > d2) { dd1 = d1; dd2 = d2; } else { dd1 = d2; dd2 = d1; } 

        	if (a1 > d1) win11++;
        	if (a1 > dd1) win12++;
        	if (aa1 > d1) win21++;
        	if (aa1 > dd1 && aa2 > dd2) win22++;
        	if (aa1 > dd1 ^ aa2 > dd2) draw22++;
        	if (aaa1 > d1) win31++;
        	if (aaa1 > dd1 && aaa2 > dd2) win32++;
        	if (aaa1 > dd1 ^ aaa2 > dd2) draw32++;
        }
        double w11 = win11 / 7776d;
        double w12 = win12 / 7776d;
        double l21 = (7776 - win21) / 7776d;
        double w21 = win21 / 7776d;
        double d22 = draw22 / 7776d;
        double w22 = win22 / 7776d;
        double l31 = (7776 - win31) / 7776d;
        double w31 = win31 / 7776d;
        double l32 = (7776 - win32 - draw32) / 7776d;
        double d32 = draw32 / 7776d;
        double w32 = win32 / 7776d;
        
        // Construct odds table
        double[][] odds = new double[attack + 1][defense + 1];
        for (int i = 0; i <= attack; i++) odds[i][0] = 1d;
        for (int a = 1; a <= attack; a++) {
        	for (int d = 1; d <= defense; d++) {
        		if (a >= 3 && d >= 2) odds[a][d] = l32 * odds[a - 2][d] + d32 * odds[a - 1][d - 1] + w32 * odds[a][d - 2];
        		else if (a >= 3) odds[a][d] = l31 * odds[a - 1][d] + w31 * odds[a][d - 1];
        		else if (a == 2 && d >= 2) odds[a][d] = d22 * odds[a - 1][d - 1] + w22 * odds[a][d - 2];
        		else if (a == 2) odds[a][d] = l21 * odds[a - 1][d] + w21 * odds[a][d - 1];
        		else if (d >= 2) odds[a][d] = w12 * odds[a][d - 1];
        		else odds[a][d] = w11;
        	}
        }

        System.out.println((String.format("%.2f", odds[attack][defense] * 100) + "%").replace(',', '.'));
    	Chrono.stop();
    }
}