package com.mebank.transaction.helper;

import com.google.common.base.Splitter;
import com.mebank.transaction.exception.TransactionException;
import com.mebank.transaction.models.Input;
import com.mebank.transaction.models.Transaction;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is a FileHelper class designed to read and map files to respective models
 */
public class FileHelper {

    private static final Logger LOGGER = Logger.getLogger(FileHelper.class);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * This method uses opencsv api to map transaction file to List of transaction objects
     *
     * @param file
     * @return
     * @throws TransactionException
     */
    public List<Transaction> getTransactionsFromFile(final String file) throws TransactionException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file), StandardCharsets.UTF_8)) {
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(Transaction.class).withIgnoreLeadingWhiteSpace(false)
                    .build();
            return csvToBean.parse();
        } catch (Exception e) {
            LOGGER.error("");
            throw new TransactionException(e.getMessage());
        }
    }

    /**
     * This method maps input file to Input object
     *
     * @param inputFileName
     * @return
     * @throws TransactionException
     */
    public List<Optional<Input>> mapInput(String inputFileName) throws TransactionException {
        try (Stream<String> stream = Files.lines(Paths.get(inputFileName))) {
            return stream.map(this::mapToEmployee)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new TransactionException("Unable to read file: " + inputFileName);
        }
    }

    private Optional<Input> mapToEmployee(String data) {
        Map<String, String> resultMap = Splitter.on(',')
                .trimResults()
                .withKeyValueSeparator(Splitter.on(':').limit(2).trimResults())
                .split(data);
        try {
            return Optional.of(new Input(resultMap.get("accountId"), simpleDateFormat.parse(resultMap.get("from")),
                    simpleDateFormat.parse(resultMap.get("to"))));
        } catch (ParseException e) {
            LOGGER.error("Unable to parse Input file for processing: " + e.getMessage());
        }
        return Optional.empty();
    }
}
