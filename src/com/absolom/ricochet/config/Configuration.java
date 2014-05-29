package com.absolom.ricochet.config;

public class Configuration implements IConfiguration {

	@Override
	public long getMoveRobotTimeout() {
		return 5000000;
	}

	@Override
	public long getShowSolutionTimeout() {
		return 5000000;
	}

	@Override
	public boolean overrideTurnWithBetterSolution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean allowRevertMove() {
		// TODO Auto-generated method stub
		return false;
	}

}
