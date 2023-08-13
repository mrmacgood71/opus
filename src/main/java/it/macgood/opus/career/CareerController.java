package it.macgood.opus.career;

import it.macgood.opus.user.model.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/v1/career")
public class CareerController {

    private final CareerRepository repository;

    @Autowired
    public CareerController(CareerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Career> findAll() {
        return repository.findAll();
    }
}
