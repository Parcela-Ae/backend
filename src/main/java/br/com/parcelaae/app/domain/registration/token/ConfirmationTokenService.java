package br.com.parcelaae.app.domain.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return  confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        var confirmationToken = getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        saveConfirmationToken(confirmationToken);
    }
}
