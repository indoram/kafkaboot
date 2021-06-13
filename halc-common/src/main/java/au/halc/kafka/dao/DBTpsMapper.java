package au.halc.kafka.dao;

import au.halc.kafka.model.DBTps;
import org.springframework.jdbc.core.RowMapper;
import java.sql.*;

/**
 * DBTpsMapper.
 */
public class DBTpsMapper implements RowMapper<DBTps> {
	
	 public DBTps mapRow(ResultSet rs, int rowNum) throws SQLException {
		 	DBTps dbTps = new DBTps();
		 	
			int noOfMsgs = rs.getInt("no_of_msgs");
			long dbTime = rs.getLong("db_time");
			
			dbTps.setTotalNo(noOfMsgs);
			dbTps.setTimeInMillis(dbTime);
			return dbTps;
	   }
	
	
}