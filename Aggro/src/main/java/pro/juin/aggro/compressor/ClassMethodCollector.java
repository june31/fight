package pro.juin.aggro.compressor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassMethodCollector extends VoidVisitorAdapter<Void> {
	
	private Clazz currentClass;
	private Map<String, SMethod> fullnameToSMethod = new LinkedHashMap<>();

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Void arg) {
		if (n.isNestedType()) return; // Ignore inner/anonymous classes
		String name = n.getNameAsString();
		currentClass = new Clazz(name);
		Compressor.nameToClassMap.put(name, currentClass);
		super.visit(n, arg);
	}

	@Override
	public void visit(EnumDeclaration n, Void arg) {
	    if (n.isNestedType()) return; // Ignore inner/anonymous enums
	    String name = n.getNameAsString();
	    currentClass = new Clazz(name);
	    Compressor.nameToClassMap.put(name, currentClass);
	    super.visit(n, arg);
	}
	
	@Override
	public void visit(MethodDeclaration n, Void arg) {
		if (!n.isStatic()) return;
		String name = n.getNameAsString();
		var sm = fullnameToSMethod.get(name);
		if (sm == null) {
			sm = new SMethod(name, currentClass);
			Compressor.staticMethods.add(sm);
			currentClass.staticMethods.add(sm);
		}
		var fsm = sm;
		
		// Manage type parameters. E.g., public static <A> int max(A[] t, ToIntFunction<A> f)
		String withoutTP = n.getDeclarationAsString(); // public static int max(A[] t, ToIntFunction<A> f)
		String end = n.getDeclarationAsString(false, true, true); // int max(A[] t, ToIntFunction<A> f)
		String typeParameters = n.getTypeParameters().isEmpty() ? "" :
	            "<" + n.getTypeParameters().toString().replace("[", "").replace("]", "") + "> "; // <A>
	    String fullSig = withoutTP.substring(0, withoutTP.indexOf(end)) + typeParameters + end;
		
		n.getBody().ifPresentOrElse(body -> fsm.content += fullSig + " " + body.toString(),
				() -> fsm.content += n.getDeclarationAsString());
	}
}
