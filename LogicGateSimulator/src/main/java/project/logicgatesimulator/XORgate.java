package project.logicgatesimulator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.util.Objects;

public class XORgate extends Component{

    public XORgate(){}
    public XORgate(MouseEvent event){

        gateImage = new Image(Objects.requireNonNull(getClass().getResource("Images/XOR_Gate.png").toExternalForm()));
        gateImageView = new ImageView(gateImage);
        gateImageView.setPreserveRatio(true);
        gateImageView.setFitHeight(53);
        gateImageView.setFitWidth(118);
        gateImageView.setLayoutX(event.getSceneX());
        gateImageView.setLayoutY(event.getSceneY());
        // Set the user data of the ImageView to this object
        this.gateImageView.setUserData(this);

        // Add event handlers for dragging and dropping the gate image
        gateImageView.setOnMouseClicked(this::handleImageClicked);
        gateImageView.setOnMousePressed(this::handleImagePressed);
        gateImageView.setOnMouseDragged(this::handleImageDragged);
        gateImageView.setOnMouseReleased(this::handleImageReleased);

    }

    public boolean getOutputValue(){
        output = (!input1 && input2) || (input1 && !input2);
        return output;
    }
}
