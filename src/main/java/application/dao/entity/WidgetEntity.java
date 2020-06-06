package application.dao.entity;

import java.time.LocalDateTime;

public class WidgetEntity implements Comparable<WidgetEntity> {

    private int id = -1;

    private int width;

    private int height;

    private LocalDateTime updateTime;

    private int x = 0;

    private int y = 0;

    private Integer zIndex;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Integer getzIndex() {
        return zIndex;
    }

    public void setzIndex(Integer zIndex) {
        this.zIndex = zIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(WidgetEntity o) {
        if (zIndex > o.getzIndex()) {
            return 1;
        }

        if (zIndex.equals(o.getzIndex())) {
            return 0;
        }

        return -1;
    }
}
