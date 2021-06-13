package au.halc.kafka.consumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static au.halc.kafka.config.KafkaConstants.GROUP_ID;
import static au.halc.kafka.config.KafkaConstants.SETTLED_TOPIC;

import au.halc.kafka.model.SettledTransaction;
import au.halc.kafka.model.DBTps;

import au.halc.kafka.consumer.services.AccountTransferService;

import au.halc.kafka.dao.CommonDAO;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * Consumer of Kafka topic.
 * @author indor
 */
@Service
public class KafkaSettledTransactionArchiver {	

	private final Logger logger 
			= LoggerFactory.getLogger(KafkaSettledTransactionArchiver.class);
	
	@Autowired
	private AccountTransferService accountTransferService;
	
	@Autowired
	private CommonDAO commonDAO;
	
	private ConcurrentLinkedQueue<SettledTransaction> settledQueue = new ConcurrentLinkedQueue<SettledTransaction>();
	
	private ConcurrentLinkedQueue<DBTps> dbTpsQueue = new ConcurrentLinkedQueue<DBTps>();
	
	private List<SettledTransaction> settledTransList = new ArrayList<>();
	private java.util.concurrent.ExecutorService executorService = null;
	
	private int thdCount = 10;
	private int batchCount = 250;
	private int currCount = 0;
	private int sleepTimeMillis = 100;
	
	private Thread archiverThread;
	
	@KafkaListener(topics = SETTLED_TOPIC, groupId = GROUP_ID)
	public void consume(SettledTransaction settledTransaction) {
		
		logger.info("Settled transaction {}", settledTransaction.toString());
		
		settledQueue.offer(settledTransaction);
		
		if (archiverThread == null) {
			archiverThread = new SettledTransArchiver();
			archiverThread.start();
		}
		
	}
	
	class SettledTransArchiver extends Thread {
		
		@Override
		public void run() {
			 
			 while (true) {
				 
				 populateLiustUptoBatchSize(settledTransList, settledQueue);				 
				  
				 if (settledTransList.size() == 0) {
					 sleepForMillis(sleepTimeMillis);
				 }				 
				 
				 try {
					 if (settledTransList.size() > 0) { 
						 logger.info("Inserting ST {}", settledTransList.size());
						 
						 DBTps dbTps = new DBTps();
						 
						 dbTps.setTotalNo(settledTransList.size());
						 dbTps.setRecordedTime(LocalDateTime.now());						 
						 long startTime = System.currentTimeMillis();
						 
						 accountTransferService.insertSettledTransactions(settledTransList);
						 
						 long endTime = System.currentTimeMillis();						 
						 long delta = endTime - startTime;
						 dbTps.setTimeInMillis(delta);			 
						 //dbTpsQueue.offer(dbTps);
						 
						 settledTransList.clear();
						 commonDAO.insertDBTps(dbTps);
					 }
					 sleepForMillis(sleepTimeMillis);
				 } catch (Exception ex) {
					 logger.error("error", ex);
				 }
				 
			 }
			 
		};
	}
	
	
	private void sleepForMillis(long timeMillis) {
		try {
			Thread.sleep(timeMillis);
		} catch (Exception ex) {
			logger.error("sleepForMillis", ex);
		}
	}
	
	private void populateLiustUptoBatchSize(List<SettledTransaction> settledTransList, 
			ConcurrentLinkedQueue<SettledTransaction> settledQueue) {
		SettledTransaction settledTran = null;		
		while ((settledTran = settledQueue.poll()) != null) {
			settledTransList.add(settledTran);
			if (settledTransList.size() == batchCount) {
				break;
			}
		}
		
	}
	
	private void executeAsync(SettledTransaction settledTran) {
		if (executorService == null) {
			logger.info("Initing  Kafkfa settled transaction archiver");
			executorService = Executors.newFixedThreadPool(thdCount);	
		}
		/*
		executorService.submit(() -> {
			List<SettledTransaction> tmpList = new ArrayList<>();
			tmpList.add(settledTran);
			return accountTransferService.insertSettledTransaction(tmpList);
		});*/
	}
	
	private void executeAsync(List<SettledTransaction> settledTransList) {
		if (executorService == null) {
			logger.info("Initing  Kafkfa settled transaction archiver");
			executorService = Executors.newFixedThreadPool(thdCount);	
		}
		
		executorService.submit(() -> {
			return accountTransferService.insertSettledTransactions(settledTransList);
		});
	}
	
}
