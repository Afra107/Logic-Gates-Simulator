package project.logicgatesimulator;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Wire extends Component{
    Pane pane;
    ImageView imageView;
    MouseEvent event;
    private ImageView startNode, endNode;  // Nodes from where wire starts and ends
    private Component inNode, outNode;    // Node from where signal enters and leaves
    protected Line wireLine = new Line(); // create a single line object
    private double startX, startY, endX, endY; // starting and ending coordinates of wire
    private boolean listenersActive = true;
    protected Point2D sp , ep; // start point, end point of wire
    private double startgateX, startgateY, endgateX, endgateY, gateWidth, gateHeight, mouseX, mouseY;
    protected static List<Point2D> points = new ArrayList<>(); // list to store starting and ending points of wires
    protected static List<Wire> allWires = new ArrayList<>();  // add a static list to keep track of all Wire objects
    private boolean signalVal;

    public Wire(MouseEvent e, Pane pane) {

        wireLine.setStrokeWidth(3);
        wireLine.setStroke(Color.BLACK);
        pane.getChildren().add(wireLine);
        // Set the user data of the ImageView to this object
        this.wireLine.setUserData(this);

        wireLine.setOnMouseClicked(this::handleImageClicked);

        // Add an event handler to listen for mouse clicks on the gate images
        pane.getChildren().forEach(node -> {
            if (node instanceof ImageView imageView) {

                imageView.setOnMousePressed(event -> {
                    if (startNode == null && listenersActive) {

                        // check if the wire is being connected to the correct input or output position
                        startgateX = imageView.getLayoutX();
                        startgateY = imageView.getLayoutY();
                        gateWidth = imageView.getFitWidth();
                        gateHeight = imageView.getFitHeight();

                        mouseX = event.getX() + startgateX;
                        mouseY = event.getY() + startgateY;

                        // check if the mouse click is within the bounds of the input or output position
                        if (imageView.getImage().getUrl().contains("Images/AND_Gate.png") | imageView.getImage().getUrl().contains("Images/OR_Gate.png") | imageView.getImage().getUrl().contains("Images/NAND_Gate.png") | imageView.getImage().getUrl().contains("Images/NOR_Gate.png") | imageView.getImage().getUrl().contains("Images/XOR_Gate.png") | imageView.getImage().getUrl().contains("Images/XNOR_Gate.png")) {
                            // AND gate has two inputs and one output
                            if (mouseY > startgateY && mouseY < startgateY + gateHeight / 3) {
                                // connect to input 1
                                startX = startgateX;
                                startY = startgateY + gateHeight / 4;
                                sp = new Point2D(startX,startY);
                                if (!(points.contains(sp))){
                                    startNode = imageView;
                                    wireLine.setStartX(startX);
                                    wireLine.setStartY(startY);
                                    wireLine.setEndX(startX);
                                    wireLine.setEndY(startY);
                                    points.add(sp);
                                    pane.setCursor(Cursor.CROSSHAIR);
                                    if (imageView.getImage().getUrl().contains("Images/AND_Gate.png"))
                                        outNode = (ANDgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/OR_Gate.png"))
                                        outNode = (ORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/NAND_Gate.png"))
                                        outNode = (NANDgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/NOR_Gate.png"))
                                        outNode = (NORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/XOR_Gate.png"))
                                        outNode = (XORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/XNOR_Gate.png"))
                                        outNode = (XNORgate) imageView.getUserData();
                                }
                                else
                                    showWarning("More than one wire cannot be connected to the same input terminal!");
                            } else if (mouseY > startgateY + 2 * gateHeight / 3 && mouseY < startgateY + gateHeight) {
                                // connect to input 2
                                startX = startgateX;
                                startY = startgateY + 3 * gateHeight / 4;
                                sp = new Point2D(startX,startY);
                                if (!(points.contains(sp))){
                                    startNode = imageView;
                                    wireLine.setStartX(startX);
                                    wireLine.setStartY(startY);
                                    wireLine.setEndX(startX);
                                    wireLine.setEndY(startY);
                                    points.add(sp);
                                    pane.setCursor(Cursor.CROSSHAIR);
                                    if (imageView.getImage().getUrl().contains("Images/AND_Gate.png"))
                                        outNode = (ANDgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/OR_Gate.png"))
                                        outNode = (ORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/NAND_Gate.png"))
                                        outNode = (NANDgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/NOR_Gate.png"))
                                        outNode = (NORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/XOR_Gate.png"))
                                        outNode = (XORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/XNOR_Gate.png"))
                                        outNode = (XNORgate) imageView.getUserData();
                                }
                                else
                                    showWarning("More than one wire cannot be connected to the same input terminal!");
                            } else if (mouseX > startgateX && mouseX < startgateX + gateWidth) {
                                // connect to output
                                startX = startgateX + gateWidth;
                                startY = startgateY + gateHeight / 2;
                                startNode = imageView;
                                wireLine.setStartX(startX);
                                wireLine.setStartY(startY);
                                wireLine.setEndX(startX);
                                wireLine.setEndY(startY);
                                pane.setCursor(Cursor.CROSSHAIR);
                                if (imageView.getImage().getUrl().contains("Images/AND_Gate.png"))
                                    inNode = (ANDgate) imageView.getUserData();
                                else if (imageView.getImage().getUrl().contains("Images/OR_Gate.png"))
                                    inNode = (ORgate) imageView.getUserData();
                                else if (imageView.getImage().getUrl().contains("Images/NAND_Gate.png"))
                                    inNode = (NANDgate) imageView.getUserData();
                                else if (imageView.getImage().getUrl().contains("Images/NOR_Gate.png"))
                                    inNode = (NORgate) imageView.getUserData();
                                else if (imageView.getImage().getUrl().contains("Images/XOR_Gate.png"))
                                    inNode = (XORgate) imageView.getUserData();
                                else if (imageView.getImage().getUrl().contains("Images/XNOR_Gate.png"))
                                    inNode = (XNORgate) imageView.getUserData();
                            }
                        }
                        else if (imageView.getImage().getUrl().contains("Images/NOT_Gate.png")) {
                            // NOT gate has one input and one output
                            if (mouseY > startgateY + gateHeight / 3 && mouseY < startgateY + 2 * gateHeight / 3 && (mouseX-startgateX) < (gateWidth/2)) {
                                // connect to input
                                startX = startgateX;
                                startY = startgateY + gateHeight / 2;
                                sp = new Point2D(startX,startY);
                                if (!(points.contains(sp))){
                                    startNode = imageView;
                                    wireLine.setStartX(startX);
                                    wireLine.setStartY(startY);
                                    wireLine.setEndX(startX);
                                    wireLine.setEndY(startY);
                                    points.add(sp);
                                    pane.setCursor(Cursor.CROSSHAIR);
                                    outNode = (NOTgate) imageView.getUserData();
                                }
                                else
                                    showWarning("More than one wire cannot be connected to the same input terminal!");
                            }
                            else if (mouseX > startgateX && mouseX < startgateX + gateWidth && (mouseX-startgateX) > (gateWidth/2)) {
                                // connect to output
                                startX = startgateX + gateWidth;
                                startY = startgateY + gateHeight / 2;
                                startNode = imageView;
                                wireLine.setStartX(startX);
                                wireLine.setStartY(startY);
                                wireLine.setEndX(startX);
                                wireLine.setEndY(startY);
                                pane.setCursor(Cursor.CROSSHAIR);
                                inNode = (NOTgate) imageView.getUserData();

                            }
                        }
                        else if(imageView.getImage().getUrl().contains("Images/toggle-0.png") | imageView.getImage().getUrl().contains("Images/toggle-1.png")){
                            // connect to output
                            startNode = imageView;
                            startX = startgateX + gateWidth;
                            startY = startgateY + gateHeight / 2;
                            wireLine.setStartX(startX);
                            wireLine.setStartY(startY);
                            wireLine.setEndX(startX);
                            wireLine.setEndY(startY);
                            pane.setCursor(Cursor.CROSSHAIR);
                            inNode = (Toggle) imageView.getUserData();

                        }
                        else if(imageView.getImage().getUrl().contains("Images/probe-0.png") | imageView.getImage().getUrl().contains("Images/probe-1.png") | imageView.getImage().getUrl().contains("Images/probe.png")){

                            startX = startgateX;
                            startY = startgateY + gateHeight / 2;
                            sp = new Point2D(startX,startY);
                            if (!(points.contains(sp))){
                                startNode = imageView;
                                wireLine.setStartX(startX);
                                wireLine.setStartY(startY);
                                wireLine.setEndX(startX);
                                wireLine.setEndY(startY);
                                points.add(sp);
                                pane.setCursor(Cursor.CROSSHAIR);
                                outNode = (Probe) imageView.getUserData();
                            }
                            else
                                showWarning("More than one wire cannot be connected to the same input terminal!");
                        }
                    }
                });

                imageView.setOnMouseDragged(event -> {
                    if (startNode != null && listenersActive) {
                        endX = event.getX() + imageView.getLayoutX();
                        endY = event.getY() + imageView.getLayoutY();
                        wireLine.setEndX(endX);
                        wireLine.setEndY(endY);

                    }
                });

                imageView.setOnMouseReleased(event -> {
                    if (event.getSource() instanceof ImageView && listenersActive && endNode == null && !imageView.equals(startNode)) {
                        this.imageView = imageView;
                        this.pane = pane;
                        this.event = event;
                        // check if the wire is being connected to the correct input or output position
                        endgateX = imageView.getLayoutX();
                        endgateY = imageView.getLayoutY();
                        gateWidth = imageView.getFitWidth();
                        gateHeight = imageView.getFitHeight();

                        mouseX = event.getX() + endgateX;
                        mouseY = event.getY() + endgateY;

                        // check if the mouse click is within the bounds of the input or output position
                        if (imageView.getImage().getUrl().contains("Images/AND_Gate.png") | imageView.getImage().getUrl().contains("Images/OR_Gate.png") | imageView.getImage().getUrl().contains("Images/NAND_Gate.png") | imageView.getImage().getUrl().contains("Images/NOR_Gate.png") | imageView.getImage().getUrl().contains("Images/XOR_Gate.png") | imageView.getImage().getUrl().contains("Images/XNOR_Gate.png")) {
                            // AND gate has two inputs and one output
                            if (mouseY > endgateY && mouseY < endgateY + gateHeight / 3) {
                                // connect to input 1
                                endX = endgateX;
                                endY = endgateY + gateHeight / 4;
                                ep = new Point2D(endX,endY);
                                if (!(points.contains(ep))){
                                    endNode = imageView;
                                    wireLine.setEndX(endX);
                                    wireLine.setEndY(endY);
                                    pane.setCursor(Cursor.DEFAULT);
                                    listenersActive = false;
                                    allWires.add(this); // add this Wire object to the list of all Wire objects
                                    points.add(ep);
                                    if (imageView.getImage().getUrl().contains("Images/AND_Gate.png"))
                                        outNode = (ANDgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/OR_Gate.png"))
                                        outNode = (ORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/NAND_Gate.png"))
                                        outNode = (NANDgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/NOR_Gate.png"))
                                        outNode = (NORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/XOR_Gate.png"))
                                        outNode = (XORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/XNOR_Gate.png"))
                                        outNode = (XNORgate) imageView.getUserData();
                                    signalVal = inNode.getOutputValue();
                                    outNode.input1 = signalVal;

                                    // disable event listeners of wire class
                                    imageView.setOnMousePressed(null);
                                    imageView.setOnMouseDragged(null);
                                    imageView.setOnMouseReleased(null);
                                    // enable event listeners of Component class
                                    enableEventListeners(pane);
                                }
                                else
                                    showWarning("More than one wire cannot be connected to the same input terminal!");
                            } else if (mouseY > endgateY + 2 * gateHeight / 3 && mouseY < endgateY + gateHeight) {
                                // connect to input 2
                                endX = endgateX;
                                endY = endgateY + 3 * gateHeight / 4;
                                ep = new Point2D(endX,endY);
                                if (!(points.contains(ep))){
                                    endNode = imageView;
                                    wireLine.setEndX(endX);
                                    wireLine.setEndY(endY);
                                    pane.setCursor(Cursor.DEFAULT);
                                    listenersActive = false;
                                    allWires.add(this); // add this Wire object to the list of all Wire objects
                                    points.add(ep);
                                    if (imageView.getImage().getUrl().contains("Images/AND_Gate.png"))
                                        outNode = (ANDgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/OR_Gate.png"))
                                        outNode = (ORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/NAND_Gate.png"))
                                        outNode = (NANDgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/NOR_Gate.png"))
                                        outNode = (NORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/XOR_Gate.png"))
                                        outNode = (XORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/XNOR_Gate.png"))
                                        outNode = (XNORgate) imageView.getUserData();
                                    signalVal = inNode.getOutputValue();
                                    outNode.input2 = signalVal;

                                    // disable event listeners of wire class
                                    imageView.setOnMousePressed(null);
                                    imageView.setOnMouseDragged(null);
                                    imageView.setOnMouseReleased(null);
                                    // enable event listeners of Component class
                                    enableEventListeners(pane);
                                }
                                else
                                    showWarning("More than one wire cannot be connected to the same input terminal!");
                            } else if (mouseX > endgateX && mouseX < endgateX + gateWidth) {
                                // connect to output
                                if(outNode == null)
                                    showWarning("Output of one node cannot be connected to the output of another node.");
                                else {
                                    endX = endgateX + gateWidth;
                                    endY = endgateY + gateHeight / 2;
                                    endNode = imageView;
                                    wireLine.setEndX(endX);
                                    wireLine.setEndY(endY);
                                    pane.setCursor(Cursor.DEFAULT);
                                    listenersActive = false;
                                    allWires.add(this); // add this Wire object to the list of all Wire objects
                                    points.add(ep);
                                    if (imageView.getImage().getUrl().contains("Images/AND_Gate.png"))
                                        inNode = (ANDgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/OR_Gate.png"))
                                        inNode = (ORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/NAND_Gate.png"))
                                        inNode = (NANDgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/NOR_Gate.png"))
                                        inNode = (NORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/XOR_Gate.png"))
                                        inNode = (XORgate) imageView.getUserData();
                                    else if (imageView.getImage().getUrl().contains("Images/XNOR_Gate.png"))
                                        inNode = (XNORgate) imageView.getUserData();

                                   // disable event listeners of wire class
                                    imageView.setOnMousePressed(null);
                                    imageView.setOnMouseDragged(null);
                                    imageView.setOnMouseReleased(null);
                                    // enable event listeners of Component class
                                    enableEventListeners(pane);
                                }
                            }
                        }
                        else if (imageView.getImage().getUrl().contains("Images/NOT_Gate.png")) {
                            // NOT gate has one input and one output
                            if (mouseY > endgateY + gateHeight / 3 && mouseY < endgateY + 2 * gateHeight / 3 && (mouseX-endgateX) < (gateWidth/2)) {
                                // connect to input
                                endX = endgateX;
                                endY = endgateY + gateHeight / 2;
                                ep = new Point2D(endX,endY);
                                if (!(points.contains(ep))){
                                    endNode = imageView;
                                    wireLine.setEndX(endX);
                                    wireLine.setEndY(endY);
                                    pane.setCursor(Cursor.DEFAULT);
                                    listenersActive = false;
                                    allWires.add(this); // add this Wire object to the list of all Wire objects
                                    points.add(ep);
                                    outNode = (NOTgate) imageView.getUserData();
                                    signalVal = inNode.getOutputValue();
                                    outNode.input1 = signalVal;

                                    // disable event listeners of wire class
                                    imageView.setOnMousePressed(null);
                                    imageView.setOnMouseDragged(null);
                                    imageView.setOnMouseReleased(null);
                                    // enable event listeners of Component class
                                    enableEventListeners(pane);
                                }
                                else
                                    showWarning("More than one wire cannot be connected to the same input terminal!");
                            }
                            else if (mouseX > endgateX && mouseX < endgateX + gateWidth && (mouseX-endgateX) > (gateWidth/2)) {
                                // connect to output
                                if(outNode == null)
                                    showWarning("Output of one node cannot be connected to the output of another node.");
                                else {
                                    endX = endgateX + gateWidth;
                                    endY = endgateY + gateHeight / 2;
                                    endNode = imageView;
                                    wireLine.setEndX(endX);
                                    wireLine.setEndY(endY);
                                    pane.setCursor(Cursor.DEFAULT);
                                    listenersActive = false;
                                    allWires.add(this); // add this Wire object to the list of all Wire objects
                                    points.add(ep);
                                    inNode = (NOTgate) imageView.getUserData();

                                    // disable event listeners of wire class
                                    imageView.setOnMousePressed(null);
                                    imageView.setOnMouseDragged(null);
                                    imageView.setOnMouseReleased(null);
                                    // enable event listeners of Component class
                                    enableEventListeners(pane);
                                }
                            }
                        }
                        else if(imageView.getImage().getUrl().contains("Images/toggle-0.png") | imageView.getImage().getUrl().contains("Images/toggle-1.png")){
                            if(outNode == null)
                                showWarning("Output of one node cannot be connected to the output of another node.");
                            else {
                                // connect to output
                                endNode = imageView;
                                endX = endgateX + gateWidth;
                                endY = endgateY + gateHeight / 2;
                                wireLine.setEndX(endX);
                                wireLine.setEndY(endY);
                                pane.setCursor(Cursor.DEFAULT);
                                listenersActive = false;
                                allWires.add(this); // add this Wire object to the list of all Wire objects
                                inNode = (Toggle) imageView.getUserData();

                                // disable event listeners of wire class
                                imageView.setOnMousePressed(null);
                                imageView.setOnMouseDragged(null);
                                imageView.setOnMouseReleased(null);
                                // enable event listeners of Component class
                                enableEventListeners(pane);
                            }
                        }
                        else if(imageView.getImage().getUrl().contains("Images/probe-0.png") | imageView.getImage().getUrl().contains("Images/probe-1.png") | imageView.getImage().getUrl().contains("Images/probe.png")) {

                            endX = endgateX;
                            endY = endgateY + gateHeight / 2;
                            ep = new Point2D(endX, endY);
                            if (!(points.contains(ep))) {
                                endNode = imageView;
                                wireLine.setEndX(endX);
                                wireLine.setEndY(endY);
                                pane.setCursor(Cursor.DEFAULT);
                                listenersActive = false;
                                allWires.add(this); // add this Wire object to the list of all Wire objects
                                points.add(ep);
                                outNode = (Probe) imageView.getUserData();
                                signalVal = inNode.getOutputValue();
                                outNode.input1 = signalVal;

                                // disable event listeners of wire class
                                imageView.setOnMousePressed(null);
                                imageView.setOnMouseDragged(null);
                                imageView.setOnMouseReleased(null);
                                // enable event listeners of Component class
                                enableEventListeners(pane);
                            }
                            else
                                showWarning("More than one wire cannot be connected to the same input terminal!");
                        }
                    }
                });
            }
        });
    }// constructor ends

    private void updateStartDimensions(MouseEvent e, ImageView imageView) {
        if (startNode != null && endNode != null) {
            double deltaX = imageView.getLayoutX() - startgateX;
            double deltaY = imageView.getLayoutY() - startgateY;
            startX += deltaX;
            startY += deltaY;
            wireLine.setStartX(startX);
            wireLine.setStartY(startY);
            startgateX = imageView.getLayoutX();
            startgateY = imageView.getLayoutY();
        }
    }
    private void updateEndDimensions(MouseEvent e, ImageView imageView) {
        if (startNode != null && endNode != null) {
            double deltaX = imageView.getLayoutX() - endgateX;
            double deltaY = imageView.getLayoutY() - endgateY;
            endX += deltaX;
            endY += deltaY;
            wireLine.setEndX(endX);
            wireLine.setEndY(endY);
            endgateX = imageView.getLayoutX();
            endgateY = imageView.getLayoutY();
        }
    }
    @Override
    void handleImageDragged(MouseEvent event){
        ImageView gateImageView = (ImageView) event.getSource();
        gateImageView.setLayoutX(event.getSceneX() - xOffset);
        gateImageView.setLayoutY(event.getSceneY() - yOffset);
        // update dimensions of wires connected to this ImageView
        for (Wire wire : allWires) {
            if (wire.startNode == gateImageView) {
                int index = points.indexOf(wire.sp);
                wire.updateStartDimensions(event, gateImageView);
                wire.sp = new Point2D(wire.startX, wire.startY);
                if (index != -1)
                    points.set(index, wire.sp);
            }
            else if(wire.endNode == gateImageView){
                int index = points.indexOf(wire.ep);
                wire.updateEndDimensions(event, gateImageView);
                wire.ep = new Point2D(wire.endX, wire.endY);
                if (index != -1)
                    points.set(index, wire.ep);
            }
        }
    }
    @Override
    void handleImageReleased(MouseEvent event){
        ImageView gateImageView = (ImageView) event.getSource();
        gateImageView.setMouseTransparent(false);
        gateImageView.setVisible(true);
        gateImageView.setLayoutX(event.getSceneX() - xOffset);
        gateImageView.setLayoutY(event.getSceneY() - yOffset);
        // update dimensions of wires connected to this ImageView
        for (Wire wire : allWires) {
            if (wire.startNode == gateImageView) {
                int index = points.indexOf(wire.sp);
                wire.updateStartDimensions(event, gateImageView);
                wire.sp = new Point2D(wire.startX, wire.startY);
                if (index != -1)
                    points.set(index, wire.sp);
            }
            else if(wire.endNode == gateImageView){
                int index = points.indexOf(wire.ep);
                wire.updateEndDimensions(event, gateImageView);
                wire.ep = new Point2D(wire.endX, wire.endY);
                if (index != -1)
                    points.set(index, wire.ep);
            }
        }
    }

    public boolean getOutputValue(){
        return input1;
    }

    public static void simulator() {

        for (Wire wire  : allWires) {
            if (wire.inNode instanceof Toggle) {
                if(wire.inNode.getOutputValue())
                    wire.wireLine.setStroke(Color.RED);
                else
                    wire.wireLine.setStroke(Color.YELLOW);
                if (wire.outNode instanceof NOTgate | wire.outNode instanceof Probe){
                    wire.outNode.input1 = wire.inNode.getOutputValue();
                }
                else{
                    if(Objects.equals(wire.sp, new Point2D(wire.startNode.getLayoutX(), (wire.startNode.getLayoutY() + wire.startNode.getFitHeight() / 4))) | Objects.equals(wire.ep, new Point2D(wire.endNode.getLayoutX(), (wire.endNode.getLayoutY() + wire.endNode.getFitHeight() / 4))))
                        wire.outNode.input1 = wire.inNode.getOutputValue();
                    else if(Objects.equals(wire.sp, new Point2D(wire.startNode.getLayoutX(), (wire.startNode.getLayoutY() + 3 * wire.startNode.getFitHeight() / 4))) | Objects.equals(wire.ep, new Point2D(wire.endNode.getLayoutX(), (wire.endNode.getLayoutY() + 3 * wire.endNode.getFitHeight() / 4))))
                        wire.outNode.input2 = wire.inNode.getOutputValue();
                }
            }
        }

        int count =0;
        for (Wire wire  : allWires) {
            if (! (wire.inNode instanceof Toggle | wire.outNode instanceof Probe) ) {
                if(wire.inNode.getOutputValue())
                    wire.wireLine.setStroke(Color.RED);
                else
                    wire.wireLine.setStroke(Color.YELLOW);
                if (wire.outNode instanceof NOTgate){
                    wire.outNode.input1 = wire.inNode.getOutputValue();
                }
                else{
                    if(Objects.equals(wire.sp, new Point2D(wire.startNode.getLayoutX(), (wire.startNode.getLayoutY() + wire.startNode.getFitHeight() / 4))) | Objects.equals(wire.ep, new Point2D(wire.endNode.getLayoutX(), (wire.endNode.getLayoutY() + wire.endNode.getFitHeight() / 4))))
                        wire.outNode.input1 = wire.inNode.getOutputValue();
                    else if(Objects.equals(wire.sp, new Point2D(wire.startNode.getLayoutX(), (wire.startNode.getLayoutY() + 3 * wire.startNode.getFitHeight() / 4))) | Objects.equals(wire.ep, new Point2D(wire.endNode.getLayoutX(), (wire.endNode.getLayoutY() + 3 * wire.endNode.getFitHeight() / 4))))
                        wire.outNode.input2 = wire.inNode.getOutputValue();
                }
                count++;
            }
        }
        for(int i=1; i<count;i++){
            for (Wire wire  : allWires) {
                if (! (wire.inNode instanceof Toggle | wire.outNode instanceof Probe) ) {
                    if(wire.inNode.getOutputValue())
                        wire.wireLine.setStroke(Color.RED);
                    else
                        wire.wireLine.setStroke(Color.YELLOW);
                    if (wire.outNode instanceof NOTgate){
                        wire.outNode.input1 = wire.inNode.getOutputValue();
                    }
                    else{
                        if(Objects.equals(wire.sp, new Point2D(wire.startNode.getLayoutX(), (wire.startNode.getLayoutY() + wire.startNode.getFitHeight() / 4))) | Objects.equals(wire.ep, new Point2D(wire.endNode.getLayoutX(), (wire.endNode.getLayoutY() + wire.endNode.getFitHeight() / 4))))
                            wire.outNode.input1 = wire.inNode.getOutputValue();
                        else if(Objects.equals(wire.sp, new Point2D(wire.startNode.getLayoutX(), (wire.startNode.getLayoutY() + 3 * wire.startNode.getFitHeight() / 4))) | Objects.equals(wire.ep, new Point2D(wire.endNode.getLayoutX(), (wire.endNode.getLayoutY() + 3 * wire.endNode.getFitHeight() / 4))))
                            wire.outNode.input2 = wire.inNode.getOutputValue();
                    }
                }
            }
        }
        for (Wire wire : allWires) {
            if (wire.outNode instanceof Probe) {
                if(wire.inNode.getOutputValue())
                    wire.wireLine.setStroke(Color.RED);
                else
                    wire.wireLine.setStroke(Color.YELLOW);
                wire.outNode.input1 = wire.inNode.getOutputValue();
                if(wire.outNode.input1)
                    wire.outNode.gateImageView.setImage(new Image(Objects.requireNonNull(Wire.class.getResource("Images/probe-1.png").toExternalForm())));
                else
                    wire.outNode.gateImageView.setImage(new Image(Objects.requireNonNull(Wire.class.getResource("Images/probe-0.png").toExternalForm())));
            }

        }
    }
    public void showWarning(String text){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Be Alert!");
        alert.setHeaderText(null);
        alert.setContentText(text);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("Images/warning.jpg").toExternalForm())));
        alert.showAndWait();
    }
} // class ends