package org.notepad.app.controllers;

import java.util.prefs.Preferences;

import javafx.scene.control.TextArea;

public class Zoom {

  private TextArea textArea;

  private double currentZoom = 1.0;
  private final double ZOOM_FACTOR = 1.1;
  private final double MIN_ZOOM = 0.5;
  private final double MAX_ZOOM = 3.0;
  private final double DEFAULT_ZOOM = 1.0;

  private Preferences prefs = Preferences.userNodeForPackage(AppController.class);

  public Zoom() {
  }

  public Zoom(TextArea textArea) {
    this.textArea = textArea;
    loadZoomPreference();
  }

  public void initialize() {
    loadZoomPreference();
  }

  public void handleZoomIn() {
    if (currentZoom < MAX_ZOOM) {
      currentZoom *= ZOOM_FACTOR;
    }
    applyZoom();
    saveZoomPreference();
  }

  public void handleZoomOut() {
    if (currentZoom > MIN_ZOOM) {
      currentZoom /= ZOOM_FACTOR;
      applyZoom();
      saveZoomPreference();
    }
  }

  public void handleResetZoom() {
    currentZoom = DEFAULT_ZOOM;
    applyZoom();
    saveZoomPreference();
  }

  private void applyZoom() {
    textArea.setStyle("-fx-font-size: " + (13 * currentZoom) + "px;");
  }

  private void saveZoomPreference() {
    prefs.putDouble("textZoom", currentZoom);
  }

  private void loadZoomPreference() {
    currentZoom = prefs.getDouble("textZoom", DEFAULT_ZOOM);
    applyZoom();
  }
}
