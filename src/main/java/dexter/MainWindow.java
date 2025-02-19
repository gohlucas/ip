package dexter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Dexter dexter;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/debbie.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/dexter.jpeg"));

    /**
     * Initialises the GUI and prints welcome message
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog("Hello! I'm Dexter. How can I help you today?", dukeImage));
    }

    /** Injects the Dexter instance */
    public void setDexter(Dexter d) {
        dexter = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = dexter.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }
}
