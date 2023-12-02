package az.office.officenter.service;


import az.office.officenter.dao.entity.User;
import az.office.officenter.dao.repository.UserRepository;
import az.office.officenter.enums.UserRole;
import az.office.officenter.exception.UserAlreadyExistException;
import az.office.officenter.exception.UserNotFoundException;
import az.office.officenter.request.AuthenticationRequest;
import az.office.officenter.request.RegisterRequest;
import az.office.officenter.response.AuthenticationResponse;
import az.office.officenter.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public RegisterResponse register(RegisterRequest request) {
        var exist = userRepo.getUserByEmail(request.getEmail()).isPresent();
        if (exist) {
            throw new UserAlreadyExistException("User already exist");
        }
        var user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ADMIN)
                .build();
        var userEntity = userRepo.save(user);
        return RegisterResponse.buildRegisterDto(userEntity);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
