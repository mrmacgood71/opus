package it.macgood.opus.career;

import it.macgood.opus.user.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<Career, Integer> {
    List<Career> findAll();
}
