package perso.test.JSDB.model;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionSystem extends AbstractSystem {

	private String				databaseName;
	private Map<String, String>	collections;

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public Map<String, String> getCollections() {
		return collections;
	}

	public void setCollections(Map<String, String> collections) {
		this.collections = collections;
	}
}
