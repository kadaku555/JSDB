package perso.test.JSDB.model;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import perso.test.JSDB.exception.JSDBDatabaseAlreadyExistsException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatabaseSystem extends AbstractSystem {

	private Map<String, String>	databases	= new HashMap<String, String>();

	public Map<String, String> getDatabases() {
		return databases;
	}

	public void setDatabases(Map<String, String> databases) {
		this.databases = databases;
	}

	public String getDatabaseFolder(String name) {
		return databases.get(name);
	}

	public void addDatabase(String name, String folder) throws JSDBDatabaseAlreadyExistsException {
		if (getDatabaseFolder(name) != null) {
			throw new JSDBDatabaseAlreadyExistsException("Database " + name + " already exists.");
		}
		getDatabases().put(name, folder);
	}

	public void deleteDatabase(String name) {
		getDatabases().put(name, null);
	}
}
