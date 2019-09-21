package com.mebank.transaction.service;

import com.mebank.transaction.exception.TransactionException;
import com.mebank.transaction.helper.TransactionHelper;
import org.apache.log4j.Logger;

/**
 * Service controls the flow of transaction processing
 */
public class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class);
    private static final String TRANSACTION_FILE = "src/main/resources/transactions.csv";

    /**
     * This method receives input filename and processes it against transaction file
     *
     * @param inputFileName
     */
    public void processTransactions(String inputFileName) {
        try {
            TransactionHelper helper = new TransactionHelper(TRANSACTION_FILE);
            LOGGER.info("Successfully read Transactions from file: " + TRANSACTION_FILE);
            helper.calculateRelativeBalance(inputFileName);
        } catch (TransactionException e) {
            LOGGER.error(e.getMessage());
        }
    }

}
