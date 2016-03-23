package view;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Result;
import model.ResultList;

public class ResultController {
	@FXML
	private TableView<Result> resultTable;
	@FXML
	private TableColumn<Result, CheckBox> checkColumn;
	@FXML
	private TableColumn<Result, String>  resultColumn;
	@FXML
	private Button ok;
	
	ResultList resultList = ResultList.getInstance(JASC.RESULT_FILE);
    
	private ObservableList<Result> results = FXCollections.observableArrayList();

	@FXML
    private void initialize() {
		resultTable.setPlaceholder(new Label("No results yet"));
		for (Result result : resultList){
			results.add(result);
		}
		// Initialize the person table with the two columns.
        resultColumn.setCellValueFactory(cellData -> cellData.getValue().getResult());
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().getCheckbox());
    	// Add observable list data to the table
        resultTable.setItems(results);
    }
	
	@FXML
	private void onDeleteAll(){
		results.clear();
		resultList.clear();
	}
	
	
	@FXML
	private void onDeleteSelected(){
		 for (int i = 0; i<results.size();i++){
			 if (results.get(i).getCheckbox().getValue().isSelected()){
				 results.remove(i);
				 resultList.remove(i);
				 i--;
			 }
		 }
	}
	
	@FXML
	private void onOK(){
		 Stage stage = (Stage) ok.getScene().getWindow();
		 stage.close();
	}
}
