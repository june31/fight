package tooltests.enumeration;

import java.util.List;

import tools.enumeration.any.MixAnyNoOrder;

public class MixAnyNoOrderTest {

	public static void main(String[] args) {
		List<String>s = List.of( "A", "B", "C", "D" );
		for (var x: new MixAnyNoOrder<String>(s)) {
			System.out.println(x);
		}
	}
}
