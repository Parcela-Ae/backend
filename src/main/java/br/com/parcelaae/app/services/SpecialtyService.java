package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Specialty;
import br.com.parcelaae.app.dto.SpecialtyDTO;
import br.com.parcelaae.app.repositories.SpecialtyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public Specialty insert(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public List<Specialty> listAll() {
        return specialtyRepository.findAll();
    }

    public Specialty fromDTO(SpecialtyDTO specialtyDTO) {
        var specialty = new Specialty();
        BeanUtils.copyProperties(specialtyDTO, specialty);
        return specialty;
    }
}
