package it.macgood.opus.teams;

import it.macgood.opus.user.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TeamsRepository extends JpaRepository<Teams, Integer> {
    List<Teams> findAll();
    Optional<Teams> findById(Integer id);
}
