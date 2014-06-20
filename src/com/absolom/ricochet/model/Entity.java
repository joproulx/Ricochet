package com.absolom.ricochet.model;

import java.util.UUID;

import com.absolom.utility.data.IDocumentEntry;
import com.absolom.utility.serialization.ByteArraySerializable;

public abstract class Entity<T extends EntityId> extends ByteArraySerializable {

	private static final long serialVersionUID = 1L;
	private final IDocumentEntry m_data;
	protected abstract T toEntityId(UUID id);

	public T getEntityId() {
		return toEntityId(UUID.fromString(getData().getId()));
	}

	public Entity(IDocumentEntry data) {
		m_data = data;
	}

	public IDocumentEntry getData() {
		return m_data;
	}
}
