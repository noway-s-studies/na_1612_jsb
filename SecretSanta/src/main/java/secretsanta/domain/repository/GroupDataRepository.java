package secretsanta.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import secretsanta.domain.entity.GroupData;

public interface GroupDataRepository extends JpaRepository<GroupData, Long> {

    @Query("SELECT gd FROM GroupData gd WHERE gd.name = :name")
    GroupData findByName(@Param("name") String name);
}
