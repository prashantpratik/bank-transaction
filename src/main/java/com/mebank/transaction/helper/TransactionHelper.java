package com.mebank.transaction.helper;

import com.mebank.transaction.exception.TransactionException;
import com.mebank.transaction.models.Input;
import com.mebank.transaction.models.Transaction;
import org.apache.log4j.Logger;

import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class performs the tax calculation for employees
 */
public class TransactionHelper {
    private static final Logger LOGGER = Logger.getLogger(TransactionHelper.class);
    private List<Transaction> transactionList;
    private FileHelper fileHelper;


    /**
     * @throws TransactionException
     */
    public TransactionHelper(String fileName) throws TransactionException {
        fileHelper = new FileHelper();
        transactionList = fileHelper.getTransactionsFromFile(fileName);
    }

    public void calculateRelativeBalance(String inputFileName) throws TransactionException {
        Optional<Input> input = fileHelper.mapInput(inputFileName).get(0);
        if (!input.isPresent()) {
            throw new TransactionException("Program was unable to process input");
        }
        Input inputData = input.get();
        double relativeBalance = 0d;
        //This code takes care of funds that were transferred to / from an account in a given time frame
        //It also takes care of taking care of reversal transactions happened over the time frame specified
        List<Transaction> relevantTransactionList = transactionList.stream().filter(transaction ->
                transaction.getFromAccountId().equals(inputData.getAccountId())
                        || transaction.getToAccountId().equals(inputData.getAccountId())).
                filter(transaction -> (transaction.getCreatedAt().after(inputData.getFrom())
                        && transaction.getCreatedAt().before(inputData.getTo()))
                        || transaction.getTransactionType().equals(Transaction.TransactionType.REVERSAL)).collect(Collectors.toList());

        for (Transaction transaction : relevantTransactionList)
            relativeBalance += getRelativeAmount(transaction, inputData);

        LOGGER.info("Relative balance for " + inputData + " is : " +  NumberFormat.getCurrencyInstance().format(relativeBalance));
    }

    private double getRelativeAmount(Transaction transaction, Input input) {
        if (transaction.getFromAccountId().equals(input.getAccountId())
                && transaction.getTransactionType().equals(Transaction.TransactionType.PAYMENT))
            return -transaction.getAmount();
        else
            return transaction.getAmount();
    }
}
