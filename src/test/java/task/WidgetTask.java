package task;

import application.dto.WidgetDto;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import queryManager.QueriesManager;

import java.util.List;
import java.util.concurrent.Callable;

public class WidgetTask implements Callable<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WidgetTask.class);

    private static HttpClient client = new DefaultHttpClient();
    private List<WidgetDto> widgetDtoList;
    private String url;

    public WidgetTask(List<WidgetDto> widgetDtoList, String url) {
        this.widgetDtoList = widgetDtoList;
        this.url = url;
    }

    @Override
    public String call() throws Exception {

        QueriesManager queriesManager = new QueriesManager();
        int count = 0;
        try {
            for (WidgetDto widgetDto : widgetDtoList) {
                String response = Requester.sendRequest(queriesManager, url, widgetDto);

                LOGGER.info("result = " + response);
                count++;
            }

            return "count of success: " + count;
        } catch (Exception ex) {
            LOGGER.error("Всё плохо. Нужно исправить ошибку. ", ex);
            throw new RuntimeException("Всё плохо. Нужно исправить ошибку. ", ex);
        }
    }
}
