package project.logicgatesimulator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.util.Objects;

public class ANDgate extends Component{

    public ANDgate(){}
    public ANDgate(MouseEvent event){

        gateImage = new Image(Objects.requireNonNull(getClass().getResource("Images/AND_Gate.png").toExternalForm()));
        gateImageView = new ImageView(gateImage);
        gateImageView.setPreserveRatio(true);
        gateImageView.setFitHeight(60);
        gateImageView.setFitWidth(100);
        gateImageView.setLayoutX(200);
        gateImageView.setLayoutY(200);
        // Set the user data of the ImageView to this object
        this.gateImageView.setUserData(this);

        // Add event handlers for dragging and dropping the gate image
        gateImageView.setOnMouseClicked(this::handleImageClicked);
        gateImageView.setOnMousePressed(this::handleImagePressed);
        gateImageView.setOnMouseDragged(this::handleImageDragged);
        gateImageView.setOnMouseReleased(this::handleImageReleased);
    }

    public boolean getOutputValue(){
        output = input1 && input2;
        return output;
    }
}
