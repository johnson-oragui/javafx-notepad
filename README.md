# JavaFX Notepad Application

A modern notepad application.

## Features

- **Rich Text Editing**:
  - Basic text editing functionality
  - Zoom in/out support (Ctrl+Plus, Ctrl+Minus, Ctrl+0 to reset)
  - File operations (New, Open, Save)

- **Additional Features**:
  - Cross-platform (Windows, Mac, Linux)
  - Responsive UI
  - Keyboard shortcuts

## Prerequisites

- Java 17 or later
- Gradle 8+

## Installation

1. **Build & Run**:
   ```bash
   gradlew clean build
   gradlew :app:run
   ```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── org/
│   │       └── notepad/
│   │           ├── App.java                # Main application class
│   │           ├── controllers/            # All controllers
│   └── resources/
│       ├── main.fxml          # FXML files
│       └── config.properties               # Configuration
├── test/
│   └── java/                              # Test classes
```

## Key Keyboard Shortcuts

| Function        | Shortcut           |
|-----------------|--------------------|
| New File        | Ctrl+N             |
| Open File       | Ctrl+O             |
| Save File       | Ctrl+S             |
| Exit            | Ctrl+Q             |
| Cut             | Ctrl+X             |
| Copy            | Ctrl+C             |
| Paste           | Ctrl+V             |
| Zoom In         | Ctrl+Plus          |
| Zoom Out        | Ctrl+Minus         |
| Reset Zoom      | Ctrl+0             |

## Dependencies

- JavaFX 20
- JUnit 5 (for testing)

## Troubleshooting

**UI Rendering Problems**:
   - Clean and rebuild project
   - Verify JavaFX is properly installed


---
## DEMO
(notepad demo)[https://github.com/user-attachments/assets/c92c392a-3730-4e70-aab0-503d61247a15]
---
## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

MIT License
