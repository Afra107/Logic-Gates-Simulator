//  OOP PROJECT: LOGIC GATES SIMULATOR
//  AFRA, CMS ID: 415007, BSCS-12-C

package project.logicgatesimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.util.Objects;

public class Main extends Application {
    protected static Stage stage;
    protected static Scene scene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage s) throws Exception {
        stage=s;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Start.fxml")));
        scene=new Scene(root);

        // ICON AND TITLE
        stage.setTitle("Logic Gate Simulator");
        Image icon= new Image(Objects.requireNonNull(getClass().getResourceAsStream("Images/icon.png")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

    }
}