package perso.test.JSDB.dao.api;

import perso.test.JSDB.exception.JSDBDatabaseAlreadyExistsException;
import perso.test.JSDB.exception.JSDBException;
import perso.test.JSDB.exception.JSDBInternalException;
import perso.test.JSDB.exception.JSDBNotInitializedException;
import perso.test.JSDB.model.CollectionSystem;
import perso.test.JSDB.model.DatabaseSystem;

public interface BaseDao {

	boolean isInitialized();

	void initialize() throws JSDBException;

	DatabaseSystem getDatabaseSystem() throws JSDBNotInitializedException, JSDBInternalException;

	CollectionSystem getCollectionSystem(String db) throws JSDBNotInitializedException, JSDBInternalException;

	String createDatabase(String name) throws JSDBNotInitializedException, JSDBInternalException,
				JSDBDatabaseAlreadyExistsException;

	void store(DatabaseSystem databaseSystem) throws JSDBNotInitializedException, JSDBInternalException;

	void store(CollectionSystem databaseSystem) throws JSDBNotInitializedException, JSDBInternalException;
}
