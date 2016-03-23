/*
*   Parser- tries to recursively parse a String as a mathematical expression. 
*   Supports +,-,*,/,^,square root(\u221A),percent and parenthesis
*	Returns NaN on unsupported or wrong expressions
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

/* 
* Grammar:
* digit = 0|1|2|3|4|5|6|7|8|9
* number = {digit}[.][{digit}]
* expression = term | expression `+` term | expression `-` term
* term = factor | term `*` factor | term `/` factor | term brackets
* factor = brackets | number | factor `^` factor | sqrt factor | factor %
* brackets = `(` expression `)`
*/
public class Parser {
	Lexer lexer;
	Token token;
	/*
	 * counter to check, if the parenthesis are all closed
	 */
	int parenCount = 0;
	
	/*
	 * constructor 
	 * @param expression the expression to be parsed 
	 */
	public Parser(String expression){
		this.lexer = new Lexer(expression);
	}

	/*
	 * parse 
	 * 
	 * starts the parsing, checks for unended expressions and unclosed parenthesis
	 * 
	 * @returns the value of the calculated expression
	 * 
	 */
	public double parse(){
        token = lexer.getNextToken();
		double value = parseExpression();
		if (token.getType().equals(Token.Type.END)&&parenCount==0){
			return value;
		}else{
			return Double.NaN;
		}
	}

	/*
	 * parseExpression 
	 * 
	 * parsed an expression according to the grammar
	 * 
	 * @returns value of the expression
	 * 
	 */
	private double parseExpression() {
		double value = parseTerm();
		while (true) {
			if (token.getType().equals(Token.Type.PLUS)) { // addition
				token=lexer.getNextToken();
				value += parseTerm();
			} else if (token.getType().equals(Token.Type.MINUS)) { // subtraction
				token = lexer.getNextToken();
				value -= parseTerm();
			} else {
				return value;
			}
		}
	}


	/*
	 * parseTerm 
	 * 
	 * parsed a term according to the grammar
	 * 
	 * @returns value of the term
	 * 
	 */
	private double parseTerm() {
        double value = parseFactor();
        while (true) {
            if (token.getType().equals(Token.Type.DIVIDE)) { // division
                token = lexer.getNextToken();
                value /= parseFactor();
            } else if (token.getType().equals(Token.Type.MULTIPLY)|| token.getType().equals(Token.Type.LPAREN)|| token.getType().equals(Token.Type.SQRT)|| token.getType().equals(Token.Type.NUMBER)) { // multiplication
                if (token.getType().equals(Token.Type.MULTIPLY)) {
                	token=lexer.getNextToken();
                }
                value *= parseFactor();
            } else {
                return value;
            }
        }
	}

	/*
	 * parseFactor 
	 * 
	 * parsed a factor according to the grammar
	 * 
	 * @returns value of the factor
	 * 
	 */
	private double parseFactor() {
	    double value = 0;
        boolean negate = false;
        boolean sqrt = false;
        if (token.getType().equals(Token.Type.PLUS) || token.getType().equals(Token.Type.MINUS)) { // unary plus & minus
            negate = token.getType().equals(Token.Type.MINUS);
            token=lexer.getNextToken();
        }
        if (token.getType().equals(Token.Type.SQRT)){ // square root
            sqrt = true;
        	token=lexer.getNextToken();
        }
        if (token.getType().equals(Token.Type.LPAREN)) { // brackets
        	parenCount++;
            token= lexer.getNextToken();
            value = parseExpression();
            if (token.getType().equals(Token.Type.RPAREN)) {
            	parenCount--;
            	token = lexer.getNextToken();
            }
        } else { // numbers
            if (token.getType().equals(Token.Type.NUMBER)){
            	value = token.getValue();
            	token = lexer.getNextToken();
            }else{
            	return Double.NaN;
            }
        }
        if (token.getType().equals(Token.Type.POW)) { // exponentiation
            token = lexer.getNextToken();
            value = Math.pow(value, parseFactor());
        }
        if (token.getType().equals(Token.Type.PERCENT)) { // Percent
            value = value*0.01;
            token = lexer.getNextToken();
        }
        if (sqrt) value = Math.sqrt(value); //square root is equal to exponentiation
        if (negate) value = -value; // unary minus is applied after exponentiation; e.g. -3^2=-9
        return value;
	}
}