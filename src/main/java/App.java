import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CsvReaderImpl;

import java.net.URISyntaxException;

public class App {
    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        Logger log = LogManager.getLogger(App.class);

        log.info("Application started");
        CsvReaderImpl reader = new CsvReaderImpl();
        reader.readAndExecute("airports.dat");
    }
}
