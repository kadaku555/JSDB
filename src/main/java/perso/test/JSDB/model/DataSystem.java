package perso.test.JSDB.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import perso.test.JSDB.exception.JSDBIndexException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSystem extends AbstractSystem {

	private String									collectionName;
	private int										lastId	= 0;
	private Map<String, String>						format;
	private Map<String, Map<String, Set<String>>>	indexes	= new HashMap<String, Map<String, Set<String>>>();

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public int getLastId() {
		return lastId;
	}

	public void setLastId(int lastId) {
		this.lastId = lastId;
	}

	public Map<String, String> getFormat() {
		return format;
	}

	public void setFormat(Map<String, String> format) {
		this.format = format;
	}

	public Map<String, Map<String, Set<String>>> getIndexes() {
		return indexes;
	}

	public void setIndexess(Map<String, Map<String, Set<String>>> indexes) {
		this.indexes = indexes;
	}

	public Set<String> getIndex() {
		return getIndexes().keySet();
	}

	public Map<String, Set<String>> getIndex(String attributeName) {
		return getIndexes().get(attributeName);
	}

	public Set<String> getIndexedValuesFor(String attributeName) {
		return getIndex(attributeName).keySet();
	}

	public Set<String> getIndexedValuesFor(String attributeName, String value) {
		return getIndex(attributeName).get(value);
	}

	public boolean isIndexable(String attributeName) {
		return getFormat().containsKey(attributeName);
	}

	public void addIndex(String attributeName, Map<String, Set<String>> index) throws JSDBIndexException {
		if (isIndexable(attributeName)) {
			if (!getIndexes().containsKey(attributeName)) {
				getIndexes().put(attributeName, index);
			}
			else {
				throw new JSDBIndexException("Attribute " + attributeName + " is already indexed.");
			}
		}
		else {
			throw new JSDBIndexException("The attribute " + attributeName + " can't be indexed.");
		}
	}

	public void addToIndex(String attributeName, String value, Set<String> ids) throws JSDBIndexException {
		if (isIndexable(attributeName)) {
			if (getIndexes().containsKey(attributeName)) {
				if (!getIndex(attributeName).containsKey(value)) {
					getIndex(attributeName).put(value, ids);
				}
				else {
					throw new JSDBIndexException("An index already exists for value " + value + " of attribute "
								+ attributeName);
				}
			}
			else {
				throw new JSDBIndexException("The attribute " + attributeName + " isn't already indexed.");
			}
		}
		else {
			throw new JSDBIndexException("The attribute " + attributeName + " can't be indexed.");
		}
	}

	public void addToIndexValue(String attributeName, String value, String id) throws JSDBIndexException {
		if (isIndexable(attributeName)) {
			if (getIndexes().containsKey(attributeName)) {
				if (getIndex(attributeName).containsKey(value)) {
					getIndexedValuesFor(attributeName, value).add(id);
				}
				else {
					throw new JSDBIndexException("The value " + value + " isn't already indexed for attribute "
								+ attributeName);
				}
			}
			else {
				throw new JSDBIndexException("The attribute " + attributeName + " isn't already indexed.");
			}
		}
		else {
			throw new JSDBIndexException("The attribute " + attributeName + " can't be indexed.");
		}
	}
}
