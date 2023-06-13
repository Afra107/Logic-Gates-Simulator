package project.logicgatesimulator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    protected TreeView<String> SelectionTree;
    @FXML
    protected Pane Pane;
    @FXML
    protected ImageView selectedImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            TreeItem<String> rootItem = new TreeItem<>("Add Component");
            TreeItem<String> branchItem1 = new TreeItem<>("Add New Gate");
            TreeItem<String> branchItem2 = new TreeItem<>("Add Logic Toggle");
            TreeItem<String> branchItem3 = new TreeItem<>("Add Logic Probe");
            TreeItem<String> branchItem4 = new TreeItem<>("Add Wire");
            TreeItem<String> leafItem1 = new TreeItem<>("AND Gate");
            TreeItem<String> leafItem2 = new TreeItem<>("OR Gate");
            TreeItem<String> leafItem3 = new TreeItem<>("NOT Gate");
            TreeItem<String> leafItem4 = new TreeItem<>("NAND Gate");
            TreeItem<String> leafItem5 = new TreeItem<>("NOR Gate");
            TreeItem<String> leafItem6 = new TreeItem<>("XOR Gate");
            TreeItem<String> leafItem7 = new TreeItem<>("XNOR Gate");

            branchItem1.getChildren().addAll(leafItem1, leafItem2, leafItem3, leafItem4, leafItem5, leafItem6, leafItem7);
            rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3, branchItem4);
            SelectionTree.setRoot(rootItem);
            SelectionTree.setShowRoot(false);
        }
        catch(NullPointerException ignored){
        }

    }

    @FXML
    private void handleGateSelection(MouseEvent event) {
        TreeItem<String> selectedGate = SelectionTree.getSelectionModel().getSelectedItem();
        if (selectedGate != null) {
            // Load the gate's image from your computer
            String gateName = selectedGate.getValue();

            if (Objects.equals(gateName, "AND Gate")) {
                ANDgate gate=new ANDgate(event);
                Pane.getChildren().add(gate.gateImageView);
                setSelectedImageView(gate.gateImage);
            }
            else if (Objects.equals(gateName, "OR Gate")) {
                ORgate gate = new ORgate(event);
                Pane.getChildren().add(gate.gateImageView);
                setSelectedImageView(gate.gateImage);
            }
            else if (Objects.equals(gateName, "NOT Gate")) {
                NOTgate gate = new NOTgate(event);
                Pane.getChildren().add(gate.gateImageView);
                setSelectedImageView(gate.gateImage);
            }
            else if (Objects.equals(gateName, "NAND Gate")) {
                NANDgate gate = new NANDgate(event);
                Pane.getChildren().add(gate.gateImageView);
                setSelectedImageView(gate.gateImage);
            }
            else if (Objects.equals(gateName, "NOR Gate")) {
                NORgate gate = new NORgate(event);
                Pane.getChildren().add(gate.gateImageView);
                setSelectedImageView(gate.gateImage);
            }
            else if (Objects.equals(gateName, "XOR Gate")) {
                XORgate gate = new XORgate(event);
                Pane.getChildren().add(gate.gateImageView);
                setSelectedImageView(gate.gateImage);
            }
            else if (Objects.equals(gateName, "XNOR Gate")) {
                XNORgate gate = new XNORgate(event);
                Pane.getChildren().add(gate.gateImageView);
                setSelectedImageView(gate.gateImage);
            }
            else if (Objects.equals(gateName, "Add Logic Toggle")) {
                Toggle toggle = new Toggle(event);
                Pane.getChildren().add(toggle.gateImageView);
                setSelectedImageView(toggle.gateImage);
            }
            else if (Objects.equals(gateName, "Add Logic Probe")) {
                Probe probe = new Probe(event);
                Pane.getChildren().add(probe.gateImageView);
                setSelectedImageView(probe.gateImage);
            }
            else if (Objects.equals(gateName, "Add Wire")) {
                new Wire(event,Pane);
            }
        }
    }
    public void selectItem(){
    }
    public void setSelectedImageView(Image image){
        selectedImageView.setImage(image);
        if(image.getUrl().contains("Images/toggle-0.png") | image.getUrl().contains("Images/toggle-1.png") | image.getUrl().contains("Images/probe-0.png") | image.getUrl().contains("Images/probe-1.png") | image.getUrl().contains("Images/probe.png"))
        {
            selectedImageView.setFitHeight(25);
            selectedImageView.setFitWidth(25);
            selectedImageView.setLayoutX(78);
            selectedImageView.setLayoutY(76);
        }
        else {
            selectedImageView.setFitHeight(60);
            selectedImageView.setFitWidth(99);
            selectedImageView.setLayoutX(41);
            selectedImageView.setLayoutY(58);
        }
    }

    public void switchToSimulator(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Simulator1.fxml")));
        Main.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Main.scene.setRoot(root);
        Main.stage.setScene(Main.scene);
        Main.stage.show();
    }
    public void switchToMainScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Start.fxml")));
        Main.scene.setRoot(root);
        Main.stage.setScene(Main.scene);
        Main.stage.show();
    }
    public void exitProgram(ActionEvent event){
        System.exit(0);
    }

    public void clearAll(ActionEvent e){
        Pane.getChildren().clear();
    }

}

