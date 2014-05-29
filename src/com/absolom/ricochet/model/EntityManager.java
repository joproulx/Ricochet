package com.absolom.ricochet.model;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.absolom.utility.data.Document;
import com.absolom.utility.data.DocumentEntry;
import com.absolom.utility.data.IDataAccess;
import com.absolom.utility.data.IDocumentEntry;

public class EntityManager {

	private HashMap<Class<?>, Document> m_documents;
	private HashMap<Class<?>, Class<?>> m_idToTypeMapping;
	private IDataAccess m_dataAccess;
	private GameBoard m_board;

	public EntityManager(IDataAccess dataAccess) {
		m_dataAccess = dataAccess;
		
		m_documents = new HashMap<Class<?>, Document>();
		m_documents.put(Robot.class, new Document("Robot", m_dataAccess));
		m_documents.put(Player.class, new Document("Player", m_dataAccess));
		m_documents.put(Ricochet.class, new Document("Ricochet", m_dataAccess));
		m_documents.put(Target.class, new Document("Target", m_dataAccess));

		m_idToTypeMapping = new HashMap<Class<?>, Class<?>>();
		m_idToTypeMapping.put(RobotId.class, Robot.class);
		m_idToTypeMapping.put(PlayerId.class, Player.class);
		m_idToTypeMapping.put(RicochetId.class, Ricochet.class);
		m_idToTypeMapping.put(TargetId.class, Target.class);
	}

	public boolean applyChanges() {
		boolean success = false;

		try {
			m_dataAccess.commit();
			success = true;
		} finally {
			if (!success) {
				m_dataAccess.rollback();
			}
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T createEntity(EntityId id) {

		if (!m_idToTypeMapping.containsKey(id.getClass())) {
			throw new InvalidParameterException();
		}

		Class<T> entityType = (Class<T>) m_idToTypeMapping.get(id.getClass());

		Document document = getDocument(entityType);

		if (document == null) {
			throw new InvalidParameterException();
		}

		IDocumentEntry entry = new DocumentEntry(id.toString());
		document.markAsNew(entry);

		return (T) createEntity(entityType, entry);
	}

	@SuppressWarnings("unchecked")
	private <T extends Entity> T createEntity(Class<?> entityType, IDocumentEntry data) {
		T entity = null;
		try {
			entity = (T) entityType.getConstructor(IDocumentEntry.class).newInstance(data);
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		} catch (InstantiationException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} finally {
			if (entity == null) {
				throw new RuntimeException("Entity class must have a constructor with one parameter of type Item");
			}
		}

		return (T) entity;
	}

	public <T extends Entity> List<T> getAllEntitiesByType(Class<T> entityType) {
		Document document = getDocument(entityType);

		List<IDocumentEntry> entries = document.getAllEntries();
		ArrayList<T> entities = new ArrayList<T>();

		for (IDocumentEntry entry : entries) {
			T entity = createEntity(entityType, entry);
			entities.add(entity);
		}

		return entities;
	}
	
	public List<EntityId> getAllEntityIdByType(Class<?> entityType) {
		Document document = getDocument(entityType);

		List<IDocumentEntry> entries = document.getAllEntries();
		ArrayList<EntityId> entities = new ArrayList<EntityId>();

		for (IDocumentEntry entry : entries) {
			Entity entity = createEntity(entityType, entry);
			entities.add(entity.getEntityId());
		}

		return entities;
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T getEntity(EntityId id) {
		Document document = getDocument(id);

		if (document == null) {
			return null;
		}

		IDocumentEntry entry = document.getEntry(id.toString());
		Class<?> entityType = m_idToTypeMapping.get(id.getClass());

		return (T) createEntity(entityType, entry);
	}

	private Document getDocument(EntityId id) {
		if (!m_idToTypeMapping.containsKey(id.getClass())) {
			return null;
		}

		Class<?> entityType = m_idToTypeMapping.get(id.getClass());

		return m_documents.get(entityType);
	}

	private Document getDocument(Class<?> entityType) {
		if (!m_documents.containsKey(entityType)) {
			throw new InvalidParameterException();
		}

		return m_documents.get(entityType);
	}

	public boolean updateEntity(Entity entity) {
		if (!m_documents.containsKey(entity)) {
			return false;
		}

		Document document = m_documents.get(entity.getClass());
		document.markAsDirty(entity.getData());
		return true;
	}

	public GameBoard getBoard() {
		return m_board;
	}

	public void setBoard(GameBoard m_board) {
		this.m_board = m_board;
	}

}
