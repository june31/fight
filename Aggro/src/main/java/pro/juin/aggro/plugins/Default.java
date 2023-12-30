package pro.juin.aggro.plugins;

import pro.juin.aggro.Platform;

public class Default extends Platform {
	@Override
	public String getName() {
		return "Default";
	}

	@Override
	public String getTargetClassName() {
		return "Solution";
	}

	@Override
	protected boolean isLowerCasePrefixCompatible(String lowerCasePrefix) {
		return lowerCasePrefix.isEmpty();
	}
}
