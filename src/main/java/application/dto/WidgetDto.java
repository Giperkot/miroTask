package application.dto;

import application.dao.entity.WidgetEntity;

import java.time.LocalDateTime;


public class WidgetDto {

    private int id = -1;

    private int width;

    private int height;

    private LocalDateTime updateTime;

    private int x = 0;

    private int y = 0;

    private Integer zIndex;

    public WidgetDto() {
    }

    public WidgetDto(WidgetEntity entity) {
        this.id = entity.getId();
        this.width = entity.getWidth();
        this.height = entity.getHeight();
        this.updateTime = entity.getUpdateTime();
        this.x = entity.getX();
        this.y = entity.getY();
        this.zIndex = entity.getzIndex();
    }

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

    public WidgetEntity toEntity () {
        WidgetEntity result = new WidgetEntity();

        result.setId(id);
        result.setWidth(width);
        result.setHeight(height);
        result.setX(x);
        result.setY(y);
        result.setzIndex(zIndex);

        return result;
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
}
