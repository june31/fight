package pro.juin.aggro.compressor;

public class SMethod extends Entity {
	Clazz clazz;
	SMethod(String name, Clazz clazz) {
		this.name = clazz.name + '.' + name;
		this.altName = clazz.name + "::" + name;
		this.simpleName = name;
		this.clazz = clazz; }
	public Clazz getClazz() { return clazz; }
}
