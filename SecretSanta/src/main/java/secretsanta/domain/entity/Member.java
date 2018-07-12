package secretsanta.domain.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="members", indexes = {
        @Index(name = "uq_group_email", columnList = "group_id,email", unique = true)
})
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="group_id",nullable = false)
    private GroupData group;

    @NotNull
    @NotEmpty
    @Column(name="name", length = 255, nullable = false)
    private String name;

    @NotNull
    @NotEmpty
    @Column(name="email", length = 255, nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(name = "picked_id", nullable = true)
    private Member picked;

    public Member() {
    }

    public Member(Long id, GroupData group, String name, String email) {
        this.id = id;
        this.group = group;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupData getGroup() {
        return group;
    }

    public void setGroup(GroupData group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Member getPicked() {
        return picked;
    }

    public void setPicked(Member picked) {
        this.picked = picked;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, name, email);
    }
}
