
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class MainClass extends Application {
	public Slider sldr1 = new Slider(1.0, 12.0, 1.0);
	public Slider sldr2 = new Slider(1.0, 12.0, 1.0);
	public Slider sldr3 = new Slider(0, 2*Math.PI, Math.PI/12);
	public Canvas canvas = new Canvas(336,255);
	public ToggleButton tglbtn = new ToggleButton("ON/OFF");
	@Override
	public void start(Stage primaryStage) {		
		AnchorPane root = new AnchorPane();
		Button btn1 = new Button("SHOW");
		sldr1.setMajorTickUnit(1.0);
		sldr1.setMinorTickCount(0);
		sldr1.setSnapToTicks(true);
		sldr2.setMajorTickUnit(1.0);
		sldr2.setMinorTickCount(0);
		sldr2.setSnapToTicks(true);
		sldr1.setOrientation(Orientation.VERTICAL);
		sldr2.setOrientation(Orientation.VERTICAL);
		sldr3.setOrientation(Orientation.VERTICAL);
		HBox hbx = new HBox();
		hbx.setSpacing(50.0);
		hbx.getChildren().addAll(sldr1,sldr2,sldr3);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
		root.setRightAnchor(btn1, 95.0);
		root.setBottomAnchor(btn1, 87.0);
		root.setLeftAnchor(tglbtn, 82.0);
		root.setBottomAnchor(tglbtn, 86.0);
		root.setLeftAnchor(canvas, 155.0);
		root.setTopAnchor(canvas, 188.0);
		root.setRightAnchor(hbx, 100.0);
		root.setTopAnchor(hbx, 270.0);
		
		btn1.setOnAction(event -> {
			runDisplay(gc);
            System.out.println(sldr1.getValue());
            System.out.println(sldr2.getValue());
            System.out.println(sldr3.getValue());
            drawShapes(gc);
        });
		
		tglbtn.setOnAction(event -> {
			runDisplay(gc);
		});
		
		root.getChildren().addAll(btn1,canvas,hbx,tglbtn);
		Scene scene = new Scene(root, 1000, 600);
		scene.getStylesheets().add
		 (MainClass.class.getResource("Style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Oscyloskop");
		primaryStage.show();
	}
	
    public void drawShapes(GraphicsContext gc) {
        int points = 360;
        double increment = 2 * Math.PI / points;
        gc.beginPath();
        gc.setStroke(Color.BLACK);
        for (int i = 0; i <= 360; i++) {
            final double t = i * increment;
            if (i == 0) {
                gc.moveTo(((200 * Math.sin(sldr1.getValue() * t
                        + sldr3.getValue()))/1.8)+168, ((200 * Math.sin(sldr2.getValue() * t))/1.8)+122);
            } else {
                gc.lineTo(((200 * Math.sin(sldr1.getValue() * t
                        + sldr3.getValue()))/1.8)+168, ((200 * Math.sin(sldr2.getValue() * t))/1.8)+122);
            }
        }
        gc.closePath();
        gc.stroke();
    }
    
    public void runDisplay(GraphicsContext gc) {
    	if(tglbtn.isSelected()) {
    		gc.setFill(Color.WHITE);
    		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    		gc.setStroke(Color.RED);
    		gc.strokeLine(0, 122, 336.0, 122);
    		gc.strokeLine(168.0, 0, 168, 255);
    	} else {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    	}
    }
    

	public static void main(String[] args) {
		launch(args);
	}
}
