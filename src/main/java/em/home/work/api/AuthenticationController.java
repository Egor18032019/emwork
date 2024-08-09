package em.home.work.api;

import em.home.work.model.JwtAuthenticationResponse;
import em.home.work.model.SignInRequest;
import em.home.work.model.SignUpRequest;
import em.home.work.model.TokenRequest;
import em.home.work.service.AuthenticationService;
import em.home.work.service.JwtTokenService;
import em.home.work.utils.EndPoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = EndPoint.API + EndPoint.AUTH)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication Controller", description = "Контроллер для аутентификации и регистрации пользователей")
public class AuthenticationController {
    AuthenticationService authenticationService;
    JwtTokenService jwtService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping(EndPoint.REGISTER)
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) {

        try {
            JwtAuthenticationResponse response = authenticationService.signUp(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.badRequest().build();
        }

    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping(EndPoint.LOGIN)
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping(EndPoint.REFRESH)
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody TokenRequest request) {
        // Проверка на валидность токена уже была произведене
        return ResponseEntity.ok(jwtService.refreshToken(request.getToken()));
    }
}
