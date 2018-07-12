package santabackend.backend.domain.repository;

import santabackend.backend.domain.entity.Child;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChildRepository extends JpaRepository<Child, Integer> {
    @Query("SELECT c FROM Child c WHERE c.city = :city")
    List<Child> findByCity(@Param("city") String city);
}
