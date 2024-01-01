package pro.juin.aggro.compressor;

public abstract class Entity {
	protected String name;
	protected String altName = null;
	protected String simpleName = null;
	protected String content = "";
	protected boolean valid = false;
	public abstract Clazz getClazz();
	public String toString() { return name; }
}
