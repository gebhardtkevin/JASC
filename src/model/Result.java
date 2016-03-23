package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Result {
	
	private StringProperty result;
	private ObjectProperty<CheckBox> checkbox = new SimpleObjectProperty<CheckBox>();

	Result(String result){
		this.result = new SimpleStringProperty(result);
		this.checkbox.set(new CheckBox());
	}
	
	public StringProperty getResult(){
		return result;
	}
	
	public ObjectProperty<CheckBox> getCheckbox(){
		return checkbox;
	}
}
