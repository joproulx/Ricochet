package com.absolom.ricochet.model;

import com.absolom.ricochet.model.common.StateType;
import com.absolom.ricochet.model.state.*;
import com.absolom.utility.common.*;
import com.absolom.utility.messaging.MessageTransceiver;

public class Game {
	private GameStateContext m_context;

	// private HashMap<PlayerId, Player> m_players;

	public Game(WorkerThread workerThread, WorkScheduler workScheduler, EntityManager entityManager, MessageTransceiver messageTransceiver) {
		m_context = new GameStateContext(workerThread, workScheduler, entityManager, messageTransceiver);
		//m_context.gotoState(new WaitingForNewPlayerState());
		m_context.gotoState(new WaitingForNewPlayerState());
		// TODO: Replace with good state
		
		// m_players = new HashMap<PlayerId, Player>();
	}

	public GameStateContext getGameContext() {
		return m_context;
	}

	public State getState() {
		return m_context.getCurrentState();
	}

	public StateType getStateType() {
		return getState().getType();
	}

	public GameBoard getGameBoard() {
		return m_context.getEntityManager().getBoard();
	}

	public void start() {
		// TODO: Implement this method
	}

	/*
	 * public Player getPlayer(PlayerId playerId) { return
	 * m_players.get(playerId); }
	 * 
	 * public List<Player> getAllPlayers() { return new
	 * ArrayList<Player>(m_players.values()); }
	 * 
	 * public List<PlayerId> getAllPlayerIds() { return new
	 * ArrayList<PlayerId>(m_players.keySet()); }
	 */

	// TODO: Should use addNewPlayer from WaitingForNewPlayerState
	/*
	 * public boolean addPlayer(Player player) { if
	 * (m_players.containsKey(player.getId())) { return false; }
	 * m_players.put(player.getId(), player); return true; }
	 * 
	 * public void removePlayer(PlayerId playerId) { m_players.remove(playerId);
	 * onPlayerRemoved(playerId); }
	 */


}
