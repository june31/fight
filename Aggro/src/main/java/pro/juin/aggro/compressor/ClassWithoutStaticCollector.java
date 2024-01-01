package pro.juin.aggro.compressor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassWithoutStaticCollector extends VoidVisitorAdapter<Void> {
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        n.getMethods().stream().filter(m -> m.isStatic()).forEach(m -> m.remove());
		String name = n.getNameAsString();
		Clazz clazz = Compressor.nameToClassMap.get(name);
		clazz.content = n.toString();
        super.visit(n, arg);
	}
}
