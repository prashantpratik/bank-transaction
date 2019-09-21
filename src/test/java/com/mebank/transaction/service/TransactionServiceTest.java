package com.mebank.transaction.service;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    private final Appender appender = mock(Appender.class);
    private final Logger LOGGER = Logger.getRootLogger();
    private TransactionService transactionService;

    @Before
    public void setup() {
        transactionService = new TransactionService();
        LOGGER.addAppender(appender);
    }

    @Test
    public void testProcessTransaction() {
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        transactionService.processTransactions("src/test/resources/input.csv");
        verify(appender, times(2)).doAppend(argument.capture());
        assertEquals("Relative balance for Input{accountId='ACC334455', from =20/10/2018 12:00:00, to =20/10/2018 19:00:00} is : -$25.00",
                argument.getValue().getMessage());
        assertEquals(Level.INFO, argument.getValue().getLevel());
    }

    @Test
    public void testProcessTransactionError() {
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        transactionService.processTransactions("src/test/resources/inpu.csv");
        verify(appender, times(2)).doAppend(argument.capture());
        assertEquals("Unable to read file: src/test/resources/inpu.csv",
                argument.getValue().getMessage());
        assertEquals(Level.ERROR, argument.getValue().getLevel());
    }
}
