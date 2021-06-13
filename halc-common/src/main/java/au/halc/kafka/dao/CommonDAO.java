package au.halc.kafka.dao;

import au.halc.kafka.model.DBTps;
import java.util.List;

/**
 * interface for CommonDAO.
 * @author indor
 */
public interface CommonDAO {
	
	boolean insertDBTps(DBTps dbTps);
	
	List<DBTps> fetchDBTpsForLastMinute();
	
}