package com.mebank.transaction;

import com.mebank.transaction.service.TransactionService;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.Scanner;

/**
 * Main class which accepts file as input
 */
public class TransactionController {
    private static final Logger LOGGER = Logger.getLogger(TransactionController.class);

    public static void main(String[] args) {
        Optional<String> fileName;
        if (args.length == 0 || args[0] == null) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Please input a file name to read data:");
                fileName = Optional.of(scanner.nextLine());
                if (fileName.get().isEmpty())
                    LOGGER.error("File name not specified. Please specify a file name to read data");
                else
                    break;
            }
        } else
            fileName = Optional.of(args[0]);

        new TransactionService().processTransactions(fileName.get());
    }
}
