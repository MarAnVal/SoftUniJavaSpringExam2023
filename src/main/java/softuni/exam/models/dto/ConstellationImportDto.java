package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static softuni.exam.util.ConstantMassages.CONSTELLATION_IMPORT_DTO_FORMAT;

public class ConstellationImportDto {
    //name – accepts char sequence (between 3 to 20 inclusive). The values are unique in the database.
    @NotNull
    @Size(min = 3, max = 20)
    private String name;
    //description - accepts char sequence about the naming of the constellation
    // with a character length value higher than or equal to 5.
    @NotNull
    @Size(min = 5)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(CONSTELLATION_IMPORT_DTO_FORMAT, getName(), getDescription());
    }
}
