package com.absolom.ricochet.model;

import java.util.Iterator;
import java.util.LinkedList;

import com.absolom.ricochet.config.IConfiguration;
import com.absolom.ricochet.model.common.GameColor;
import com.absolom.ricochet.model.common.MoveErrorType;

public class PlayerSolutionManager {
	protected LinkedList<PlayerSolution> m_playerSolutions;

	IConfiguration m_configuration;
	TileCoordinates m_targetCoordinates;
	GameColor m_targetColor;
	EntityManager m_entityManager;

	private IConfiguration getConfig() {
		return m_configuration;
	}

	public PlayerSolutionManager(EntityManager entityManager, IConfiguration configuration) {
		m_entityManager = entityManager;
		m_configuration = configuration;
		m_playerSolutions = new LinkedList<PlayerSolution>();
	}

	public void clear() {
		m_playerSolutions.clear();
		m_targetCoordinates = null;
		m_targetColor = null;
	}

	public void setTargetInfo(TileCoordinates targetCoordinates, GameColor targetColor) {
		m_targetCoordinates = targetCoordinates;
		m_targetColor = targetColor;
	}

	public void setFailedSolution(PlayerId playerId, MoveErrorType reason) {
		PlayerSolution solution = getPlayerSolution(playerId);

		if (solution == null) {
			return;
		}

		solution.setFailed(reason);
	}

	public PlayerSolution addMove(PlayerId playerId, RobotMove robotMove) {
		PlayerSolution solution = getPlayerSolution(playerId);

		if (solution == null) {
			// Player didn't claimed any solutions
			return null;
		}

		if (solution.isRevertMove(robotMove) && getConfig().allowRevertMove()) {
			solution.removeLastMove();
		} else {
			solution.addMove(robotMove);
		}

		updateSolutionState(solution);
		return solution;
	}

	private void updateSolutionState(PlayerSolution solution) {
		if (hasBustedMoveCount(solution.getPlayerId())) {
			if (!getConfig().allowRevertMove()) {
				solution.setFailed(MoveErrorType.BustedMoveCount);
				return;
			}
		}

		if (hasReachedMoveCount(solution.getPlayerId()) && isSolutionCompleted(solution.getLastMove())) {
			solution.setSucceeded();
			return;
		}
	}

	private boolean isSolutionCompleted(RobotMove move) {
		Robot robot = m_entityManager.getEntity(move.getRobotId());

		if (!robot.getColor().equals(m_targetColor)) {
			return false;
		}

		if (!move.getPath().getLastWayPoint().equals(m_targetCoordinates)) {
			return false;
		}

		return true;
	}

	public boolean hasBustedMoveCount(PlayerId playerId) {
		PlayerSolution solution = getPlayerSolution(playerId);

		if (solution == null) {
			return false;
		}

		return solution.hasBustedMoveCount();
	}

	public boolean hasReachedMoveCount(PlayerId playerId) {
		PlayerSolution solution = getPlayerSolution(playerId);

		if (solution == null) {
			return false;
		}

		return solution.hasReachedMoveCount();
	}

	public boolean hasClaimedSolution(PlayerId playerId) {
		return getPlayerSolution(playerId) != null;
	}

	public PlayerSolution getPlayerSolution(PlayerId playerId) {
		for (PlayerSolution solution: m_playerSolutions) {
			if (solution.getPlayerId().equals(playerId)) {
				return solution;
			}
		}

		return null;
	}

	public boolean addClaimedSolution(PlayerId playerId, int claimedMoveCount) {
		if (hasClaimedSolution(playerId)) {
			return false;
		}

		int index = 0;
		Iterator<PlayerSolution> itr = m_playerSolutions.iterator();
		while (itr.hasNext()) {
			PlayerSolution playerSolution = itr.next();

			if (claimedMoveCount < playerSolution.getClaimedMoveCount()) {
				break;
			}
			index++;
		}

		m_playerSolutions.add(index, new PlayerSolution(playerId, claimedMoveCount));

		return true;
	}

	public void removeClaimedSolutionFrom(PlayerId playerId) {
		int index = 0;
		Iterator<PlayerSolution> itr = m_playerSolutions.iterator();
		while (itr.hasNext()) {
			PlayerSolution existingClaimedSolution = itr.next();

			if (existingClaimedSolution.getPlayerId().equals(playerId)) {
				m_playerSolutions.remove(index);
				break;
			}
			index++;
		}
	}

	public PlayerClaimedSolution getBestClaimedUnvalidatedSolution() {
		Iterator<PlayerSolution> itr = m_playerSolutions.iterator();
		while (itr.hasNext()) {
			PlayerSolution solution = itr.next();

			if (!solution.isValidated()) {
				return solution.getClaimedSolution();
			}
		}

		return null;
	}
}
