package com.test.movie_app.Model.Service;

import com.test.movie_app.Model.Director;
import com.test.movie_app.Model.Repository.DirectorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Director> getDirectors() {
        return directorRepository.findAll();
    }

    public void validateDirector(Director director) {
        if (director.getName() == null || director.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Director name cannot be empty");
        }
    }

    public void saveDirector(Director director) {
        validateDirector(director);
        directorRepository.save(director);
    }

    public void deleteDirector(Director director) {
        directorRepository.delete(director);
    }

    public Director getDirectorByName(String directorName) {
        return directorRepository.findDirectorByName(directorName);
    }

    @Transactional
    public void updateDirector(Director director) {
        if (directorRepository.existsById(director.getDirectorId())){
            throw new IllegalArgumentException("Director does not exist");
        }
        validateDirector(director);
        directorRepository.save(director);
    }

    public Director getDirectorById(int directorId) {
        return directorRepository.findDirectorByDirectorId(directorId);
    }
}
