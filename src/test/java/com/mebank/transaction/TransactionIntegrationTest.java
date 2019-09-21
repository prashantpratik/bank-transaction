package com.mebank.transaction;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionIntegrationTest {
    private final Appender appender = mock(Appender.class);
    private final Logger LOGGER = Logger.getRootLogger();

    @Before
    public void setup() {
        LOGGER.addAppender(appender);
    }

    @Test
    public void testMain() throws IOException {
        String inputFile = "src/test/resources/input.csv";
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        TransactionController.main(new String[]{inputFile});
        verify(appender, times(2)).doAppend(argument.capture());
        assertEquals(1, Files.lines(Paths.get(inputFile)).collect(Collectors.toList()).size());
        assertEquals(Level.INFO, argument.getValue().getLevel());
        assertEquals("Relative balance for Input{accountId='ACC334455', from =20/10/2018 12:00:00, to =20/10/2018 19:00:00} is : -$25.00",
                argument.getValue().getMessage());
    }

    @Test
    public void testMainWithManualInput() {
        System.setIn(new ByteArrayInputStream("src/test/resources/input.csv".getBytes()));
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        TransactionController.main(new String[]{});
        verify(appender, times(2)).doAppend(argument.capture());
        assertEquals(Level.INFO, argument.getValue().getLevel());
        assertEquals("Relative balance for Input{accountId='ACC334455', from =20/10/2018 12:00:00, to =20/10/2018 19:00:00} is : -$25.00",
                argument.getValue().getMessage());
    }

    @Test
    public void testFileNotFound() {
        System.setIn(new ByteArrayInputStream("src/test/resources/inpu.csv".getBytes()));
        ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
        TransactionController.main(new String[]{});
        verify(appender, times(2)).doAppend(argument.capture());
        assertEquals("Unable to read file: src/test/resources/inpu.csv",
                argument.getValue().getMessage());
        assertEquals(Level.ERROR, argument.getValue().getLevel());
    }
}
