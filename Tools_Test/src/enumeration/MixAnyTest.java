package enumeration;

import java.util.List;

import tools.enumeration.any.MixAny;

public class MixAnyTest {

	public static void main(String[] args) {
		List<String>s = List.of( "A", "B", "C", "D" );
		int i = 0;
		for (var x: new MixAny<String>(s)) {
			System.out.println(x);
			i++;
		}
		System.out.println(i);
	}
}
