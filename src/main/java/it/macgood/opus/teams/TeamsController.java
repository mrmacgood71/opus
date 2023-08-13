package it.macgood.opus.teams;

import it.macgood.opus.user.model.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/v1/teams")
public class TeamsController {

    private final TeamsRepository repository;

    @Autowired
    public TeamsController(TeamsRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Teams> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Teams findById(@PathVariable String id) {
        return repository.findById(Integer.parseInt(id)).get();
    }
}
