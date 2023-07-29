package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.StarExportDto;
import softuni.exam.models.dto.StarImportDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarTypeEnum;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.util.ConstantMassages.*;

@Service
public class StarServiceImpl implements StarService {
    private final String STAR_RESOURCE_PATH = "src/main/resources/files/json/stars.json";
    private StarRepository starRepository;
    private ConstellationRepository constellationRepository;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    @Autowired
    public StarServiceImpl(StarRepository starRepository, ConstellationRepository constellationRepository,
                           Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(STAR_RESOURCE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder result = new StringBuilder();
        List<StarImportDto> stars = Arrays.stream(gson.fromJson(readStarsFileContent(), StarImportDto[].class))
                .collect(Collectors.toList());
        for (StarImportDto star : stars) {
            result.append(System.lineSeparator());
            if (starRepository.findStarByName(star.getName()).isPresent() ||
                    constellationRepository.findConstellationById(star.getConstellation()).isEmpty() ||
                    !validationUtil.isValid(star)) {
                result.append(String.format(INVALID_IMPORT, STAR));
            } else {
                Constellation constellation = constellationRepository.getById(star.getConstellation());
                Star starToSave = modelMapper.map(star, Star.class);
                starToSave.setConstellation(constellation);
                starRepository.save(starToSave);
                result.append(String.format(VALID_IMPORT, STAR, star.toString()));
            }
        }
        return result.toString().trim();
    }

    @Override
    public String exportStars() {
        StringBuilder result = new StringBuilder();
        List<StarExportDto> stars = starRepository
                .findStarsByStarTypeWithoutObserversOrderByLightYearsAsc(StarTypeEnum.RED_GIANT);
        for (StarExportDto star : stars) {
            result.append(System.lineSeparator());
            result.append(star.toString());
        }
        return result.toString().trim();
    }
}
