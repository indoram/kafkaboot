package au.halc.kafka.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Account transfer constants.
 * @author indor
 */
public class KafkaAccountTransferConstants {
	
	public static final int DEFAULT_MAX_OF_TRANSACTIONS = 1000;
	public static final int DEFAULT_DURATION = 10;
	public static final int DEFAULT_DELAY_MILLIS = 1;
	
	public static final int ACCOUNT_ID_1 = 64534;
	public static final int ACCOUNT_ID_2 = 64535;
	public static final int ACCOUNT_ID_3 = 74535;
	public static final int ACCOUNT_ID_4 = 74536;
	
	public static final String TRAN_REF = "Ref";
	
	public static final BigDecimal TRAN_AMT = BigDecimal.valueOf(1.12);
	
	
	public static List<Integer> getAccountIds() {
		List<Integer> accountIds = new ArrayList<>();
		accountIds.add(ACCOUNT_ID_1);
		accountIds.add(ACCOUNT_ID_2);
		accountIds.add(ACCOUNT_ID_3);
		accountIds.add(ACCOUNT_ID_4);
		return accountIds;
	}
	
	public static List<Integer> getToAccountIds() {
		List<Integer> accountIds = new ArrayList<>();
		accountIds.add(ACCOUNT_ID_2);
		accountIds.add(ACCOUNT_ID_1);
		accountIds.add(ACCOUNT_ID_3);
		accountIds.add(ACCOUNT_ID_4);
		return accountIds;
	}
}
