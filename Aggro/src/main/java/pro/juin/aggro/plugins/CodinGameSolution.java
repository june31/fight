package pro.juin.aggro.plugins;

import pro.juin.aggro.Platform;

public class CodinGameSolution extends Platform {

	@Override
	public String getName() {
		return "CodinGame (Solution)";
	}

	@Override
	public String getTargetClassName() {
		return "Solution";
	}

	@Override
	protected boolean isLowerCasePrefixCompatible(String lowerCasePrefix) {
		return lowerCasePrefix.equals("s") || lowerCasePrefix.equals("cgs");
	}
}
