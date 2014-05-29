package com.absolom.ricochet.model;

import java.util.UUID;

import com.absolom.utility.serialization.ByteArraySerializable;

public class EntityId extends ByteArraySerializable {

	private static final long serialVersionUID = 1L;
	private final UUID m_id;

	public EntityId(UUID id) {
		m_id = id;
	}

	public UUID getId() {
		return m_id;
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof EntityId) {
			EntityId that = (EntityId) other;
			result = this.m_id.equals(that.m_id);
		}
		return result;
	}

	@Override
	public int hashCode() {
		return m_id.hashCode();
	}

	@Override
	public String toString() {
		return getId().toString();
	}

}
