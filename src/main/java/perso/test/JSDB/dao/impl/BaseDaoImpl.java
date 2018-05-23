package perso.test.JSDB.dao.impl;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import perso.test.JSDB.dao.api.BaseDao;
import perso.test.JSDB.exception.JSDBAlreadyInitializedException;
import perso.test.JSDB.exception.JSDBDatabaseAlreadyExistsException;
import perso.test.JSDB.exception.JSDBInternalException;
import perso.test.JSDB.exception.JSDBNotInitializedException;
import perso.test.JSDB.model.BaseCache;
import perso.test.JSDB.model.CollectionSystem;
import perso.test.JSDB.model.DatabaseSystem;

@Repository("BaseDao")
public class BaseDaoImpl implements BaseDao {

	private static final Logger	LOG			= Logger.getLogger(BaseDaoImpl.class);

	private final String		COLLECTIONS	= "/Collections";
	private final String		DATABASES	= "/Databases";
	private final String		BASE		= "/JSDB";
	private final File			BASE_FOLDER	= new File(BASE);

	@Autowired
	private BaseCache			cache;

	public boolean isInitialized() {
		return BASE_FOLDER.exists() && BASE_FOLDER.isDirectory();
	}

	public void initialize() throws JSDBNotInitializedException, JSDBAlreadyInitializedException {
		if (!isInitialized()) {
			Exception exception = null;
			BASE_FOLDER.mkdir();
			if (isInitialized()) {
				File databaseFolder = new File(BASE + File.separator + DATABASES);
				databaseFolder.mkdir();
				if (databaseFolder.exists() && databaseFolder.isDirectory()) {
					DatabaseSystem system = new DatabaseSystem();
					File systemFile = new File(BASE + File.separator + "system");
					try {
						systemFile.createNewFile();
						new ObjectMapper().writeValue(systemFile, system);
						return;
					}
					catch (IOException ioe) {
						exception = ioe;
						LOG.error("Fail to create system file.", ioe);
					}
					catch (Exception e) {
						exception = e;
						LOG.error("Fail to write system file.", e);
						systemFile.delete();
					}
					databaseFolder.delete();
				}
				BASE_FOLDER.delete();
			}
			throw new JSDBNotInitializedException("Fail to initialize JSDB engine.", exception);
		}
		else {
			throw new JSDBAlreadyInitializedException("JSDB engine is already initialized.");
		}
	}

	public DatabaseSystem getDatabaseSystem() throws JSDBNotInitializedException, JSDBInternalException {
		if (isInitialized()) {
			if (getCache().get("DatabaseSystem") == null) {
				try {
					getCache().put(
								"DatabaseSystem",
								new ObjectMapper().readValue(new File(BASE + File.separator + "system"),
											DatabaseSystem.class));
				}
				catch (Exception e) {
					throw new JSDBInternalException("Unable to read DatabaseSystem file.", e);
				}
			}
			return (DatabaseSystem) getCache().get("DatabaseSystem");
		}
		throw new JSDBNotInitializedException("JSDB engine is not initialized.");
	}

	public CollectionSystem getCollectionSystem(String db) throws JSDBNotInitializedException, JSDBInternalException {
		if (isInitialized()) {
			if (getCache().get("CollectionSystem_" + db) == null) {

				DatabaseSystem databaseSystem = getDatabaseSystem();
				String databaseFolder = databaseSystem.getDatabaseFolder(db);
				if (databaseFolder != null) {
					try {
						getCache().put(
									"CollectionSystem_" + db,
									new ObjectMapper().readValue(new File(BASE + File.separator + DATABASES
												+ File.separator + databaseFolder + File.separator + "system"),
												CollectionSystem.class));
					}
					catch (Exception e) {
						throw new JSDBInternalException("Unable to read CollectionSystem file.", e);
					}
				}
			}
			return (CollectionSystem) getCache().get("CollectionSystem_" + db);
		}
		throw new JSDBNotInitializedException("JSDB engine is not initialized.");
	}

	public String createDatabase(String name) throws JSDBNotInitializedException, JSDBInternalException,
				JSDBDatabaseAlreadyExistsException {
		if (isInitialized()) {
			Exception exception = null;
			String folder = name + System.currentTimeMillis();

			File dbFolder = new File(BASE + File.separator + DATABASES + File.separator + folder);
			if (!dbFolder.exists()) {
				dbFolder.mkdir();
				if (dbFolder.exists() && dbFolder.isDirectory()) {
					File collectionFolder = new File(dbFolder + File.separator + COLLECTIONS);
					collectionFolder.mkdir();
					if (collectionFolder.exists() && collectionFolder.isDirectory()) {
						CollectionSystem collectionSystem = new CollectionSystem();
						collectionSystem.setDatabaseName(name);
						File systemFile = new File(dbFolder.getAbsolutePath() + File.separator + "system");
						try {
							systemFile.createNewFile();
							new ObjectMapper().writeValue(systemFile, collectionSystem);
							return folder;
						}
						catch (IOException ioe) {
							exception = ioe;
							LOG.error("Fail to create system file.", ioe);
						}
						catch (Exception e) {
							exception = e;
							LOG.error("Fail to write system file.", e);
							systemFile.delete();
						}
						collectionFolder.delete();
					}
					dbFolder.delete();
				}
				throw new JSDBInternalException("Fail to create JSDB database.", exception);
			}
			else {
				throw new JSDBInternalException("JSDB database already exists.");
			}
		}
		throw new JSDBNotInitializedException("JSDB engine is not initialized.");
	}

	public void store(DatabaseSystem databaseSystem) throws JSDBNotInitializedException, JSDBInternalException {
		if (isInitialized()) {
			File databaseFile = new File(BASE + File.separator + DATABASES + File.separator + "System");
			try {
				new ObjectMapper().writeValue(databaseFile, databaseSystem);
				getCache().put("DatabaseSystem", databaseSystem);
			}
			catch (Exception e) {
				LOG.error("Fail to update system file.", e);
				throw new JSDBInternalException("Fail to update system file", e);
			}

		}
		throw new JSDBNotInitializedException("JSDB engine is not initialized.");
	}

	public void store(CollectionSystem collectionSystem) throws JSDBNotInitializedException, JSDBInternalException {
		if (isInitialized()) {
			DatabaseSystem databaseSystem = getDatabaseSystem();
			File collectionFile = new File(BASE + File.separator + DATABASES + File.separator
						+ databaseSystem.getDatabaseFolder(collectionSystem.getDatabaseName()) + File.separator
						+ "System");
			try {
				new ObjectMapper().writeValue(collectionFile, collectionSystem);
				getCache().put("CollectionSystem_" + collectionSystem.getDatabaseName(), databaseSystem);
			}
			catch (Exception e) {
				LOG.error("Fail to update system file.", e);
				throw new JSDBInternalException("Fail to update system file", e);
			}
		}
		throw new JSDBNotInitializedException("JSDB engine is not initialized.");
	}

	public BaseCache getCache() {
		return cache;
	}

	public void setCache(BaseCache cache) {
		this.cache = cache;
	}

}
