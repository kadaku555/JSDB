package perso.test.JSDB.model;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository("BaseCache")
public class BaseCache extends HashMap<String, AbstractSystem> {

}
