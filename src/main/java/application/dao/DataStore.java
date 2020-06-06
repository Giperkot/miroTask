package application.dao;

import application.dao.entity.WidgetEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Здесь реализовано что-то вроде блокировки всей таблицы,
 * обычно БД лочат построчно.
 * Но кто так делает?
 */
public class DataStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataStore.class);
    private static final DataStore instance = new DataStore();

    public static DataStore getInstance() {
        return instance;
    }

    private static Integer  widgetSequence = 1;

    private static int getNextWidgetId () {
        return widgetSequence++;
    }

    private Map<Integer, WidgetEntity> widgetStore = new ConcurrentHashMap<>();

    private TreeSet<WidgetEntity> zIndexWidgetIndex = new TreeSet<>();

    public int getMaxZIndex () {
        if (zIndexWidgetIndex.size() < 1) {
            return -1;
        }

        return zIndexWidgetIndex.last().getzIndex();
    }

    public void addWidgetToIndex (WidgetEntity widgetEntity) {
        zIndexWidgetIndex.add(widgetEntity);
    }

    public void removeWidgetFromIndex (WidgetEntity widgetEntity) {
        zIndexWidgetIndex.remove(widgetEntity);
    }

    private void setZIndex (WidgetEntity widgetEntity) {
        int zIndex = getMaxZIndex();

        if (widgetEntity.getzIndex() != null && widgetEntity.getzIndex() > zIndex) {
            // Всё корректно.
            return;
        }

        if (widgetEntity.getzIndex() != null && widgetEntity.getzIndex() <= zIndex) {
            // Тут начинается веселье

            moveWidgetsClose(widgetEntity);
            return;
        }

        if (widgetEntity.getzIndex() == null) {
            widgetEntity.setzIndex(++zIndex);
        }
    }

    public WidgetEntity addWidget (WidgetEntity widgetEntity) {

        synchronized (DataStore.class) {
            int currentId = DataStore.getNextWidgetId();
            widgetEntity.setId(currentId);

            // Сдвинуть элементы, если нужно
            setZIndex(widgetEntity);

            widgetStore.put(widgetEntity.getId(), widgetEntity);
            addWidgetToIndex(widgetEntity);
        }

        return widgetEntity;
    }

    public WidgetEntity updateWidget (WidgetEntity widgetEntity) {
        if (widgetEntity.getId() < 0 || widgetStore.get(widgetEntity.getId()) == null) {
            throw new RuntimeException("Нельзя добавить виджет без id");
        }

        synchronized (DataStore.class) {

            WidgetEntity oldVersion = widgetStore.get(widgetEntity.getId());
            removeWidgetFromIndex(oldVersion);

            // Сдвинуть элементы, если нужно
            setZIndex(widgetEntity);

            widgetStore.put(widgetEntity.getId(), widgetEntity);
            addWidgetToIndex(widgetEntity);
        }

        return widgetEntity;
    }

    public boolean removeWidget (WidgetEntity widgetEntity) {
        if (widgetEntity.getId() < 0 || widgetStore.get(widgetEntity.getId()) == null) {
            throw new RuntimeException("Нельзя добавить виджет без id");
        }

        synchronized (DataStore.class) {
            // Сдвинуть элементы, если нужно
            setZIndex(widgetEntity);

            widgetEntity = widgetStore.remove(widgetEntity.getId());
            removeWidgetFromIndex(widgetEntity);
        }

        return true;
    }

    public void moveWidgetsClose (WidgetEntity widgetEntity) {
        TreeSet<WidgetEntity> entitySet = DataStore.getInstance().getAllWidgetsOrderByZIndex();
        Iterator<WidgetEntity> descIterator = entitySet.descendingIterator();

        while(descIterator.hasNext()) {
            WidgetEntity widgetEntity1 = descIterator.next();

            if (widgetEntity1.getzIndex() < widgetEntity.getzIndex()) {
                break;
            }

            widgetEntity1.setzIndex(widgetEntity1.getzIndex() + 1);
        }
    }

    public int getWidgetsCount () {
        return widgetStore.size();
    }

    public WidgetEntity getWidgetById (int id) {
        return widgetStore.get(id);
    }

    public TreeSet<WidgetEntity> getAllWidgetsOrderByZIndex () {
        return zIndexWidgetIndex;
    }
}
