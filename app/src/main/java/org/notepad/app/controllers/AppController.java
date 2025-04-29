package org.notepad.app.controllers;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppController {
  private File currentFile;
  private Stage primaryStage;
  private Boolean isModified = false;

  private Zoom zoom;
  @FXML
  private MenuItem zoomInMenuItem;
  @FXML
  private MenuItem zoomOutMenuItem;
  @FXML
  private MenuItem resetZoomMenuItem;
  @FXML
  private TextArea textArea;

  public AppController() {
  }

  public void setPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void setIsModified(Boolean ans) {
    this.isModified = ans;
  }

  // FMXL OPERATIONS
  @FXML
  public void initialize() {
    textArea.textProperty().addListener((obs, oldText, newtex) -> {
      setIsModified(true);
    });

    zoom = new Zoom(textArea);
    // Set up menu item actions
    zoomInMenuItem.setOnAction(e -> zoom.handleZoomIn());
    zoomOutMenuItem.setOnAction(e -> zoom.handleZoomOut());
    resetZoomMenuItem.setOnAction(e -> zoom.handleResetZoom());

    // Set keyboard shortcuts
    setupKeyboardShortcuts();
  }

  private void setupKeyboardShortcuts() {
    // Menu item accelerators
    zoomInMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.PLUS, KeyCombination.SHORTCUT_DOWN));
    zoomOutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.SHORTCUT_DOWN));
    resetZoomMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.SHORTCUT_DOWN));

    // Direct text area shortcuts
    textArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.isShortcutDown()) {
        if (event.getCode() == KeyCode.PLUS || event.getCode() == KeyCode.ADD) {
          zoom.handleZoomIn();
          event.consume();
        } else if (event.getCode() == KeyCode.MINUS || event.getCode() == KeyCode.SUBTRACT) {
          zoom.handleZoomOut();
          event.consume();
        } else if (event.getCode() == KeyCode.DIGIT0) {
          zoom.handleResetZoom();
          event.consume();
        }
      }
    });
  }

  @FXML
  private void handleNew() {
    textArea.clear();
    currentFile = null;
    updateTitle();
  }

  @FXML
  private void handleOpen() {
    FileChooser fileChooser = new FileChooser();

    fileChooser.setTitle("Open Text File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

    File file = fileChooser.showOpenDialog(primaryStage);
    if (file != null) {
      try {
        String content = Files.readString(file.toPath());
        textArea.setText(content);
        currentFile = file;
      } catch (Exception e) {
        showError("Error opening file", e.getMessage());
      }
    }
  }

  @FXML
  private void handleSave() {
    if (currentFile == null) {
      handleSaveAs();
      return;
    }
    saveToFile(currentFile);
  }

  @FXML
  private void handleSaveAs() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Text File");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Text Files", "*.txt"));

    File file = fileChooser.showSaveDialog(primaryStage);
    if (file != null) {
      saveToFile(file);
    }
  }

  @FXML
  private void handleExit() {
    if (confirmExit()) {
      primaryStage.close();
    }
  }

  @FXML
  private void handleCut() {
    textArea.cut();
  }

  @FXML
  private void handleCopy() {
    textArea.copy();
  }

  @FXML
  private void handlePaste() {
    textArea.paste();
  }

  // ------------ helper methods ------------
  private void updateTitle() {
    String title = "NotePad";
    if (currentFile != null) {
      title += " - " + currentFile.getName();
    }
    primaryStage.setTitle(title);
  }

  private void showError(String title, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void saveToFile(File file) {
    try {
      Files.writeString(file.toPath(), textArea.getText());
      currentFile = file;
      setIsModified(false);
      updateTitle();
    } catch (Exception e) {
      showError("Error saving file", e.getMessage());
    }
  }

  public Boolean confirmExit() {
    if (!isModified) {
      return true;
    }
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Unsaved CHanges");
    alert.setHeaderText("You have unsaved changes!");
    alert.setContentText("Do you want to save before exiting?");

    ButtonType saveButton = new ButtonType("Save");
    ButtonType discardButton = new ButtonType("Discard");
    ButtonType cancelButton = new ButtonType("cancel", ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(saveButton, discardButton, cancelButton);

    Optional<ButtonType> result = alert.showAndWait();

    if (result.orElse(null) == saveButton) {
      handleSave();
      return true;
    }
    if (result.orElse(null) == discardButton) {
      return true;
    }
    return false;
  }

}
