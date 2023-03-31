package pro.juin.aggro.plugins;

import pro.juin.aggro.Platform;

public class CodinGamePlayer extends Platform {

	@Override
	public String getName() {
		return "CodinGame (Player)";
	}

	@Override
	public String getTargetClassName() {
		return "Player";
	}

	@Override
	protected boolean isLowerCasePrefixCompatible(String lowerCasePrefix) {
		return lowerCasePrefix.equals("p") || lowerCasePrefix.equals("cgp");
	}
}
