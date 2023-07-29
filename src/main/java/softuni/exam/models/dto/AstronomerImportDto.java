package softuni.exam.models.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

import static softuni.exam.util.ConstantMassages.ASTRONOMER_IMPORT_DTO_FORMAT;

@XmlRootElement(name = "astronomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerImportDto {
    //first name - accepts char sequence (between 2 to 30 inclusive).
    @XmlElement(name = "first_name")
    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;
    //last name - accepts char sequence (between 2 to 30 inclusive).
    @XmlElement(name = "last_name")
    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;
    //salary - accepts number values that are more than or equal to 15000.00.
    @XmlElement(name = "salary")
    @DecimalMin(value = "15000.00")
    private Double salary;
    //averageObservationHours - accepts number values that are more than 500.00.
    @XmlElement(name = "average_observation_hours")
    @NotNull
    @DecimalMin(value = "500.00")
    private Double averageObservationHours;
    //birthday - a date in the "yyyy-MM-dd" format. Can be nullable.
    @XmlElement(name = "birthday")
    private String birthday;
    //observing star - the current star that the astronomer is studying.
    @XmlElement(name = "observing_star_id")
    private Long observingStarId;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Long getObservingStarId() {
        return observingStarId;
    }

    public void setObservingStarId(Long observingStarId) {
        this.observingStarId = observingStarId;
    }

    @Override
    public String toString() {
        return String.format(ASTRONOMER_IMPORT_DTO_FORMAT,
                getFirstName(),
                getLastName(),
                formatDouble(getAverageObservationHours()));
    }

    private String formatDouble(Double value) {
        String formatted = "";
        String years = String.format("%.2f", value);
        for (int i = 0; i < years.length(); i++) {
            if (years.charAt(i) != ',') {
                formatted += years.charAt(i);
            } else {
                formatted += ".";
            }
        }
        return formatted;
    }
}
