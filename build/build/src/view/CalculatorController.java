/*
*   CalculatorController - Controller Class for the inner View
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
import java.util.NoSuchElementException;

import Parser.Parser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import model.PermanentExpressionList;
import model.ResultList;

public class CalculatorController {
	@FXML
	private PermanentExpressionList expressions;
	@FXML
	private SplitMenuButton splitMenuButton;
	@FXML
	private Button button;
	@FXML
	public TextField textField;
	@FXML
	private Label result;

	private ResultList results = ResultList.getInstance(JASC.RESULT_FILE);
	// Reference to the main application.
    @SuppressWarnings("unused")
	private JASC calculatorViewLoader;

    public CalculatorController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */   
    @FXML
    private void initialize() {
        try {
			results.loadFromPermanent();
		} catch (IOException e) {
			//we can't load any results, so the best will be to do nothing
			System.err.println("Can't load result file");
		}
        textField.end();
        this.expressions = PermanentExpressionList.getInstance(splitMenuButton.getItems(),JASC.EXPRESSION_FILE);
        try {
        	this.expressions.loadFromFile();
		} catch (IOException e) {
			//we can't load any results, so the best will be to do nothing
			System.err.println("Can't load expression file");
		}
        for (MenuItem item :this.expressions){
        	item.setOnAction(this::restoreCalculation);
        }
    }
    

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(JASC calculatorViewLoader) {
        this.calculatorViewLoader = calculatorViewLoader;
    }

    /**
     * Handler for number buttons ('0'-'9','.')
     * 
     * @param event
     */
    @FXML
    private void onNumberButtonClicked(ActionEvent event){
    	String formerText = textField.getText();
    	String additionalText = ((Button)event.getSource()).getText();
    	if (textField.getSelection().getLength()==0){
    		int pos = textField.getCaretPosition();
    		textField.setText(formerText.substring(0, pos) + additionalText + formerText.substring(pos));
    		textField.selectRange(pos+additionalText.length(), pos+additionalText.length());
    	}else{
    		textField.replaceSelection(additionalText);
    	}
    }

    /**
     * Handler for operator buttons (+,-,*,/)
     * 
     * @param event
     */
    @FXML
    private void onOperationButtonClicked(ActionEvent event){
    	String formerText = textField.getText();
    	String additionalText = ((Button)event.getSource()).getText();
    	if (textField.getSelection().getLength()==0){
    		int pos = textField.getCaretPosition();
    		textField.setText(formerText.substring(0, pos) + additionalText + formerText.substring(pos));
    		textField.selectRange(pos+additionalText.length(), pos+additionalText.length());
    	}else{
    		textField.replaceSelection(additionalText);
    	}
    }
    
    /**
     * Handler for the parenthesis-button.
     * It is either possible to generate parenthesis and start writing inside or 
     * to mark parts of the expression and generate the parenthesis around the marked part 
     * 
     * @param event
     */
    @FXML
    private void onParenthesisClicked(ActionEvent event){
    	String formerText = textField.getText();
    	IndexRange pos = textField.getSelection();
    	if (pos.getLength()==0){
        	textField.setText(formerText.substring(0,pos.getStart()) + "()" +  formerText.substring(pos.getEnd()));    		
        	textField.selectRange(pos.getEnd()+1, pos.getEnd()+1);
    	}else{
        	String selectedText = formerText.substring(pos.getStart(),pos.getEnd()).trim();
        	textField.setText(formerText.substring(0, pos.getStart()) + "(" + selectedText +")" +  formerText.substring(pos.getEnd()));    		
        	textField.selectRange(pos.getEnd()+2, pos.getEnd()+2);
    	}
    }
    
    /**
     * Handler for the parenthesis-button.
     * It is either possible to generate a square root and start writing inside or 
     * to mark parts of the expression and generate thesquare-root around the marked part 
     * 
     * @param event
     */
    @FXML
    private void onSqrtClicked(ActionEvent event){
    	String formerText = textField.getText();
    	IndexRange pos = textField.getSelection();
    	if (pos.getLength()==0){
        	textField.setText(formerText.substring(0,pos.getStart()) + "\u221A()" +  formerText.substring(pos.getEnd()));    		
        	textField.selectRange(pos.getEnd()+2, pos.getEnd()+2);
    	}else{
        	String selectedText = formerText.substring(pos.getStart(),pos.getEnd()).trim();
        	textField.setText(formerText.substring(0, pos.getStart()) + "\u221A(" + selectedText +")" +  formerText.substring(pos.getEnd()));    		
        	textField.selectRange(pos.getEnd()+3, pos.getEnd()+3);
    	}
    }
    
    /**
     * Handler for the ANS-button.
     * fetches the last calculated result
     * 
     * @param event
     */
    @FXML
    private void onANSClicked(ActionEvent event){
    	String formerText = textField.getText();
    	String additionalText;
    	try{
    		additionalText = results.getFirst().getResult().getValue();
    	} catch (NoSuchElementException e){
    		additionalText="0";
    	} 
    	if (textField.getSelection().getLength()==0){
    		int pos = textField.getCaretPosition();   	
    		textField.setText(formerText.substring(0, pos) + additionalText + formerText.substring(pos));
    		textField.selectRange(pos+1, pos+1);
    	}else{
    		textField.replaceSelection(additionalText);
    	}
    }
    
    /**
     * Handler for ANS
     * parses the expression an writes expression and result to permanent, if result is not NaN 
     * 
     * @param event
     */
    @FXML
    public void calculate(ActionEvent event){
    	String expressionString = textField.getText();
    	Parser parser = new Parser(expressionString);
    	result.setText(String.format("%s",parser.parse()));
    	if (!result.getText().equals("NaN")){
    		try {
    			results.addAndSave(result.getText());
        		MenuItem item = new MenuItem(expressionString);
        		item.setOnAction(this::restoreCalculation);
    			this.expressions.addAndSave(item);
    		} catch (IOException e) {
    			System.err.println("Could not write to File. Maybe missing rights?");
    			e.printStackTrace();
    		}
    		textField.clear();
    	}
    }
    
    /**
     * Handler for the ANS-Dropdown-Menu
     * Recalls an old calculation
     * 
     * @param event
     */
    @FXML
    private void restoreCalculation(ActionEvent event){
      	String additionalText = ((MenuItem)event.getSource()).getText();
    	if (textField.getSelection().getLength()==0){
    		int pos = textField.getCaretPosition();
    		textField.appendText(additionalText);
    		textField.selectRange(pos+additionalText.length(), pos+additionalText.length());
    	}else{
    		textField.replaceSelection(additionalText);
    	}
  }  
}