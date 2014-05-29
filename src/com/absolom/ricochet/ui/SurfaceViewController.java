/*package com.absolom.ricochet.ui;

import android.content.*;
import android.view.*;
import com.absolom.utility.messaging.*;
import com.absolom.ricochet.message.command.*;
import com.absolom.ricochet.message.event.*;

public class SurfaceViewController extends SurfaceView implements IMessageReceiver
{
	MessageBroker m_controller;

	public SurfaceViewController(Context context) 
	{ 
     	super(context);
	}
	
	public MessageResult onCommandReceived(ICommand command)
	{
		return m_controller.onCommandReceived(command);
	}

	public void onEvent(IEvent event)
	{
		m_controller.onEvent(event);
	}

	public void sendEvent(IEvent event)
	{
		m_controller.sendEvent(event);
	}

	public void unregisterAllEventReceivers()
	{
		m_controller.unregisterAllEventReceivers();
	}

	public void registerEventReceiver(Class type, IEventReceiver receiver)
	{
		m_controller.registerEventReceiver(type, receiver);
	}

	public void unregisterEventReceiver(Class type, IEventReceiver receiver)
	{
		m_controller.unregisterEventReceiver(type, receiver);
	}

	public void registerEventReceiver(IEventReceiver receiver)
	{
		m_controller.registerEventReceiver(receiver);
	}

	public void unregisterEventReceiver(IEventReceiver receiver)
	{
		m_controller.unregisterEventReceiver(receiver);
	}

	//public boolean canExecute(ICommand command)
	//{
	//	return m_controller.canExecute(command);
	//}

	public MessageResult sendCommand(ICommand command)
	{
		return m_controller.sendCommand(command);
	}

	public void unregisterAllCommandReceivers()
	{
		m_controller.unregisterAllCommandReceivers();
	}

	public void registerCommandReceiver(Class type, ICommandReceiver receiver)
	{
		m_controller.registerCommandReceiver(type, receiver);
	}

	public void unregisterCommandReceiver(Class type, ICommandReceiver receiver)
	{
		m_controller.unregisterCommandReceiver(type, receiver);
	}

	public void registerCommandReceiver(ICommandReceiver receiver)
	{
		m_controller.registerCommandReceiver(receiver);
	}

	public void unregisterCommandReceiver(ICommandReceiver receiver)
	{
		m_controller.unregisterCommandReceiver(receiver);
	}
}*/
