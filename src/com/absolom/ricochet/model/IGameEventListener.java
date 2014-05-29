package com.absolom.ricochet.model;

import com.absolom.ricochet.model.common.MoveErrorType;

public interface IGameEventListener {
	public void onFailedShowSolution(PlayerId playerId, MoveErrorType reason);

	public void onNewSolutionShowing(PlayerId playerId);

}
