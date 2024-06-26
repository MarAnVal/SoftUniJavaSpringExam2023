package softuni.exam.models.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "astronomers")
public class Astronomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Double salary;
    @Column(name = "average_observation_hours", nullable = false)
    private Double averageObservationHours;
    @Column
    private Date birthday;
    @ManyToOne
    @JoinColumn(name = "observing_star_id")
    private Star observingStar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getAverageObservationHours() {
        return averageObservationHours;
    }

    public void setAverageObservationHours(Double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Star getStar() {
        return observingStar;
    }

    public void setStar(Star star) {
        this.observingStar = star;
    }
}
