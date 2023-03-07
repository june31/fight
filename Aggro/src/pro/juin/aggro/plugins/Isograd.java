package pro.juin.aggro.plugins;

import pro.juin.aggro.Platform;

public class Isograd extends Platform {

	@Override
	public String getName() {
		return "Isograd";
	}

	@Override
	public String getTargetClassName() {
		return "IsoContest";
	}

	@Override
	public String getTargetPackageName() {
		return "com.isograd.exercise";
	}
	
	@Override
	protected boolean isLowerCasePrefixCompatible(String lowerCasePrefix) {
		return lowerCasePrefix.equals("iso") || lowerCasePrefix.equals("isograd") || lowerCasePrefix.equals("isocontest");
	}
}
