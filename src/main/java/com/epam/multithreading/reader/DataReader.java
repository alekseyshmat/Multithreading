package com.epam.multithreading.reader;

import com.epam.multithreading.exception.ReadingFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    private static final String MESSAGE_FOR_EXCEPTION = "File is not found";
    private static final Logger LOGGER = LogManager.getLogger(DataReader.class);

    public DataReader() {
    }

    public List<String> readingLines(String path) throws ReadingFileException {
        List<String> linesWithValues = new ArrayList<>();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(new File(path)));
            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                linesWithValues.add(tmp);
            }
            LOGGER.info("File was read");
        } catch (IOException ex) {
            LOGGER.error("File is not found");
            throw new ReadingFileException(MESSAGE_FOR_EXCEPTION, ex);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                  LOGGER.error("IOException");
                }
            }
        }
        return linesWithValues;
    }
}
