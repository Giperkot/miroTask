package application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrontController {

    @GetMapping
    public String index () {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>if you see this page application started succesfully.</h1>\n" +
                "    <br/>\n" +
                "    <p>You can use api to widget interface</p>\n" +
                "</body>\n" +
                "</html>";
    }

}
