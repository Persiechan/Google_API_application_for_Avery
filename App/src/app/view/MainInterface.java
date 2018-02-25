package app.view;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import javafx.scene.control.*;

public class MainInterface extends Application {
	String rbo = "";
	double xOffset = 0;
	double yOffset = 0;
	@Override
	public void start(Stage primaryStage) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainInterface.class.getResource("MainInterface.fxml"));
		//loader.setController(new SheetToOracleController());
		AnchorPane rootLayout = (AnchorPane)loader.load();
		
		Scene scene = new Scene(rootLayout);
		
		/**
		scene.setOnMousePressed((MouseEvent event) ->{
			event.consume();
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});
		scene.setOnMouseDragged((MouseEvent event) ->{
			event.consume();
			primaryStage.setX(event.getScreenX()- xOffset);
			primaryStage.setY(event.getSceneY() - yOffset);
		});
		*/
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Google Assistant Tool - Avery Dennison");
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		
		SheetToOracleController sheetToOracleController = loader.getController();
		sheetToOracleController.launch();
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
