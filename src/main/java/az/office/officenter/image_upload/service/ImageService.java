package az.office.officenter.image_upload.service;

import az.office.officenter.exception.ContextRuntimeException;
import az.office.officenter.image_upload.domain.Image;
import az.office.officenter.image_upload.repository.ImageRepository;
import az.office.officenter.image_upload.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public String uploadImage(MultipartFile imageFile) throws IOException {
        String randomImageName = UUID.randomUUID().toString();
        var imageToSave = Image.builder()
                .name(randomImageName)
                .type(imageFile.getContentType())
                .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                .build();
        imageRepository.save(imageToSave);
        return randomImageName;
    }

    public byte[] downloadImage(String imageName) {
        Optional<Image> dbImage = imageRepository.findByName(imageName);

        return dbImage.map(image -> {
            try {
                return ImageUtils.decompressImage(image.getImageData());
            } catch (DataFormatException | IOException exception) {
                throw new ContextRuntimeException("Error of image download");
            }
        }).orElse(null);
    }

}