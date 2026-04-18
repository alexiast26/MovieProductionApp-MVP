package com.test.movie_app.Config;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApp extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        this.context = new SpringApplicationBuilder().sources(JavaFxApp.class).run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage){
        //anunta spring ca fereastra e gata
        context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop(){
        context.close();
        Platform.exit();
    }
}
