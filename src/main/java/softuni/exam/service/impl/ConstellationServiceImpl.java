package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationImportDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.util.ConstantMassages.*;

// TODO: Implement all methods
@Service
public class ConstellationServiceImpl implements ConstellationService {
    private final String CONSTELLATION_RESOURCE_PATH = "src/main/resources/files/json/constellations.json";
    private ConstellationRepository constellationRepository;
    private Gson gson;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;

    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository,
                                    Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATION_RESOURCE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder result = new StringBuilder();
        List<ConstellationImportDto> constellations = Arrays
                .stream(gson.fromJson(readConstellationsFromFile(), ConstellationImportDto[].class))
                .collect(Collectors.toList());
        for (ConstellationImportDto constellation : constellations) {
            result.append(System.lineSeparator());
            //If a constellation with the same name already exists in the DB return "Invalid constellation".
            if(constellationRepository.findConstellationByName(constellation.getName()).isPresent() ||
                    !validationUtil.isValid(constellation)){
                result.append(String.format(INVALID_IMPORT, CONSTELLATION));
            } else {
                Constellation constellationToSave = modelMapper.map(constellation, Constellation.class);
                constellationRepository.save(constellationToSave);
                result.append(String.format(VALID_IMPORT, CONSTELLATION, constellation.toString()));
            }
        }
        return result.toString().trim();
    }
}
