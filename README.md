# Website Backend (Legacy)

This project was the backend service for a previous version of my personal website, [shenderov.me](https://shenderov.me).

It was built as a learning project to explore basic authorization and to further practice backend development with Java and Spring Boot.  
**The stack was likely overkill for a personal site, but the main goal was hands-on learning and experimentation.**

> **Note:**  
> This backend, along with its companion frontend ([website-frontend](https://github.com/shenderov/website-frontend)), has been **fully replaced** by my new static site: [shenderov.github.io](https://github.com/shenderov/shenderov.github.io).  
> This project is now left for reference and future experiments only.

---

## 🚀 Features

- RESTful API for contact form submission and other site features
- Basic email sending via Spring Mail
- Experimental support for simple user authorization
- Modular architecture for adding endpoints
- MongoDB for data storage
- Configuration via `application.properties`

---

## 🛠️ Tech Stack

- Java 8+
- Spring Boot
- Embedded Tomcat
- Spring Mail
- MongoDB
- Gradle

---

## 📁 Project Structure

<pre>
website-backend/
├── src/
│   ├── main/
│   │   ├── java/              # Application source code
│   │   └── resources/         # Config, email templates
│   └── test/                  # Tests
├── build.gradle               # Gradle config
└── ...
</pre>

---

## ⚡ Getting Started

1. **Clone the repository:**
    ```bash
    git clone https://github.com/shenderov/website-backend.git
    cd website-backend
    ```

2. **Configure your email settings:**  
   Edit `src/main/resources/application.properties` using the provided example.

3. **Build and run:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
   The server runs on [http://localhost:8080](http://localhost:8080) by default.

---

## 🌐 Related Projects

- **Frontend:**  
  [website-frontend](https://github.com/shenderov/website-frontend) — AngularJS/Bootstrap single-page app for this backend and the admin panel

- **Current site (replacement):**  
  [shenderov.github.io](https://github.com/shenderov/shenderov.github.io) — The new, static version of [shenderov.me](https://shenderov.me) (no backend required)

---

## ⚠️ Disclaimer

This project is no longer actively maintained.  
It is left public for reference, learning, and future backend experiments.

---

## 📝 License

This project is licensed under the [MIT License](LICENSE).
