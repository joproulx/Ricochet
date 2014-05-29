package com.absolom.ricochet.model;

import java.util.List;

import com.absolom.utility.data.IDataAccess;
import com.absolom.utility.data.IDocumentEntry;
import com.absolom.utility.messaging.MessageTransceiver;

public class DataAccessQuerier implements IDataAccess {
	// private MessageTransceiver m_messageTransceiver;

	public DataAccessQuerier(MessageTransceiver messageTransceiver) {
		// m_messageTransceiver = messageTransceiver;
	}

	@Override
	public void initializeDocument(String documentId, boolean clearAllItems) {
	}

	@Override
	public List<IDocumentEntry> getAllEntries(String documentId) {
		// QueryDocumentCommand.Result result =
		// (QueryDocumentCommand.Result)m_messageTransceiver.postAndWait(new
		// QueryDocumentCommand(documentId));

		// return result.getData();
		return null;
	}

	@Override
	public IDocumentEntry getEntry(String documentId, String itemId) {
		/*
		 * QueryDocumentMessage.Result result =
		 * (QueryDocumentMessage.Result)m_messageTransceiver.postAndWait(new
		 * QueryDocumentMessage(documentId, itemId));
		 * 
		 * List<IDocumentEntry> entries = result.getData();
		 * 
		 * if (entries.size() == 0) { return null; }
		 * 
		 * return entries.iterator().next();
		 */
		return null;
	}

	@Override
	public void commit() {
		throw new RuntimeException("Can only query from this class");
	}

	@Override
	public void rollback() {
		throw new RuntimeException("Can only query from this class");
	}

	@Override
	public void markAsNew(IDocumentEntry entry) {
		throw new RuntimeException("Can only query from this class");
	}

	@Override
	public void markAsDirty(IDocumentEntry entry) {
		throw new RuntimeException("Can only query from this class");
	}

	@Override
	public void markAsRemoved(String documentId, String entryId) {
		throw new RuntimeException("Can only query from this class");
	}

}
