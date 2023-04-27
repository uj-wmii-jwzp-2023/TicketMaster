package uj.jwzp.ticketmaster.exceptions;

public class EntityNotExistsException extends RuntimeException {
	public EntityNotExistsException(String exception) {
		super(exception);
	}
	public EntityNotExistsException(long entityId) {
		super("Entity with ID " + entityId + " does not exist");
	}
}
