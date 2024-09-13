package org.java4me.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class ImageService {
    
    @Value("${app.image.bucket:C:/Users/Egor/IdeaProjects/Spring/images}")
    private final String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        var fullImgPath = Path.of(bucket, imagePath);

        try(content) {
            Files.createDirectories(fullImgPath.getParent());
            Files.write(fullImgPath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        var fullImgPath = Path.of(bucket, imagePath);

        return Files.exists(fullImgPath)
                ? Optional.of(Files.readAllBytes(fullImgPath))
                : Optional.empty();
    }
}
