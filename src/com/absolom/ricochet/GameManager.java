package com.absolom.ricochet;

import java.util.EnumSet;



import com.absolom.ricochet.model.*;
import com.absolom.ricochet.model.common.GameColor;
import com.absolom.utility.common.Direction;
import com.absolom.utility.common.WorkScheduler;
import com.absolom.utility.common.WorkerThread;
import com.absolom.utility.data.CachedDataAccess;
import com.absolom.utility.data.DataAccessObserver;
import com.absolom.utility.data.IDataAccess;
import com.absolom.utility.data.NullDataAccess;
import com.absolom.utility.messaging.CommsAgent;
import com.absolom.utility.messaging.IConnectionInfo;
import com.absolom.utility.messaging.MessageTransceiver;


/**
 * 
 *
 */
public class GameManager {
	private EntityManager m_entityManager;
	@SuppressWarnings("unused")
	private ModelAgent m_modelAgent;
	private CommsAgent m_commsAgent;
	private MessageTransceiver m_messageTransceiver;
	private WorkerThread m_workerThread;
	private WorkScheduler m_workScheduler;

	// @SuppressWarnings("unused")
	// private IViewAgent m_viewAgent;

	public GameManager(WorkerThread workerThread, WorkScheduler scheduler, MessageTransceiver messageTransceiver) {
		m_workerThread = workerThread;
		m_workScheduler = scheduler;
		
		m_messageTransceiver = messageTransceiver;

		// registerMessageReceiver(HostGameCommand.class, onHostingGame);
		// registerMessageReceiver(JoinGameCommand.class, onJoiningGame);

		// m_viewAgent = viewAgent;
	}

	// private IMessageHandler onHostingGame = new
	// MessageHandler<HostGameCommand>(this) {
	// public @Override
	// IMessageResult onMessage(HostGameCommand command) {
	// IDataAccess dataAccess = new CachedDataAccess(new DataAccessObserver(new
	// NullDataAccess(), GameManager.this));
	// m_entityManager = new EntityManager(dataAccess);
	//
	// m_modelAgent = new ModelAgent(getMessageBus(), createNewGame(command));
	//
	// if (command.isRemoteJoinAccepted()) {
	// m_commsAgent = new CommsAgent(getMessageBus());
	//
	// if (command.isTcpAccepted())
	// m_commsAgent.startAcceptingTcpConnections();
	//
	// if (command.isBluetoothAccepted())
	// m_commsAgent.startAcceptingBluetoothConnections();
	// }
	//
	// return MessageResult.success();
	// }
	// };

	public void hostGame() {
		IDataAccess dataAccess = new CachedDataAccess(new DataAccessObserver(new NullDataAccess(), m_messageTransceiver));
		m_entityManager = new EntityManager(dataAccess);

		m_modelAgent = new ModelAgent(m_messageTransceiver, createNewGame());

		// if (command.isRemoteJoinAccepted()) {
		// m_commsAgent = new CommsAgent(getMessageBus());
		//
		// if (command.isTcpAccepted())
		// m_commsAgent.startAcceptingTcpConnections();
		//
		// if (command.isBluetoothAccepted())
		// m_commsAgent.startAcceptingBluetoothConnections();
		// }

	}

	public void joinGame(IConnectionInfo connectionInfo) {
		IDataAccess dataAccess = new CachedDataAccess(new DataAccessQuerier(m_messageTransceiver));
		m_entityManager = new EntityManager(dataAccess);

		m_commsAgent.connectToGameServer(connectionInfo);
	}

	// private IMessageHandler onJoiningGame = new
	// MessageHandler<JoinGameCommand>(this) {
	// public @Override
	// IMessageResult onMessage(JoinGameCommand command) {
	// IDataAccess dataAccess = new CachedDataAccess(new
	// DataAccessQuerier(GameManager.this));
	// m_entityManager = new EntityManager(dataAccess);
	//
	// m_commsAgent.connectToGameServer(command.getConnectionInfo());
	// // TODO: Return result from connection
	// return MessageResult.success();
	// }
	// };

	private Game createNewGame() {
		Game game = new Game(m_workerThread, m_workScheduler, m_entityManager, m_messageTransceiver);
		GameBoard gameBoard = loadGameBoard();
		m_entityManager.setBoard(gameBoard);
		return game;
	}

	public GameBoard loadGameBoard() {

		GameBoard gameBoard = new GameBoard(16, 16);
		Robot robot1 = m_entityManager.createEntity(RobotId.generate());
		robot1.setColor(GameColor.Blue);
		gameBoard.setItemOnTile(robot1, 2, 2);

//		Robot robot2 = m_entityManager.createEntity(RobotId.generate());
//		robot2.setColor(GameColor.Red);
//		gameBoard.setItemOnTile(robot2, 4, 6);
//		
		Target target1 = m_entityManager.createEntity(TargetId.generate());
		target1.setColor(GameColor.Blue);
		gameBoard.setItemOnTile(target1, 3, 8);

		Ricochet ricochet1 = m_entityManager.createEntity(RicochetId.generate());
		ricochet1.setColor(GameColor.Red);
		gameBoard.setItemOnTile(ricochet1, 10, 12);

		gameBoard.setWalled(2, 0, EnumSet.of(Direction.Left));
		gameBoard.setWalled(2, 12, EnumSet.of(Direction.Down));
		gameBoard.setWalled(3, 15, EnumSet.of(Direction.Left));
		
		m_entityManager.applyChanges();

		return gameBoard; // getCurrentGame().setGameBoard(gameBoard);

		// return null;
	}

	public EntityManager getEntityManager() {
		return m_entityManager;
	}
}
