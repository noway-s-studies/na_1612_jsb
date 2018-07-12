package santabackend.backend.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "children")
public class Child {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @NotNull
    @NotEmpty
    @Column(name = "name", length = 255)
    private String name;
    
    @NotNull
    @NotEmpty
    @Column(name = "city", length = 100)
    private String city;
    
    @NotNull
    @Column(name = "gender", length = 6)
    @Enumerated(EnumType.STRING)    
    private Gender gender;
    
    @NotNull
    @Column(name = "behaviour", length = 4)
    @Enumerated(EnumType.STRING)
    private Behaviour behaviour;
    
    @NotNull
    @Column(name = "state", length = 20)
    @Enumerated(EnumType.STRING)
    private State state;

    public Child() {
    }

    public Child(Integer id, String name, String city, Gender gender, Behaviour behaviour, State state) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.gender = gender;
        this.behaviour = behaviour;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
}
