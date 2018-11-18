package com.epam.multithreading.parser;

import com.epam.multithreading.validator.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FileParser {

    private static final Logger LOGGER = LogManager.getLogger();
    private Validation validation;
    private static final String LINE_SPLIT = "\\s";

    public FileParser() {
        validation = new Validation();
    }

    public List<Integer> parsingLines(List<String> inputList) {
        List<Integer> resultList = new ArrayList<>();
        for (String line : inputList) {
            if (validation.isValid(line)) {
                LOGGER.info("Line: " + line + " is valid line");
                String[] numbers = line.split(LINE_SPLIT);
                for (String number : numbers) {
                    resultList.add(Integer.parseInt(number));
                }
            } else {
                LOGGER.info("Line: " + line + " is not valid line");
            }
        }
        return resultList;
    }

}
