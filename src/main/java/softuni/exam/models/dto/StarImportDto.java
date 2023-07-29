package softuni.exam.models.dto;

import softuni.exam.models.entity.StarTypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static softuni.exam.util.ConstantMassages.STAR_IMPORT_DTO_FORMAT;

public class StarImportDto {
    //name - accepts char sequence (between 2 to 30 inclusive). The values are unique in the database.
    @NotNull
    @Size(min = 2, max = 30)
    private String name;
    //light years - The distance from Earth in light years. Accepts only positive number.
    @NotNull
    @Positive
    private Double lightYears;
    //description - a long and detailed description about the star with a character length value higher than or equal to 6.
    @NotNull
    @Size(min = 6)
    private String description;
    //star type - categorization of the stars. Ordinal enumeration, one of the following – RED_GIANT, WHITE_DWARF, NEUTRON_STAR
    @NotNull
    private StarTypeEnum starType;
    //observers – a collection with all the astronomers that are studying the star.
    @NotNull
    @Positive
    private Long constellation;

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

    public Long getConstellation() {
        return constellation;
    }

    public void setConstellation(Long constellation) {
        this.constellation = constellation;
    }

    @Override
    public String toString() {
        String formatted = "";
        String years = String.format("%.2f", getLightYears());
        for (int i = 0; i < years.length(); i++) {
            if(years.charAt(i)!=','){
                formatted+=years.charAt(i);
            } else {
                formatted+=".";
            }
        }
        return String.format(STAR_IMPORT_DTO_FORMAT, getName(), formatted);
    }
}
