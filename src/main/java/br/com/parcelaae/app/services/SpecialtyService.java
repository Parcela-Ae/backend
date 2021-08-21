package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Specialty;
import br.com.parcelaae.app.dto.SpecialtyDTO;
import br.com.parcelaae.app.repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository repository;

    public Specialty insert(Specialty specialty) {
        return repository.save(specialty);
    }

    public List<Specialty> listAll() {
        return repository.findAll();
    }

    public Specialty fromDTO(SpecialtyDTO specialtyDTO) {
        Specialty specialty = new Specialty();
        specialty.setName(specialtyDTO.getName());
        return specialty;
    }
}
