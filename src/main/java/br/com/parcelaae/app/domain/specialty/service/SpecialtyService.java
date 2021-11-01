package br.com.parcelaae.app.domain.specialty.service;

import br.com.parcelaae.app.domain.specialty.model.Specialty;
import br.com.parcelaae.app.domain.specialty.model.SpecialtyApiModel;
import br.com.parcelaae.app.domain.specialty.repository.SpecialtyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public Specialty insert(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public List<Specialty> listAll() {
        return specialtyRepository.findAll();
    }

    public Specialty fromDTO(SpecialtyApiModel specialtyApiModel) {
        var specialty = new Specialty();
        BeanUtils.copyProperties(specialtyApiModel, specialty);
        return specialty;
    }
}
