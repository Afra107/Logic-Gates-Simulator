package project.logicgatesimulator;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import java.util.Objects;

public abstract class Component{

    protected ImageView  gateImageView;
    protected Image gateImage;
    double xOffset = 0;
    double yOffset = 0;
    boolean input1, input2, output;


    // Event handlers for dragging and dropping the gate image
    void handleImageClicked(MouseEvent event) {
        if(event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY && (event.getSource() instanceof ImageView)) {
            ImageView gateImageView = (ImageView) event.getSource();
            Image gateImage = gateImageView.getImage();

            if (gateImage.getUrl().contains("Images/toggle-0.png")) {
                gateImage = new Image(Objects.requireNonNull(getClass().getResource("Images/toggle-1.png").toExternalForm()));
                gateImageView.setImage(gateImage);

            } else if (gateImage.getUrl().contains("Images/toggle-1.png")) {
                gateImage = new Image(Objects.requireNonNull(getClass().getResource("Images/toggle-0.png").toExternalForm()));
                gateImageView.setImage(gateImage);
            }
            Wire.simulator();
        }
        if(event.getButton() == MouseButton.SECONDARY && (event.getSource() instanceof ImageView | event.getSource() instanceof Line)){

            // create a context menu
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteMenuItem = new MenuItem("Delete");
            deleteMenuItem.setOnAction(e -> handleDelete(contextMenu));  // Set the action for the delete menu item
            // add 'Delete' menu item to the context menu
            contextMenu.getItems().add(deleteMenuItem);
            contextMenu.show((Node) event.getSource(), event.getScreenX(), event.getScreenY());
        }
    }

    void handleImagePressed(MouseEvent event) {
        ImageView gateImageView = (ImageView) event.getSource();
        if (gateImageView != null) {
            gateImageView.setMouseTransparent(false);
            xOffset = event.getSceneX() - gateImageView.getBoundsInParent().getMinX();
            yOffset = event.getSceneY() - gateImageView.getBoundsInParent().getMinY();
        }
    }

    void handleImageDragged(MouseEvent event) {
        ImageView gateImageView = (ImageView) event.getSource();
        gateImageView.setLayoutX(event.getSceneX() - xOffset);
        gateImageView.setLayoutY(event.getSceneY() - yOffset);
    }

    void handleImageReleased(MouseEvent event) {
        ImageView gateImageView = (ImageView) event.getSource();
        gateImageView.setMouseTransparent(false);
        gateImageView.setVisible(true);
        gateImageView.setLayoutX(event.getSceneX() - xOffset);
        gateImageView.setLayoutY(event.getSceneY() - yOffset);
    }

    public void enableEventListeners(Pane pane) {
        pane.getChildren().forEach(node -> {
            if (node instanceof ImageView imageView) {
                imageView.setOnMousePressed(this::handleImagePressed);
                imageView.setOnMouseDragged(this::handleImageDragged);
                imageView.setOnMouseReleased(this::handleImageReleased);
            }
            if (node instanceof Line wireline){
                wireline.setOnMouseClicked(this::handleImageClicked);
            }
        });
    }

    void handleDelete(ContextMenu contextMenu) {
        Node selectedNode = contextMenu.getOwnerNode();
        if (selectedNode instanceof ImageView) {
            // remove the selected ImageView from its parent
            ((Pane) selectedNode.getParent()).getChildren().remove(selectedNode);

        } else if (selectedNode instanceof Line) {
            // remove the selected Line from its parent
            ((Pane) selectedNode.getParent()).getChildren().remove(selectedNode);

            // remove the ending and starting points of line from the 'points' array of Wire class
            Wire wire = (Wire) selectedNode.getUserData();
            int index1 = Wire.points.indexOf(wire.sp);
            if (index1 != -1)
                Wire.points.remove(index1);

            int index2 = Wire.points.indexOf(wire.ep);
            if (index2 != -1)
                Wire.points.remove(index2);

            // remove the line from the 'allWires' array of Wire class
            Wire.allWires.remove(wire);
        }
    }

    public abstract boolean getOutputValue();
}
