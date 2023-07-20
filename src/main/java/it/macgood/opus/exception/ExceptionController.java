package it.macgood.opus.exception;

import it.macgood.opus.demo.DemoController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exception")
public class ExceptionController {

    @GetMapping
    public ExceptionDetails forbidden(
            @RequestParam String code,
            @RequestParam String explain
    ) {

        ExceptionDetails message = ExceptionDetails.builder()
                .code(code)
                .explain(explain)
                .build();

        return message;
    }



}
