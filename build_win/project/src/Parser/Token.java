/*
*   Token- generates mathematical tokens from a string expression
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

package Parser;

public class Token {

	/*
	 * supported types of Tokens
	 * 
	 */
	public static enum Type {
		PLUS, MINUS, MULTIPLY, DIVIDE, POW, PERCENT,SQRT,
		NUMBER,
		LPAREN,RPAREN,
		END
	}
	
	public Token(){
	}
	
	/*
	 * value is only used  for numbers
	 */
	private double value = Double.NaN;
	private Type kind = null;
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double val) {
		this.value = val;
	}
	
	public Type getType() {
		return kind;
	}
	
	public void setType(Type kind) {
		this.kind = kind;
	}
}
