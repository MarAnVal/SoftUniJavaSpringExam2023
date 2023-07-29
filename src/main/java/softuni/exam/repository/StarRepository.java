package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.StarExportDto;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarTypeEnum;

import javax.persistence.Enumerated;
import java.util.List;
import java.util.Optional;

@Repository

public interface StarRepository extends JpaRepository<Star, Long> {
Optional<Star> findStarByName(String name);
Optional<Star> findStarById(Long id);
@Query("select new softuni.exam.models.dto.StarExportDto(s.name, s.lightYears, s.description, s.constellation.name)" +
        "from Star s " +
        "where s.starType = :starType " +
        "and s.id not in(select distinct o.observingStar.id from Astronomer o)" +
        "order by s.lightYears")
List<StarExportDto> findStarsByStarTypeWithoutObserversOrderByLightYearsAsc(StarTypeEnum starType);
}
