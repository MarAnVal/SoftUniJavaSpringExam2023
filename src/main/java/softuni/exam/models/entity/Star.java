package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stars")
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "light_years", nullable = false)
    private Double lightYears;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(name = "star_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StarTypeEnum starType;
    @OneToMany(mappedBy = "observingStar", fetch = FetchType.EAGER)
    private List<Astronomer> observers;
    @ManyToOne
    @JoinColumn
    private Constellation constellation;

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

    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StarTypeEnum getStarType() {
        return starType;
    }

    public void setStarType(StarTypeEnum starType) {
        this.starType = starType;
    }

    public List<Astronomer> getObservers() {
        return observers;
    }

    public void setObservers(List<Astronomer> observers) {
        this.observers = observers;
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }
}
