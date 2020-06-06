package application.service;

import application.dao.DataStore;
import application.dao.entity.WidgetEntity;
import application.dto.SimpleResultDto;
import application.dto.WidgetDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WidgetService {

    public WidgetDto createWidget (WidgetDto widgetDto) {

        if (widgetDto.getHeight() <=0 || widgetDto.getWidth() <= 0) {
            throw new RuntimeException("Геометрия задана не верно");
        }

        DataStore dataStore = DataStore.getInstance();

        WidgetEntity widgetEntity = widgetDto.toEntity();

        // ставим updateTime
        widgetEntity.setUpdateTime(LocalDateTime.now());

        // Добавляем
        widgetEntity = dataStore.addWidget(widgetEntity);

        return new WidgetDto(widgetEntity);
    }

    public List<WidgetDto> getWidgetList () {

        Set<WidgetEntity> entitySet = DataStore.getInstance().getAllWidgetsOrderByZIndex();
        List<WidgetDto> widgetDtoList = new ArrayList<>();

        for (WidgetEntity widgetEntity : entitySet) {
            widgetDtoList.add(new WidgetDto(widgetEntity));
        }

        return widgetDtoList;
    }

    public WidgetDto updateWidget (WidgetDto widgetDto) {

        if (widgetDto.getHeight() <=0 || widgetDto.getWidth() <= 0) {
            throw new RuntimeException("Геометрия задана не верно");
        }

        DataStore dataStore = DataStore.getInstance();

        WidgetEntity widgetEntity = widgetDto.toEntity();
        widgetEntity.setUpdateTime(LocalDateTime.now());

        // Добавляем
        widgetEntity = dataStore.updateWidget(widgetEntity);


        return new WidgetDto(widgetEntity);
    }

    public boolean removeWidget (WidgetDto widgetDto) {

        if (widgetDto == null) {
            throw new RuntimeException("Геометрия задана не верно");
        }

        DataStore dataStore = DataStore.getInstance();

        WidgetEntity widgetEntity = widgetDto.toEntity();

        // Добавляем
        return dataStore.removeWidget(widgetEntity);
    }

    public SimpleResultDto getWidgetsCount () {

        SimpleResultDto result = new SimpleResultDto();

        result.setMessage(String.valueOf(DataStore.getInstance().getWidgetsCount()));
        result.setSuccess(true);

        return result;
    }

}
