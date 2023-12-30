package pro.juin.compressor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
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
	public void visit(MethodDeclaration n, Void arg) {
		if (!n.isStatic()) return;
		String name = n.getNameAsString();
		var sm = fullnameToSMethod.get(name);
		if (sm == null) {
			sm = new SMethod(currentClass.name + '.' + name);
			Compressor.staticMethods.add(sm);
			currentClass.staticMethods.add(sm);
		} else {
			sm.synonyms++;
		}
		var fsm = sm;
		n.getBody().ifPresentOrElse(body -> fsm.content += n.getDeclarationAsString() + " " + body.toString(),
				() -> fsm.content += n.getDeclarationAsString());
	}
}
