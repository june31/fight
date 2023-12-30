package pro.juin.aggro.plugins;

import pro.juin.aggro.Platform;

public class CodeChef extends Platform {

	@Override
	public String getName() {
		return "CodeChef";
	}

	@Override
	public String getTargetClassName() {
		return "Main";
	}

	@Override
	protected boolean isLowerCasePrefixCompatible(String lowerCasePrefix) {
		return lowerCasePrefix.equals("cc") || lowerCasePrefix.equals("codechef");
	}
}
