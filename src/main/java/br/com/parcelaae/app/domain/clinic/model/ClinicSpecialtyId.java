package br.com.parcelaae.app.domain.clinic.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClinicSpecialtyId implements Serializable {

    private static final long serialVersionUID  = 1L;

    private Integer clinicId;
    private Integer specialtyId;

    public ClinicSpecialtyId() {
    }

    public ClinicSpecialtyId(Integer clinicId, Integer specialtyId) {
        this.clinicId = clinicId;
        this.specialtyId = specialtyId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public Integer getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClinicSpecialtyId that = (ClinicSpecialtyId) o;
        return clinicId.equals(that.clinicId) && specialtyId.equals(that.specialtyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clinicId, specialtyId);
    }
}
