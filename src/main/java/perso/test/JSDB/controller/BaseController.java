package perso.test.JSDB.controller;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import perso.test.JSDB.dao.api.BaseDao;
import perso.test.JSDB.model.CollectionSystem;
import perso.test.JSDB.model.DatabaseSystem;

@Path("/base")
@Component("BaseController")
public class BaseController implements Serializable {

	private static final Logger	LOG	= Logger.getLogger(BaseController.class);

	@Autowired
	private BaseDao				baseDao;

	@GET
	@Path("hello")
	@Produces("text/plain")
	public String sayHello() {
		return "Hello world!";
	}

	@GET
	@Path("showDBs")
	@Produces(MediaType.APPLICATION_JSON)
	public String showDBs() throws Exception {
		DatabaseSystem system = getBaseDao().getDatabaseSystem();
		return system.getDatabases().keySet().toString();
	}

	@GET
	@Path("showCollections/{db}")
	@Produces(MediaType.APPLICATION_JSON)
	public String showCollections(@PathParam("db") String db) throws Exception {
		CollectionSystem system = getBaseDao().getCollectionSystem(db);
		return system.getCollections().keySet().toString();
	}

	@PUT
	@Path("createDatabase/{db}")
	@Produces(MediaType.APPLICATION_JSON)
	public void createDatabase(@PathParam("db") String db) throws Exception {
		DatabaseSystem databaseSystem = getBaseDao().getDatabaseSystem();
		String folder = getBaseDao().createDatabase(db);
		databaseSystem.addDatabase(db, folder);
		getBaseDao().store(databaseSystem);
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
}
