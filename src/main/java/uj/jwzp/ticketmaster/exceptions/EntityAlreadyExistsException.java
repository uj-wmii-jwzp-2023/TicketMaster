package uj.jwzp.ticketmaster.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {
	public EntityAlreadyExistsException(String exception) {
		super(exception);
	}
	public EntityAlreadyExistsException(String field, Object value) {
		super("Entity already exists with " + field + " = " + value.toString());
	}
}
