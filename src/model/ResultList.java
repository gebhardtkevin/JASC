/*
*   ResultList - extended FILO list with support for saving and recreating the list from persistent storage 
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class ResultList extends LinkedList<String>{
	
	private static final long serialVersionUID = -1305599998299292076L;
	
	/*
	 * We only need one ResultList in the application, so this class is singleton for easy access from the controllers
	 */
	private static ResultList instance = null; 
	
	private ResultList(){
		super();
	}
	
	public static ResultList getInstance(){
		if (instance==null){
			instance = new ResultList();
		}
		return instance;
	}
	
	/*
	 * loadFromPermanent 
	 * 
	 * tries to parse a file line-wise into the list
	 * 
	 * @param File the file to load 
	 * 
	 */
	public void loadFromPermanent(File file) throws IOException{
			BufferedReader resultReader = new BufferedReader(new FileReader(file));
			String result;
			while (null!=(result = resultReader.readLine())){
				this.add(result);
			}
			resultReader.close();
	}

	/*
	 * saveToFile 
	 * 
	 * tries to save the list line-wise into a file
	 * 
	 * @param File were to save
	 * 
	 */
	public void saveToPermanent(File file)throws IOException{
			BufferedWriter resultWriter = new BufferedWriter(new FileWriter(file,false));
			for (String result:this){
				resultWriter.write(result);
				resultWriter.newLine();
			}
			resultWriter.close();
	}
	
	/*
	 * addAndSave 
	 * 
	 * add an value to the list and instantly save it to the given file
	 * 
	 * @param value the new item
	 * @param file were to save 
	 * 
	 */
	public void addAndSave(String value, File file) throws IOException{
		addFirst(value);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
		writer.write(value);
		writer.newLine();
		writer.close();
	}
}