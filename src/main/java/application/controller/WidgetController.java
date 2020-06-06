package application.controller;

import application.dto.SimpleResultDto;
import application.dto.WidgetDto;
import application.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/widget")
public class WidgetController {

    private WidgetService service;

    @Autowired
    public WidgetController(WidgetService service) {
        this.service = service;
    }

    @RequestMapping(value = "/createWidget", method = RequestMethod.POST)
    public WidgetDto createWidget (@RequestBody WidgetDto widgetDto) {
        return service.createWidget(widgetDto);
    }

    @RequestMapping(value = "/getWidgetList", method = RequestMethod.POST)
    public List<WidgetDto> getWidgetList () {
        return service.getWidgetList();
    }

    @RequestMapping(value = "/updateWidget", method = RequestMethod.POST)
    public WidgetDto updateWidget (@RequestBody WidgetDto widgetDto) {
        return service.updateWidget(widgetDto);
    }

    @RequestMapping(value = "/removeWidget", method = RequestMethod.POST)
    public boolean removeWidget (@RequestBody WidgetDto widgetDto) {
        return service.removeWidget(widgetDto);
    }

    @RequestMapping(value = "/getWidgetsCount", method = RequestMethod.POST)
    public SimpleResultDto getWidgetsCount () {
        return service.getWidgetsCount();
    }
}
