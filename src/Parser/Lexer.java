/*
*   Lexer- Splits a String in mathematical tokens
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

package Parser;

import java.util.ArrayList;

public class Lexer {

	/*
	 * The expression to be lexed
	 */
	String expression;
	/*
	 * The "global" position in the String. We move through the String only once.
	 */
	int pos=0;
	
	public Lexer(String expression){
		this.expression=expression;
	}

	/*
	 * getNextToken 
	 * 
	 * tries to read the next token from a String. Numbers are returned as a whole, not as digits.
	 * 
	 * @returns the next token in the expression. If the End is reached, Token.Type.END is returned.
	 * 
	 */
	public Token getNextToken(){
		Token token = new Token();
		if (this.expression.length()<=pos){
			token.setType(Token.Type.END);
		}else{
			switch (String.valueOf(expression.charAt(pos))){
				case "+":  		 pos++;token.setType(Token.Type.PLUS);break;
				case "-":  		 pos++;token.setType(Token.Type.MINUS);break;
				case "*":  		 pos++;token.setType(Token.Type.MULTIPLY);break;
				case "/":  		 pos++;token.setType(Token.Type.DIVIDE);break;
				case "(":  		 pos++;token.setType(Token.Type.LPAREN);break;
				case ")":  		 pos++;token.setType(Token.Type.RPAREN);break;
				case "^":  		 pos++;token.setType(Token.Type.POW);break;
				case "%":  		 pos++;token.setType(Token.Type.PERCENT);break;
				case "\u221A": pos++;token.setType(Token.Type.SQRT);break;
				case " ":  		 pos++;token=getNextToken();break;
				default:
					double val = getNumber();
					if (Double.isNaN(val)){
						System.err.println("Expression contains unknown Token");	
						token.setType(Token.Type.NUMBER);
						token.setValue(Double.NaN);
					}else{
						token.setType(Token.Type.NUMBER);
						token.setValue(val);
					}					
			}
		}
		return token;
	}

	/*
	 * getNumber
	 * 
	 * tries to read the next double-value from the String.
	 * 
	 * @returns the next token from Token.Type.NUMBER. If the Token is not a double, NaN is returned.
	 * 
	 */
	private double getNumber() {
		double value = Double.NaN;
		ArrayList<Character> digits = new ArrayList<>();
		while(pos<this.expression.length()&&String.valueOf(expression.charAt(pos)).matches("[0-9\\.]")){
			digits.add(this.expression.charAt(pos));
			pos++;
		}
		String numStr="";
		for (char digit:digits){
			numStr += digit; 
		}
		if (!numStr.equals("")){
			try{
			value=Double.parseDouble(numStr);
			}catch (NumberFormatException e){
				value=Double.NaN;
			}
		}
		return value;
	}
}