package pro.juin.aggro.plugins;

import pro.juin.aggro.Platform;

public class CodinGame extends Platform {

	@Override
	public String getName() {
		return "CodinGame";
	}

	@Override
	public String getTargetClassName() {
		return "Solution";
	}

	@Override
	protected boolean isLowerCasePrefixCompatible(String lowerCasePrefix) {
		return lowerCasePrefix.equals("cg") || lowerCasePrefix.equals("codingame");
	}
}
