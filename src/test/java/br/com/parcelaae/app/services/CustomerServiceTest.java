package br.com.parcelaae.app.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Test
    public void shouldReturnHello() {
        String response = "Hello";

        Assertions.assertEquals("Hello", response);
    }
}
