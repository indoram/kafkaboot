package au.halc.kafka.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import au.halc.kafka.model.DBTps;

/**
 * Common DAO Impl.
 * @author indor
 */

@Repository
public class CommonDAOImpl implements CommonDAO {
	
	private final Logger logger 
		= LoggerFactory.getLogger(CommonDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String dbtps_sql = "INSERT INTO kafka_db_tps " + "(no_of_msgs, db_time, save_time) VALUES (?, ?, ?)";
	
	private String dbtps_last_minute = "SELECT * FROM kafka_db_tps where save_time >= ? and save_time <= ?";
	
	
	@Override
	public List<DBTps> fetchDBTpsForLastMinute() {
		List<DBTps> dbList = new ArrayList<>();
		
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime localDateTimeMinuteLess = localDateTime.minusMinutes(1);
		
		Object[] objs = new Object[2];
		objs[0] = localDateTimeMinuteLess;
		objs[1] = localDateTime;
		
		
		List<DBTps> dbTpsList = jdbcTemplate.query(dbtps_last_minute, new PreparedStatementSetter() {			   
	         public void setValues(PreparedStatement preparedStatement) throws SQLException {
	            preparedStatement.setTimestamp(1,  Timestamp.valueOf(localDateTimeMinuteLess));
	            preparedStatement.setTimestamp(2,  Timestamp.valueOf(localDateTime));
	         }
	      }, new DBTpsMapper());
		return dbTpsList;
	}
	
	@Override
	public boolean insertDBTps(DBTps dbTps) {		
		int total = jdbcTemplate.update(dbtps_sql, new Object[] {
				dbTps.getTotalNo(), dbTps.getTimeInMillis(), dbTps.getRecordedTime() });
	
		if (total != 1) {
			logger.error("Error when inserting db tps");
		}
		
		return (total == 1);
	}
	
}