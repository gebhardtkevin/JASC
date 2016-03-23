/*
*   ResultList - extended FILO list with auto-update for the view with support for saving and recreating the list from persistent storage 
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
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

public class PermanentExpressionList extends SimpleListProperty<MenuItem>{
	/*
	 * construct an empty list
	 */
	private File file;
	private static PermanentExpressionList instance = null;
	boolean firstStart=false;
	
	private PermanentExpressionList(File file){
		super(FXCollections.observableArrayList());	
		this.file=file;
	}
	
	/*
	 * copy-constructor
	 */
	private PermanentExpressionList(ObservableList<MenuItem> list, File file){
			super(list);
			this.file=file;
	}
	
	public static PermanentExpressionList getInstance(){
		if (instance == null){
			System.err.println("List is not initialised yet!");
			throw new NullPointerException();
		}
		return instance;
	}
	
	public static PermanentExpressionList getInstance(ObservableList<MenuItem> list,File file){
		if (instance == null){
			instance =  new PermanentExpressionList(list,file);
		}
		return instance;
	}

	/*
	 * loadFromFile 
	 * 
	 * tries to parse a file line-wise into the list
	 * 
	 * @param File the file to load 
	 * 
	 */
	public void loadFromFile() throws IOException{
		if (!file.exists()){
			add(new MenuItem("0"));
		}
		BufferedReader reader =  new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

		String firstLine = reader.readLine();
		if (null==firstLine){
			add(0,new MenuItem("0"));
		}else{
			add(0,new MenuItem(firstLine));
		}
		String line;
		while (null != (line = reader.readLine())){
			add(0,new MenuItem(line));				
		}
		reader.close();
	}
	
	/*
	 * saveToFile 
	 * 
	 * tries to save the list line-wise into a file
	 * 
	 * @param File were to save
	 * 
	 */
	public void saveToFile() throws IOException{
		//remove the placeholder, if necessary
		if (!file.exists()){
			firstStart = true;
			remove(0);
		}
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
		Iterator<MenuItem> it = iterator();
		while(it.hasNext()){
			writer.write(it.next().getText());
			writer.newLine();
		}
		writer.close();
		if (firstStart){
			add(new MenuItem("0"));
		}	
	}
	
	/*
	 * addAndSave 
	 * 
	 * add an item to the list and instantly save it to the given file
	 * 
	 * @param item the new item
	 * @param file were to save 
	 * 
	 */
	public void addAndSave(MenuItem item) throws IOException{
		if (firstStart){
			remove(0);
		}
		add(0,item);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8"));
		writer.write(item.getText());
		writer.newLine();
		writer.close();
	}
	
	public void addAndSaveAll(List<MenuItem> items) throws IOException{
		addAll(items);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8"));
		for (MenuItem item:items){
			writer.write(item.getText());
			writer.newLine();
		}
		writer.close();
	}
	
	@Override
	public void clear(){
		super.clear();
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		add(new MenuItem("0"));
		firstStart=true;
	}
}