package az.office.officenter.controller;

import az.office.officenter.dto.AboutDto;
import az.office.officenter.service.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/about")
public class AboutController {
    private final AboutService aboutService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<AboutDto> createAbout(@RequestBody AboutDto aboutRequest,
                                                @PathVariable Long userId) {
        return aboutService.add(aboutRequest, userId);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<AboutDto> updateAbout(@RequestBody AboutDto aboutRequest,
                                                     @PathVariable Long userId) {
        return aboutService.update(aboutRequest, userId);
    }

    @DeleteMapping("/{userId}/delete/{aboutId}")
    public void deleteAbout(@PathVariable Long userId,
                            @PathVariable Long aboutId) {
        aboutService.deleteById(userId, aboutId);
    }

}
