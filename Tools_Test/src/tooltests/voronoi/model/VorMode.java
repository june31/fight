package tooltests.voronoi.model;

public enum VorMode {
	
	// A.. -> Aaa
	// .B.    aBb
	SEQUENTIAL,
	
	// A.. -> Axb
	// .B.    xBb
	SYNC_BLOCK,
	
	// A.. -> Axx
	// .B.    xBb
    SYNC_FLUID
}
