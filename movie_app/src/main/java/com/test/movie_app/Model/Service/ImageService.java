package com.test.movie_app.Model.Service;

import com.test.movie_app.Model.Image;
import com.test.movie_app.Model.Repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(Image image) {
        if (image.getImgUrl() == null || image.getImgUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be empty");
        }
        imageRepository.save(image);
    }

    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }

    public Image getImageByUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL is null or empty");
        }

        Image img = imageRepository.getImageByImgUrl(url);
        if (img == null) {
            throw new IllegalArgumentException("Image not found");
        }
        return img;
    }

    @Transactional
    public void deleteImagesByMovieId(int movieId) {
        imageRepository.deleteByMovie_MovieId(movieId);
    }
}
