/*
*   RootController - Controller for the Root-Node, manages the Main-Menu 
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
*   JASC ist Freie Software: Sie können es unter den Bedingungen
*   der GNU General Public License, wie von der Free Software Foundation,
*   Version 3 der Lizenz oder (nach Ihrer Wahl) jeder späteren
*   veröffentlichten Version, weiterverbreiten und/oder modifizieren.
*
*   JASC wird in der Hoffnung, dass es nützlich sein wird, aber
*   OHNE JEDE GEWÄHRLEISTUNG, bereitgestellt; sogar ohne die implizite
*   Gewährleistung der MARKTFÄHIGKEIT oder EIGNUNG FÜR EINEN BESTIMMTEN ZWECK.
*   Siehe die GNU General Public License für weitere Details.
*
*   Sie sollten eine Kopie der GNU General Public License zusammen mit diesem
*   Programm erhalten haben. Wenn nicht, siehe <http://www.gnu.org/licenses/>.
*/

package view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.PermanentExpressionList;

public class RootController {
	
@FXML 
private MenuItem close;

@SuppressWarnings("unused")
private JASC calculatorViewLoader;

/*
 * Handler for the close menu
 */
@FXML
private void onClose(){
	Platform.exit();
	System.exit(0);
}

@FXML
private void onShowResultList(){
	 Stage stage = new Stage();
     stage.centerOnScreen();
     stage.setTitle("Calculator");
     initResultLayout(stage);
	 stage.show();
}

private void initResultLayout(Stage stage) {
	  // Load root layout from fxml file.
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(JASC.class.getResource("results.fxml"));
    BorderPane resultLayout;
	try {
		resultLayout = (BorderPane) loader.load();
	    // Show the scene containing the root layout.
	    Scene scene = new Scene(resultLayout);
	    stage.setScene(scene);
	    stage.show();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

@FXML
private void onDeleteExpressions(){
	PermanentExpressionList.getInstance().clear();
}

@FXML
private void onAbout(){
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("JASC " + JASC.VERSION );
	alert.setHeaderText(null);
	alert.setContentText("Just another small calculator" 
						 + System.lineSeparator() + 
						 "\u00a9 2016 Kevin Gebhardt"
						 + System.lineSeparator() + 
						 "gebhardt.kevin@gmail.com"
						 );
	alert.showAndWait();
}
/**
 * Is called by the main application to give a reference back to itself.
 * 
 * @param mainApp
 */
public void setMainApp(JASC calculatorViewLoader) {
    this.calculatorViewLoader = calculatorViewLoader;
    }
}
