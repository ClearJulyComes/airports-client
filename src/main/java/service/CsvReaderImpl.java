package service;

import client.ApiClient;
import client.ApiClientImpl;
import model.AirportResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CsvReaderImpl implements CsvReader{
    @Override
    public void readAndExecute(String fileName) {
        Logger log = LogManager.getLogger(service.CsvReader.class);
        ApiClient apiClient = new ApiClientImpl();

        File file = new File(fileName);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);

        try(FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr)) {

            String line;

            while((line = br.readLine()) != null) {
                int id = Integer.parseInt(line.substring(0, line.indexOf(",")));
                executor.execute(() -> {
                    try {
                        AirportResource airport = apiClient.getAirport(id).get();
                        log.info(Thread.currentThread().getName() + " " + airport);
                        if (id == airport.getAirport().getId()) {
                            log.info("Received right id = " + id);
                        } else {
                            log.warn("Wrong id in response. Request id = " + id + ", response id = " + airport.getAirport().getId());
                        }
                    } catch (InterruptedException | ExecutionException | URISyntaxException e) {
                        log.error("Error during requesting", e);
                    }
                });
            }
            System.out.println("Finish");
        } catch (FileNotFoundException e) {
            log.error("Data file not found", e);
        } catch (IOException e) {
            log.error("Error during reading from csv", e);
        }
    }
}
