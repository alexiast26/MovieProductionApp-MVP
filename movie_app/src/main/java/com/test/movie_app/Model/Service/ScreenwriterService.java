package com.test.movie_app.Model.Service;

import com.test.movie_app.Model.Repository.ScreenwriterRepository;
import com.test.movie_app.Model.Screenwriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenwriterService {
    private final ScreenwriterRepository screenwriterRepository;

    @Autowired
    public ScreenwriterService(ScreenwriterRepository screenwriterRepository) {
        this.screenwriterRepository = screenwriterRepository;
    }

    public List<Screenwriter> getScreenwriters() {
        return screenwriterRepository.findAll();
    }

    public void validateScreenwriter(Screenwriter screenwriter) {
        if (screenwriter.getName() == null || screenwriter.getName().isEmpty()) {
            throw new IllegalArgumentException("Screenwriter name cannot be empty");
        }
    }

    public void saveScreenwriter(Screenwriter screenwriter) {
        validateScreenwriter(screenwriter);
        screenwriterRepository.save(screenwriter);
    }

    public void deleteScreenwriter(Screenwriter screenwriter) {
        screenwriterRepository.delete(screenwriter);
    }

    public void updateScreenwriter(Screenwriter screenwriter) {
        if (screenwriterRepository.existsById(screenwriter.getScreenwriterId())){
            throw new IllegalArgumentException("Screenwriter does not exist");
        }
        validateScreenwriter(screenwriter);
        screenwriterRepository.save(screenwriter);
    }

    public Screenwriter getScreenwriterByName(String screenwriterName) {
        return screenwriterRepository.findScreenwriterByName(screenwriterName);
    }

    public Screenwriter getScreenwriterById(int screenwriterId) {
        return screenwriterRepository.findScreenwriterByScreenwriterId(screenwriterId);
    }
}
