package com.mebank.transaction.helper;

import com.mebank.transaction.exception.TransactionException;
import com.mebank.transaction.models.Input;
import com.mebank.transaction.models.Transaction;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FileHelperTest {
    private final Appender appender = mock(Appender.class);
    private final Logger LOGGER = Logger.getRootLogger();
    private FileHelper fileHelper;

    @Before
    public void setup() {
        fileHelper = new FileHelper();
        LOGGER.addAppender(appender);
    }

    @Test
    public void testGetTransactionsFromFile() throws TransactionException {
        List<Transaction> transactionsList = fileHelper.getTransactionsFromFile("src/test/resources/transactions.csv");
        assertEquals(5, transactionsList.size());
    }

    @Test
    public void testConvertEmptyFile() throws TransactionException {
        List<Transaction> transactionsList = fileHelper.getTransactionsFromFile("src/test/resources/emptytransactions.csv");
        assertEquals(0, transactionsList.size());
    }

    @Test(expected = TransactionException.class)
    public void testConvertErrorFile() throws TransactionException {
        fileHelper.getTransactionsFromFile("src/test/resources/errortransactions.csv");
    }

    @Test
    public void testGetInputFromFile() throws TransactionException {
        List<Optional<Input>> inputOptional = fileHelper.mapInput("src/test/resources/input.csv");
        assertEquals(1, inputOptional.size());
    }

    @Test
    public void testGetInputFromEmptyFile() throws TransactionException {
        List<Optional<Input>> inputOptional = fileHelper.mapInput("src/test/resources/emptyinput.csv");
        assertTrue(inputOptional.isEmpty());
    }

    @Test
    public void testGetInputFromErrorFile() throws TransactionException {
        List<Optional<Input>> inputOptionalList = fileHelper.mapInput("src/test/resources/errorinput.csv");
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(appender).doAppend(argument.capture());
        assertEquals(Level.ERROR, argument.getValue().getLevel());
        assertEquals("Unable to parse Input file for processing: Unparseable date: \"20/10/201\"", argument.getValue().getMessage());
        assertEquals(inputOptionalList.get(0), Optional.empty());
    }
}
