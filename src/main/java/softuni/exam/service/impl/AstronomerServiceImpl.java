package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerImportDto;
import softuni.exam.models.dto.AstronomerImportWrapperDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static softuni.exam.util.ConstantMassages.*;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    private final String ASTRONOMER_RESOURCE_PATH = "src/main/resources/files/xml/astronomers.xml";
    private AstronomerRepository astronomerRepository;
    private StarRepository starRepository;
    private XmlParser xmlParser;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;

    @Autowired
    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, StarRepository starRepository,
                                 XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRONOMER_RESOURCE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder result = new StringBuilder();
        List<AstronomerImportDto> astronomers = xmlParser
                .fromFile(ASTRONOMER_RESOURCE_PATH, AstronomerImportWrapperDto.class)
                .getAstronomers();
        for (AstronomerImportDto astronomer : astronomers) {
            result.append(System.lineSeparator());
            //If an astronaut with the same full name (first name and last name) already exists in the DB return "Invalid astronomer".
            //If an astronaut is observing star that doesn't exist in the DB return "Invalid astronomer ".
            if (starRepository.findStarById(astronomer.getObservingStarId()).isEmpty() ||
                    astronomerRepository
                            .findAstronomerByFirstNameAndLastName(astronomer.getFirstName(), astronomer.getLastName()).isPresent() ||
            !validationUtil.isValid(astronomer)){
                result.append(String.format(INVALID_IMPORT, ASTRONOMER));
            } else{
                Star star = starRepository.getById(astronomer.getObservingStarId());
                Astronomer astronomerToSave = modelMapper.map(astronomer, Astronomer.class);
                astronomerToSave.setStar(star);
                astronomerRepository.save(astronomerToSave);
                result.append(String.format(VALID_IMPORT, ASTRONOMER, astronomer.toString()));
            }
        }
        return result.toString().trim();
    }
}
