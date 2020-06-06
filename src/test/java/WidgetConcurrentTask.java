import application.dto.WidgetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import queryManager.QueriesManager;
import task.Requester;
import task.WidgetTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Что ещё можно интересного попробовать?
 *
 * Давайте создадим 100 виджетов
 *
 * и запустим параллельно
 * 1) создание ещё 100 виджетов
 * 2) обновление 50 существующих
 * 3) удаление 50 (которые не обновляем)
 *
 * В результате должно остаться 150 записей.
 *
 * Возьмём 5 потоков.
 */
public class WidgetConcurrentTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(WidgetConcurrentTask.class);
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 5, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public static void main (String... args) throws ExecutionException, InterruptedException, IOException {

        ArrayList<Future<String>> futureList = new ArrayList<>();

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

        for (Future<String> result : futureList) {
            LOGGER.info("result is " + result.get());
        }

        List<WidgetDto> updateDtoList = new ArrayList<>();
        List<WidgetDto> deleteDtoList = new ArrayList<>();

        // Теперь обновления и удаления.
        for (int j = 0; j < 50; j++) {
            WidgetDto widgetDtoUpd = new WidgetDto();
            widgetDtoUpd.setWidth(10);
            widgetDtoUpd.setHeight(20);
            widgetDtoUpd.setX(50);
            widgetDtoUpd.setY(50);
            widgetDtoUpd.setId(52 + j);
            widgetDtoUpd.setzIndex(1);

            WidgetDto widgetDtoDel = new WidgetDto();
            widgetDtoDel.setId(j + 1);

            updateDtoList.add(widgetDtoUpd);
            deleteDtoList.add(widgetDtoDel);
        }

        futureList.clear();

        WidgetTask updateWidgetTask = new WidgetTask(updateDtoList, "http://localhost:8080/api/widget/updateWidget");
        WidgetTask deleteWidgetTask = new WidgetTask(deleteDtoList, "http://localhost:8080/api/widget/removeWidget");


        futureList.add(threadPool.submit(updateWidgetTask));
        futureList.add(threadPool.submit(deleteWidgetTask));
        futureList.add(threadPool.submit(createWidgetTask));

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
