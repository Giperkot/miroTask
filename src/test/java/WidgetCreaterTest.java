

import application.dto.WidgetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import queryManager.QueriesManager;
import task.WidgetTask;
import task.Requester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WidgetCreaterTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WidgetCreaterTest.class);
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(50, 100, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    /**
     * Запусткать после старта приложения
     *
     * Суть теста - создать по 100 виджетов в 50 потоков.
     * Если результирующее количество будет == 5_000 - это успех.
     *
     * @param args
     */
    public static void main (String... args) throws ExecutionException, InterruptedException, IOException {

        ArrayList<Future<String>> futureList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            List<WidgetDto> widgetDtoList = new ArrayList<>();

            for (int j = 0; j < 100; j++) {
                WidgetDto widgetDto = new WidgetDto();
                widgetDto.setWidth((int) (Math.random() * 50 + 10));
                widgetDto.setHeight((int) (Math.random() * 50 + 20));
                widgetDto.setX((int) (Math.random() * 50));
                widgetDto.setY((int) (Math.random() * 50));

                widgetDtoList.add(widgetDto);
            }

            WidgetTask createWidgetTask = new WidgetTask(widgetDtoList, "http://localhost:8080/api/widget/createWidget");

            futureList.add(threadPool.submit(createWidgetTask));
        }

        for (Future<String> result : futureList) {
            LOGGER.info("result is " + result.get());
        }


        // Попробуем получить количество записей
        QueriesManager queriesManager = new QueriesManager();
        String response = Requester.sendRequest(queriesManager, "http://localhost:8080/api/widget/getWidgetsCount", null);

        LOGGER.warn("Final result is: " + response);

        threadPool.shutdown();
    }

}
