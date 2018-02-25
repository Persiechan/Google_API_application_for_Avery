package app.view;

import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;

import app.model.*;
import google.sheet.main.java.GoogleSheetToOracle;
public class SheetToOracleController {
	@FXML
	public ComboBox<String> rboComboBox;
	
	@FXML
	public ComboBox<String> itemComboBox;
	
	@FXML
	public Button importOracleButton;
	
	public SheetToOracleController() {
		
	}
	
	public void initialize() {
		this.rboComboBox.setPromptText("<please select ROB>");
		this.rboComboBox.setEditable(true);
		this.itemComboBox.setPromptText("<please select Item>");
		this.itemComboBox.setEditable(true);
		
	}
	private void getRBOList() {
		this.rboComboBox.setItems(FXCollections.observableArrayList(new SheetToOracleRBO().getRBOList()));
	}
	private void getItemList(String newRBO) {
		this.itemComboBox.setItems(FXCollections.observableArrayList(new SheetToOracleItem().getItemList(newRBO)));
	}
	public void launch(){
		getRBOList();
		this.rboComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override 
			public void changed(ObservableValue<? extends String> observable,String oldRBO,String newRBO) {
				getItemList(newRBO);
				//System.out.print(newRBO);
			}
		});
		
		this.itemComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override 
			public void changed(ObservableValue<? extends String> observable,String oldItem,String newItem) {
				new SheetToOracleItem().getItemContent(newItem);
			}
		});
		
		this.importOracleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try {
					new GoogleSheetToOracle().toOracle();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	//System.out.println("Hello World!");
            }
        });
	}
}
