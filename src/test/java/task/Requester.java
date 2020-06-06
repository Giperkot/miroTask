package task;

import application.dto.WidgetDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import queryManager.QueriesManager;
import queryManager.RequestProperties;

import java.io.IOException;

public class Requester {

    public static String sendRequest (QueriesManager queriesManager, String url, WidgetDto widgetDto) throws IOException {
        RequestProperties requestProperties = new RequestProperties();
        ObjectMapper objectMapper = new ObjectMapper();

        requestProperties.setRequestBody(objectMapper.writeValueAsString(widgetDto));

        return queriesManager.sendRequest("POST", url, requestProperties);
    }
}
