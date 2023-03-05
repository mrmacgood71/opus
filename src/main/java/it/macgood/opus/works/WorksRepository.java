package it.macgood.opus.works;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface WorksRepository extends JpaRepository<Work, Integer> {

    Optional<Work> findWorkById(Integer id);
    Optional<Work> findWorkByOriginalFileName(String originalFileName);
    Optional<Work> findWorkByPath(String path);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO _user_works(_user_id, works_id) VALUES (:userId, :workId)",
            nativeQuery = true)
    void saveSequence(
            @Param("userId") Integer userId,
            @Param("workId") Integer workId
    );

    @Modifying
    @Transactional
    @Query(value = "SELECT DISTINCT work.*\n" +
            "FROM work\n" +
            "         JOIN _user_works ON work.id = _user_works.works_id\n" +
            "WHERE _user_works._user_id = :userId",
            nativeQuery = true)
    Optional<List<Work>> findWorksByUserId(Integer userId);

    @Transactional
    @Query(value = "SELECT path from work where original_file_name = :filename",
            nativeQuery = true)
    String findPathByOriginalFileName(
            @Param("filename") String originalFilename
    );

}
