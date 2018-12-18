package jason.luo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class ScheduledImportOrder {
    @Autowired
    private MovieService movieService;

    private final static int MAX_SIZE = 1000;

    @Scheduled(cron = "0 0 2 * * ?")
    public void importOrdersFromRedisToPostgres() throws ParseException {
        movieService.importOrdersFromRedisToPostgres(MAX_SIZE);
    }

}
