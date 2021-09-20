package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Address;
import br.com.parcelaae.app.repositories.AddressRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Test
    void shouldFindAddressById() {
        var addressId = 1;
        var addressExpected = Optional.ofNullable(Address.builder().build());

        when(addressRepository.findById(addressId)).thenReturn(addressExpected);

        var addressActual = addressService.find(addressId);

        assertThat(addressActual).isNotNull();
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenDoesNotExistAnAddressForId() {
        var addressId = 1;

        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        try {
            addressService.find(addressId);
        } catch (ObjectNotFoundException exception) {
            assertThat(exception).isInstanceOf(ObjectNotFoundException.class);
            assertThat(exception).hasMessageContaining("Objeto não encontrado para o Id: " + addressId);
        }
    }

    @Test
    void shouldInsertNewAddress() {
        var addressToSave = Address.builder().build();
        var addressExpected = Address.builder().id(1).build();

        when(addressRepository.save(addressToSave)).thenReturn(addressExpected);

        var addressActual = addressService.insert(addressToSave);

        assertThat(addressActual).isNotNull();
        assertThat(addressActual.getId()).isEqualTo(addressExpected.getId());
    }

    @Test
    void shouldSaveAllAddress() {
        var addresses = List.of(Address.builder().build());
        var addressesExpected = List.of(Address.builder().id(1).build());

        when(addressRepository.saveAll(addresses)).thenReturn(addressesExpected);

        var addressesActual = addressService.saveAll(addresses);

        assertThat(addressesActual).isNotNull();
        assertThat(addressesActual.get(0).getId()).isEqualTo(addressesExpected.get(0).getId());
    }

    @Test
    void shouldUpdateAnAddress() {
        var addressToUpdate = Address.builder().build();
        var addressExpected = Address.builder().id(1).build();

        when(addressRepository.save(addressToUpdate)).thenReturn(addressExpected);

        var addressActual = addressService.update(addressToUpdate);

        assertThat(addressActual).isNotNull();
        assertThat(addressActual.getId()).isEqualTo(addressExpected.getId());
    }

    @Test
    void shouldDeleteAnAddress() {
        var addressId = 1;
        var addressToDelete = Optional.ofNullable(Address.builder().build());
        var addressServiceSpy = Mockito.spy(addressService);

        when(addressRepository.findById(addressId)).thenReturn(addressToDelete);
        doNothing().when(addressRepository).deleteById(addressId);

        addressService.delete(addressId);

        verify(addressRepository, times(1)).deleteById(addressId);
    }
}
