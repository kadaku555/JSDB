package perso.test.JSDB.exception;

public class JSDBNotInitializedException extends JSDBException {

	public JSDBNotInitializedException() {
		super();
	}

	public JSDBNotInitializedException(String message) {
		super(message);
	}

	public JSDBNotInitializedException(String message, Throwable cause) {
		super(message, cause);
	}

	public JSDBNotInitializedException(Throwable cause) {
		super(cause);
	}
}
