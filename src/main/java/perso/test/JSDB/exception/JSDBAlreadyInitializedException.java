package perso.test.JSDB.exception;

public class JSDBAlreadyInitializedException extends JSDBException {

	public JSDBAlreadyInitializedException() {
		super();
	}

	public JSDBAlreadyInitializedException(String message) {
		super(message);
	}

	public JSDBAlreadyInitializedException(String message, Throwable cause) {
		super(message, cause);
	}

	public JSDBAlreadyInitializedException(Throwable cause) {
		super(cause);
	}
}
