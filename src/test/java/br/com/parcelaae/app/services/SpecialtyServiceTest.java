package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Specialty;
import br.com.parcelaae.app.dto.SpecialtyDTO;
import br.com.parcelaae.app.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SpecialtyServiceTest {

    public static final int ID = 1;
    public static final String SPECIALTY_NAME = "Odontologia";

    @InjectMocks
    private SpecialtyService specialtyService;

    @Mock
    private SpecialtyRepository specialtyRepository;

    @Test
    void shouldInsertASpecialty() {
        var specialtyToSave = Specialty.builder().build();
        var specialtyExpected = Specialty.builder().id(ID).build();

        when(specialtyRepository.save(specialtyToSave)).thenReturn(specialtyExpected);

        var specialtyActual = specialtyService.insert(specialtyToSave);

        assertThat(specialtyActual).isNotNull();
    }

    @Test
    void shouldListAllSpecialties() {
        var specialties = List.of(Specialty.builder().build());

        when(specialtyRepository.findAll()).thenReturn(specialties);

        var specialtiesActual = specialtyService.listAll();

        assertThat(specialtiesActual).isNotEmpty();
    }

    @Test
    void shouldConvertDTOToEntity() {
        var specialtyDTO = SpecialtyDTO.builder()
                .id(ID)
                .name(SPECIALTY_NAME)
                .build();

        var specialtyEntityExpected = Specialty.builder()
                .id(ID)
                .name(SPECIALTY_NAME)
                .build();

        var specialtyEntityActual = specialtyService.fromDTO(specialtyDTO);

        assertThat(specialtyEntityActual).usingRecursiveComparison().isEqualTo(specialtyEntityExpected);
    }
}
