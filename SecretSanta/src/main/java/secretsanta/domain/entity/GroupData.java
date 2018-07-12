package secretsanta.domain.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table( name="group_data",
        indexes={
            @Index( name="uq_group_name",
                    columnList = "group_name",
                    unique = true)
        })
public class GroupData {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name="group_name", length = 255, nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name="group_state", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupState state;

    @OneToMany(mappedBy = "group")
    private List<Member> members;

    public GroupData() {

    }

    public GroupData(Long id, String name, GroupState state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupState getState() {
        return state;
    }

    public void setState(GroupState state) {
        this.state = state;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
