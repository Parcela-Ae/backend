package br.com.parcelaae.app.domain.clinic.model;

import br.com.parcelaae.app.domain.specialty.model.Specialty;
import br.com.parcelaae.app.domain.user.model.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clinic_specialties")
public class ClinicSpecialty implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ClinicSpecialtyId clinicSpecialtyId = new ClinicSpecialtyId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clinicId")
    private User clinic;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("specialtyId")
    private Specialty specialty;

    private Double appointmentValue;

    public ClinicSpecialty() {
    }

    public ClinicSpecialty(ClinicSpecialtyId clinicSpecialtyId, Clinic clinic, Specialty specialty, Double appointmentValue) {
        this.clinicSpecialtyId = clinicSpecialtyId;
        this.clinic = clinic;
        this.specialty = specialty;
        this.appointmentValue = appointmentValue;
    }

    public ClinicSpecialty(Specialty specialty) {
        this.specialty = specialty;
        this.appointmentValue = 0.00;
    }

    public ClinicSpecialty(User clinic, Specialty specialty) {
        this.clinic = clinic;
        this.specialty = specialty;
        this.appointmentValue = 0.00;
    }

    public ClinicSpecialty(User clinic, Specialty specialty, Double appointmentValue) {
        this.clinic = clinic;
        this.specialty = specialty;
        this.appointmentValue = appointmentValue;
    }

    public ClinicSpecialtyId getClinicSpecialtyId() {
        return clinicSpecialtyId;
    }

    public void setClinicSpecialtyId(ClinicSpecialtyId clinicSpecialtyId) {
        this.clinicSpecialtyId = clinicSpecialtyId;
    }

    public User getClinic() {
        return clinic;
    }

    public void setClinic(User clinic) {
        this.clinic = clinic;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Double getAppointmentValue() {
        return appointmentValue;
    }

    public void setAppointmentValue(Double appointmentValue) {
        this.appointmentValue = appointmentValue;
    }
}
