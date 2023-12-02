package az.office.officenter.service;

import az.office.officenter.dao.entity.About;
import az.office.officenter.dao.entity.User;
import az.office.officenter.dao.repository.AboutRepository;
import az.office.officenter.dao.repository.UserRepository;
import az.office.officenter.dto.AboutDto;
import az.office.officenter.enums.UserRole;
import az.office.officenter.exception.AboutNotFoundException;
import az.office.officenter.exception.UserNotFoundException;
import az.office.officenter.mapper.AboutMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AboutService {
    private final AboutRepository aboutRepository;
    private final AboutMapper aboutMapper;
    private final UserRepository userRepository;

    public ResponseEntity<AboutDto> add(AboutDto aboutRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            About about = aboutMapper.fromDtoToModel(aboutRequest);
            log.info("Inside add {}", about);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(aboutMapper.fromModelToDto(aboutRepository.save(about)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<AboutDto> update(AboutDto aboutRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            aboutRepository.findById(aboutRequest.getId()).orElseThrow(
                    () -> new AboutNotFoundException("About not found"));
            About updatedAbout = aboutMapper.fromDtoToModel(aboutRequest);
            log.info("Inside update {}", aboutRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(aboutMapper.fromModelToDto(aboutRepository.save(updatedAbout)));

        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    public void deleteById(Long userId, Long aboutId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("user not found"));
        if (Objects.nonNull(user) && user.getRole().equals(UserRole.ADMIN)) {
            About about = aboutRepository.findById(aboutId).orElseThrow(
                    () -> new AboutNotFoundException("About not found"));
            aboutRepository.deleteById(aboutId);
            log.info("deleteById {}", about);

        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}