package com.mebank.transaction.helper;

import com.mebank.transaction.exception.TransactionException;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TransactionHelperTest {
    private final Appender appender = mock(Appender.class);
    private final Logger LOGGER = Logger.getRootLogger();
    private TransactionHelper transactionHelper;

    @Before
    public void setup() throws TransactionException {
        transactionHelper = new TransactionHelper("src/test/resources/transactions.csv");
        LOGGER.addAppender(appender);
    }

    @Test
    public void testCalculateRelativeBalance() throws TransactionException {
        transactionHelper.calculateRelativeBalance("src/test/resources/input.csv");
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(appender).doAppend(argument.capture());
        assertEquals(Level.INFO, argument.getValue().getLevel());
        assertEquals("Relative balance for " + "Input{accountId='ACC334455', from =20/10/2018 12:00:00, to =20/10/2018 19:00:00}" + " is " +
                ": -$25.00", argument.getValue().getMessage());
    }

    @Test(expected = TransactionException.class)
    public void testCalculateRelativeBalanceFailure() throws TransactionException {
        transactionHelper.calculateRelativeBalance("src/test/resources/inpu.csv");
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(appender).doAppend(argument.capture());
        assertEquals(Level.ERROR, argument.getValue().getLevel());
        assertEquals("Program was unable to process input", argument.getValue().getMessage());
    }
}
