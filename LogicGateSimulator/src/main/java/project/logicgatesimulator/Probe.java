package project.logicgatesimulator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class Probe extends Component{

    public Probe(){}
    public Probe(MouseEvent event){

        gateImage = new Image(Objects.requireNonNull(getClass().getResource("Images/probe.png").toExternalForm()));
        gateImageView = new ImageView(gateImage);
        gateImageView.setPreserveRatio(true);
        gateImageView.setFitHeight(30);
        gateImageView.setFitWidth(30);
        gateImageView.setLayoutX(event.getSceneX());
        gateImageView.setLayoutY(event.getSceneY());

        // Add event handlers for dragging and dropping the gate image
        gateImageView.setOnMouseClicked(this::handleImageClicked);
        gateImageView.setOnMousePressed(this::handleImagePressed);
        gateImageView.setOnMouseDragged(this::handleImageDragged);
        gateImageView.setOnMouseReleased(this::handleImageReleased);

        // Set the user data of the ImageView to this object
        this.gateImageView.setUserData(this);
    }

    public boolean getOutputValue(){
        output = input1;
        return input1;
    }
}
