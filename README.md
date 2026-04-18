# 🎬 Movie Production Management System (MVP Architecture)

## 📌 Project Overview
A desktop application designed for movie production management, implemented using the **MVP (Model-View-Presenter)** architectural pattern. This project demonstrates a clean separation between the user interface and business logic, ensuring that the UI remains a "Passive View" while the Presenter handles all orchestration.

## 🛠 Tech Stack
* **Language:** Java 17+
* **UI Framework:** JavaFX / Java Swing
* **Core Framework:** Spring Boot (Dependency Injection & Service Layer)
* **Persistence:** MySQL / PostgreSQL via JPA/Hibernate
* **Tools:** Maven, Lombok

## 🏗 Architectural Design (MVP)
The project follows the **Supervising Controller / Passive View** flavor of the MVP pattern:

* **Model:** Contains the data structures (Movies, Actors, etc.) and the Service layer that handles database transactions and business rules.
* **View:** An interface-driven UI component. It is "dumb" and only responsible for displaying data and forwarding user events (clicks, text input) to the Presenter.
* **Presenter:** The middleman. It subscribes to View events, fetches data from the Model, and manually updates the View. Unlike MVVM, there is no automatic data binding; the Presenter explicitly tells the View what to show.

## ✨ Key Features (Problem 17)
* **Production Management:** Full CRUD operations for Movies, Directors, Screenwriters, and Actors.
* **Active Mediation:** Presenter-led validation for all inputs, ensuring no incomplete movie records are persisted.
* **Multimedia Handling:** Support for associating up to 3 images per movie, managed via specialized service logic.
* **Relational Mapping:** Dynamic listing of actor filmographies and crew assignments through bidirectional entity relationships.
* **Decoupled Events:** UI events are handled via listener interfaces, allowing for easier unit testing of the Presenter logic without a headless UI.

## 🚀 Getting Started
1. **Prerequisites:** Java 17 and a running SQL instance.
2. **Database Setup:** Configure `src/main/resources/application.properties` with your credentials.
3. **Execution:** Run the application via `mvn spring-boot:run`.

Note: The images might not appear when running the app, so I recommend clearing them and adding them again (this can be done by pressing the **Modify** button). :)
