package perso.test.JSDB.exception;

public class JSDBDatabaseAlreadyExistsException extends JSDBException {

	public JSDBDatabaseAlreadyExistsException() {
		super();
	}

	public JSDBDatabaseAlreadyExistsException(String message) {
		super(message);
	}

	public JSDBDatabaseAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public JSDBDatabaseAlreadyExistsException(Throwable cause) {
		super(cause);
	}
}
