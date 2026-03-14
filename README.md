
# 🤖 MockLLM Wrapper Chatbot

A **Spring Boot** application designed as a wrapper for LLM interactions. It enforces strict response constraints (one-word answers) and provides a stateful terminal-based command interface.

## 🚀 Overview
This system follows the **Adapter Pattern** to wrap an LLM client. It intercepts user queries, applies prompt engineering to ensure brevity, and manages conversation states using in-memory data structures.

### Key Features
* **MockLLMClient:** Decoupled client that forces "One-Word" answers via internal logic.
* **Terminal UI:** A native CLI experience using `CommandLineRunner` for real-time interaction.
* **Command Router:** Strict validation for system commands (starting with `/`).
* **Stateful Conversations:** Automatic session management and thread-based history tracking.

---

## 🛠️ Setup & Installation

### Prerequisites
* **Java 17** or higher
* **Maven 3.6+**
* **Network Note:** If you are in a corporate environment, ensure your `settings.xml` is not blocking Maven Central.

### Build the Project
```bash
mvn clean install

```

---

## 🏃 How to Run

The application starts in **Interactive Mode** directly in your terminal. No external API tools (like Postman) are required.

```bash
mvn spring-boot:run

```

---

## ⌨️ Command Interface

Once the terminal displays `User [session-id]>`, you can use the following:

### 1. System Commands

Validations are in place to ensure only recognized commands are executed.

| Command | Action |
| --- | --- |
| `/new` | Starts a fresh conversation with a new unique ID. |
| `/list` | Displays all conversation IDs and their auto-generated titles. |
| `/history` | Shows the full transcript of the current active session. |
| `/exit` | Terminates the application. |

### 2. Regular Queries

Any input **not** starting with `/` is treated as a question.

* **Input:** `What is the capital of France?`
* **Output:** `Response is: Paris`

---

## 🏗️ Technical Design

### Project Structure

* **Controller:** `ChatController` — Exposes REST endpoints for external `curl` calls.
* **Service:** `ChatService` — The core logic; handles command routing and session history.
* **Client:** `MockLLMClient` — Implements the `LLMClient` interface to format responses.
* **CLI:** `TerminalInterface` — Manages the `System.in` loop for the terminal UI.

### Data Model

* **Conversation:** Stores a `UUID`, a `Title` (derived from the first word of the first prompt), and a list of messages.
* **ChatMessage:** Stores the query, response, and timestamp.

---

## 🔮 Future Enhancements

### 1. Persistence Layer

* **Current:** In-memory `ConcurrentHashMap`.
* **Future:** Integrate **Spring Data JPA** with an **H2** or **PostgreSQL** database to persist conversations across restarts.

### 2. Live LLM Integration

* **Current:** Hardcoded simulation logic.
* **Future:** Implement `WebClient` to connect to real APIs like **Google Gemini** or **OpenAI** using their free-tier endpoints.

### 3. Intelligent Titling

* **Current:** First-word heuristic.
* **Future:** Use the LLM to generate a meaningful 3-5 word title based on the context of the first few messages.

### 4. Advanced CLI (JLine)

* **Current:** Basic `Scanner` input.
* **Future:** Use the **JLine library** to add command auto-completion (tab-complete), ANSI colored output, and arrow-key history navigation.
