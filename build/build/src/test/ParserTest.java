/*
*   ParserTest - Unit-Tests for the parser
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

package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Parser.Parser;

public class ParserTest {

	
	@BeforeClass
	public static void init(){
		
	}
	
	@Test
	public void emptyExpressionShouldReturnNaN() {
		String expression = "";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,Double.NaN, 0.000001);
	}
	
	@Test
	public void wrongExpressionShouldReturnNaN() {
		String expression = "5+3fail";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertTrue("Failed: " + expression,Double.isNaN(value));
	}
	
	@Test
	public void wrongExpressionShouldReturnNaN2() {
		String expression = "5+fail3";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertTrue("Failed: " + expression,Double.isNaN(value));
	}
	
	@Test
	public void MissingParentesisExpressionShouldReturnNaN() {
		String expression = "(5+3";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,Double.NaN, 0.000001);
	}
	
	@Test
	public void MissingParentesisExpressionShouldReturnNaN2() {
		String expression = "(5*3";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,Double.NaN, 0.000001);
	}
	
	@Test
	public void MissingParentesisExpressionShouldReturnNaN3() {
		String expression = "(";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,Double.NaN, 0.000001);
	}
	
	@Test
	public void calculationShouldReturnCorrectValue001() {
		String expression = "4+3";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,7.0, 0.000001);
	}
	
	@Test
	public void calculationShouldReturnCorrectValue002() {
		String expression = "5 + ((1 + 2) * 4) - 3";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,14.0, 0.000001);
	}

	@Test
	public void calculationShouldReturnCorrectValue003() {
		String expression = "6+2*5";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,16.0, 0.000001);
	}
	
	@Test
	public void calculationShouldReturnCorrectValue004() {
		String expression = "-8/2-5";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,-9.0, 0.000001);
	}
	
	@Test
	public void calculationShouldReturnCorrectValue005() {
		String expression = "5*3+(6+1)";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,22.0, 0.000001);
	}
	
	@Test
	public void calculationShouldReturnCorrectValue006() {
		String expression = "\u221A(4)+2";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,4.0, 0.000001);
	}
	
	@Test
	public void calculationShouldReturnCorrectValue007() {
		String expression = "4^1.5*8";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,64.0, 0.000001);
	}
	
	@Test
	public void calculationShouldReturnCorrectValue008() {
		String expression = "(4^1.5*8)%";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,0.64, 0.000001);
	}
	
	@Test
	public void calculationShouldReturnCorrectValue009() {
		String expression = "1.5+1.8";
		Parser parser = new Parser(expression);
		double value = parser.parse();
		assertEquals("Failed: " + expression,value,3.3, 0.000001);
	}
	
}
