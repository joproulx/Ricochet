package com.absolom.ricochet.config;

public interface IConfiguration {

	public long getMoveRobotTimeout();

	public long getShowSolutionTimeout();

	public boolean overrideTurnWithBetterSolution();

	public boolean allowRevertMove();

}
