/*
*   JASC - Main class for starting the Calulator
*
*   Copyright 2016 Kevin Gebhardt <gebhardt.kevin@gmail.com>
*
*	This file is part of JASC - Just another simple calculator.
*
*   JASC is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   JASC is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with JASC.  If not, see <http://www.gnu.org/licenses/>.
*
*   Diese Datei ist Teil von JASC - Just another simple calculator.
*
*   JASC ist Freie Software: Sie k�nnen es unter den Bedingungen
*   der GNU General Public License, wie von der Free Software Foundation,
*   Version 3 der Lizenz oder (nach Ihrer Wahl) jeder sp�teren
*   ver�ffentlichten Version, weiterverbreiten und/oder modifizieren.
*
*   JASC wird in der Hoffnung, dass es n�tzlich sein wird, aber
*   OHNE JEDE GEW�HRLEISTUNG, bereitgestellt; sogar ohne die implizite
*   Gew�hrleistung der MARKTF�HIGKEIT oder EIGNUNG F�R EINEN BESTIMMTEN ZWECK.
*   Siehe die GNU General Public License f�r weitere Details.
*
*   Sie sollten eine Kopie der GNU General Public License zusammen mit diesem
*   Programm erhalten haben. Wenn nicht, siehe <http://www.gnu.org/licenses/>.
*/

package view;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ObservablePermanentList;

public class JASC extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    public static File EXPRESSION_FILE = new File("expressions"); 
    public static File RESULT_FILE = new File("results"); 
    
    private ObservablePermanentList expressions=new ObservablePermanentList();
    
    /*
     * Main
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Calculator");
        primaryStage.setOnCloseRequest(event -> {
        	//No need to do anything at the moment
        });
        initRootLayout();
        showCalculator();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JASC.class.getResource("root.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Give the controller access to the main app.
            RootController controller = loader.getController();
            controller.setMainApp(this);
       
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
     * Shows the calculator inside the root layout.
     */
    public void showCalculator() {
        try {    
            
            // Load calculator
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JASC.class.getResource("calculator.fxml"));            
            AnchorPane calculator = (AnchorPane) loader.load();
            
            // Give the controller access to the main app
            CalculatorController controller = loader.getController();
            controller.setMainApp(this);
            
            //get key bindings
            rootLayout.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    if (ke.getCode() == KeyCode.ENTER) {
                    	controller.calculate(null);
                    	ke.consume();
                    }
                    if (ke.getCode() == KeyCode.BACK_SPACE) {
                    	if (controller.lastCaret-1>=0){
                    		controller.textField.deleteText(controller.lastCaret-1, controller.lastCaret);
                    		controller.lastCaret--;
                    		ke.consume();
                    	}
                    }
                    if (ke.getCode() == KeyCode.DELETE) {
                    	if (controller.lastCaret+1<=controller.textField.getLength()){
                    		controller.textField.deleteText(controller.lastCaret, controller.lastCaret+1);
                    		ke.consume();
                    	}
                    }
                }
            });      
            // Set calculator into the center of root layout.
            rootLayout.setCenter(calculator);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /*
     * loads expression-list from file
     * 
     * @returns a list containing all former expressions
     */
    public ObservablePermanentList getExpressions(){
        try {
   			expressions.loadFromFile(EXPRESSION_FILE);
   		} catch (IOException e) {
   			System.out.println("No expression File found. Will try to create a new one...");
   			try {
				expressions.saveToFile(RESULT_FILE);
				System.out.println("Success!");
			} catch (IOException e1) {
				System.err.println("Could not create expression file. Maybe missing rights?");	
			}
   		}
        return expressions;	
    }
}
