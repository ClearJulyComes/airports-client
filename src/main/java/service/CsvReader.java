package service;

import java.net.URISyntaxException;

public interface CsvReader {
    void readAndExecute(String fileName) throws URISyntaxException;
}
