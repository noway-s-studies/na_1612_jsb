package secretsanta.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import secretsanta.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.email = :email AND m.group.id = :id")
    Member findByGroupIdAndEmail(@Param("id") Long id, @Param("email") String email);
}
