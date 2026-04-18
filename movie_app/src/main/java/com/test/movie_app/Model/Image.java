package com.test.movie_app.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "image")
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int imageId;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
