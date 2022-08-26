package expressionTreeBuilderEvaluator;

import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

import calculator.Variable;
import lexer.Lexer;
import lexer.Token;
import lexer.Token.Kind;

public class ExpressionTreeBuilderEvaluator {
	/**
	 * <p>
	 * Title: ExpressionTreeBuilderEvaluator - A demonstration of how to build and evaluate a tree
	 * </p>
	 *
	 * <p>
	 * Description: A controller object class that implements an expression parser, tree builder,
	 * and tree evaluator functions
	 * </p>
	 *
	 * <p>
	 * Copyright: Copyright © 2019
	 * </p>
	 *
	 * @author Lynn Robert Carter
	 * @version 1.00	Baseline version 2019-03-10
	 */
	
	/**********************************************************************************************
	 * 
	 * The ExpressionTreeBuilderEvaluator Class provides the functions that parses an input stream
	 * of lexical tokens, uses the structure to build an expression tree, and then uses that tree
	 * to compute the value of the expression.
	 * 
	 * The following are the primary followed by the secondary attributes for this Class
	 */

	// The following String is the input for this demonstration
	private static String theExpression= "(3+2)/(4-2)";

	// The following are the attributes that support the scanning and lexing of the input
	public static Scanner theReader = new Scanner (theExpression);
	
	public static Lexer lexer;
	
	static Lexer lex;
	static ExprNode tree;
	//static String ex = "";
	static double result;
	static boolean flag = false;
	
	public static Token current;
	public static Token next;
	
	// The following are the stacks that are used to transform the parse output into a tree
	public static Stack<ExprNode> exprStack = new Stack<>();
	private static Stack<Token> opStack = new Stack<>();
	
	/**********
	 * The addSub Expression method parses a sequence of expression elements that are added
	 * together, subtracted from one another, or a blend of them.
	 * 
	 * @return	The method returns a boolean value indicating if the parse was successful
	 */
	public static boolean addSubExpr() {

		// The method assumes the input is a sequence of additive/subtractive elements separated
		// by addition and/or subtraction operators
		if (mpyDivExpr()) {
			
			// Once an additive/subtractive element has been found, it can be followed by a
			// sequence of addition or subtraction operators followed by another 
			// additive/subtractive element.  Therefore we start by looking for a "+" or a "-"
			while ((current.getTokenKind() == Kind.SYMBOL) && 
					((current.getTokenCode() == 6) ||		// The "+" operator
					 (current.getTokenCode() == 7))) {		// The "-" operator
				
				// When you find a "+" or a "-", push it onto the operator stack
				opStack.push(current);
				
				// Advance to the next input token
				
				current = next;
				next = lexer.accept();
				
				// Look for the next additive/subtractive element
				if (mpyDivExpr()) {
					
					// If one is found, pop the two operands and the operator
					ExprNode expr2 = exprStack.pop();
					ExprNode expr1 = exprStack.pop();
					Token oper = opStack.pop();
					
					// Create an Expression Tree node from those three items and push it onto
					// the expression stack
					exprStack.push(new ExprNode(oper, true, expr1, expr2));
				}
				else {
					
					// If we get here, we saw a "+" or a "-", but it was not followed by a valid
					// additive/subtractive element
					System.out.println("Parse error: A required additive/subtractive element was not found");
					return false;
				}
			}
			
			// Reaching this point indicates that we have processed the sequence of 
			// additive/subtractive elements
			return true;
		}
		else
			
			// This indicates that the first thing found was not an additive/subtractive element
			return false;
	}
	
	/**********
	 * The mpyDiv Expression method parses a sequence of expression elements that are multiplied
	 * together, divided from one another, or a blend of them.
	 * 
	 * @return	The method returns a boolean value indicating if the parse was successful
	 */
	private static boolean mpyDivExpr() {

		// The method assumes the input is a sequence of terms separated by multiplication and/or 
		// division operators
		if (term()) {
			//if (term()) {	
			// Once an multiplication/division element has been found, it can be followed by a
			// sequence of multiplication or division operators followed by another 
			// multiplication/division element.  Therefore we start by looking for a "*" or a "/"
			while ((current.getTokenKind() == Kind.SYMBOL) && 
					((current.getTokenCode() == 8) ||		// The "*" operator	
					 (current.getTokenCode() == 9))) {		// The "/" operator
				
				// When you find a "*" or a "/", push it onto the operator stack
				opStack.push(current);
				
				// Advance to the next input token
				
				current = next;
				next = lexer.accept();
				
				// Look for the next multiplication/division element
				//if (term()) {
					if (term()) {	
					// If one is found, pop the two operands and the operator
					ExprNode expr2 = exprStack.pop();
					ExprNode expr1 = exprStack.pop();
					Token oper = opStack.pop();
					
					// Create an Expression Tree node from those three items and push it onto
					// the expression stack
					exprStack.push(new ExprNode(oper, true, expr1, expr2));
				}
				else {
					
					// If we get here, we saw a "*" or a "/", but it was not followed by a valid
					// multiplication/division element
					System.out.println("Parse error: a term was not found");
					return false;
				}
			}
			
			// Reaching this point indicates that we have processed the sequence of 
			// additive/subtractive elements
			return true;
		}
		else
			
			// This indicates that the first thing found was not a multiplication/division element
			return false;
	}
	
		
	/**********
	 * The term Expression method parses constants.
	 * 
	 * @return	The method returns a boolean value indicating if the parse was successful
	 */
	private static boolean term() {
		
		// Parse the term
		if (current.getTokenKind() == Kind.FLOAT || 
			current.getTokenKind() == Kind.INTEGER) {
			
			// When you find one, push a corresponding expression tree node onto the stack
			exprStack.push(new ExprNode(current, false, null, null));
			
			// Advance to the next input token
			
			current = next;
			next = lexer.accept();
			
			// Signal that the term was found
			return true;
		}
		else if((current.getTokenKind() == Kind.RESERVED_WORD) && 
				((current.getTokenCode() == 3) ))
		{
			opStack.push(current);
			
			// Advance to the next input token
			
			current = next;
			next = lexer.accept();
			
			// Look for the next multiplication/division element
			if (term()) {
					
				// If one is found, pop the two operands and the operator
				//ExprNode expr2 = exprStack.pop();
				ExprNode expr1 = exprStack.pop();
				Token oper = opStack.pop();
				
				// Create an Expression Tree node from those three items and push it onto
				// the expression stack
				exprStack.push(new ExprNode(oper, false, expr1, null));
			
			// Signal that the term was found
			}
			return true;
		}
		else if ((current.getTokenKind() == Kind.SYMBOL) && ((current.getTokenCode() == 4) ||
				(current.getTokenCode() == 5) )) {
			
			flag = true;
			 String ex = "";
			//opStack.push(current);
			while(current.getTokenCode() != 5) {
			// Advance to the next input token
			current = next;
			next = lexer.accept();
			
			ex = ex+current.getTokenText();
			
			}
			Token copy = next;
			//opStack.push(current);
			
			Scanner theRead = new Scanner (ex);
			
			lex = new Lexer(theRead);
			current = lex.accept();
			next = lex.accept();

			// Invoke the parser and the tree builder
			boolean isValid = addSubExpr();
			System.out.println("The expression is valid: " + isValid + "\n");
			if (isValid) {
				
				// Display the expression tree
				tree = exprStack.pop();
				System.out.println(tree);
				result =compute(tree);
				System.out.println(result);
				}
			Scanner read = new Scanner (Double.toString(result));
			Lexer vex = new Lexer(read);
			Token cu = vex.accept();
			exprStack.push(new ExprNode(cu, false, null, null));
			flag = false;
			current = copy;
			next = lexer.accept();
			return isValid;
		}
	
		else
		{
			System.out.println("Parse error:"+current.getTokenText()+" term was not found");
			return false;
		}
			// Signal that a term was not found
			
		//return false;
	}
	
	/**********
	 * The compute method is passed a tree as an input parameter and computes the value of the
	 * tree based on the operator nodes and the value node in the tree.  Precedence is encoded
	 * into the tree structure, so there is no need to deal with it during the evaluation.
	 * 
	 * @param r - The input parameter of the expression tree
	 * 
	 * @return  - A double value of the result of evaluating the expression tree
	 */
	public static double compute(ExprNode r) {

		// Check to see if this expression tree node is an operator.
		if ((r.getOp().getTokenKind() == Kind.SYMBOL) && ((r.getOp().getTokenCode() == 6) ||
				(r.getOp().getTokenCode() == 7) || (r.getOp().getTokenCode() == 8) ||
				(r.getOp().getTokenCode() == 9))) {

			// if so, fetch the left and right sub-tree references and evaluate them
			double leftValue = compute(r.getLeft());
			double rightValue = compute(r.getRight());
			
			// Give the value for the left and the right sub-trees, use the operator code
			// to select the correct operation
			double result = 0;
			switch ((int)r.getOp().getTokenCode()) {
			case 6: result = leftValue + rightValue; break;
			case 7: result = leftValue - rightValue; break;
			case 8: result = leftValue * rightValue; break;
			case 9: result = leftValue / rightValue; break;
			}
			
			// Display the actual computation working from the leaves up to the root
			System.out.println("   " + result + " = " + leftValue + r.getOp().getTokenText() + rightValue);
			
			// Return the result to the caller
			return result;
		}
		
		else if ((r.getOp().getTokenKind() == Kind.RESERVED_WORD) && ((r.getOp().getTokenCode() == 3))) {

			// if so, fetch the left and right sub-tree references and evaluate them
			//double leftValue = compute(r.getLeft());
			double rightValue = compute(r.getLeft());
			
			// Give the value for the left and the right sub-trees, use the operator code
			// to select the correct operation
			double result =  Math.sqrt(rightValue);
			
			
			// Display the actual computation working from the leaves up to the root
			System.out.println("   " + result + " = "  + r.getOp().getTokenText() + rightValue);
			
			// Return the result to the caller
			return result;
		}
		
		else if ((r.getOp().getTokenKind() == Kind.SYMBOL) && ((r.getOp().getTokenCode() == 4)||
				(r.getOp().getTokenCode() == 5) )) 
		{
			//double result =compute(tree);

			// Evaluate the expression tree
				//System.out.println("\nThe evaluation of the tree:");
				//System.out.println("\nThe resulting value is: " + compute(tree));
			return result;
					
		}
		
		// If the node is not an operator, determine what it is and fetch the value 
		else if (r.getOp().getTokenKind() == Kind.INTEGER) {
			Scanner convertInteger = new Scanner(r.getOp().getTokenText());
			Double result = convertInteger.nextDouble();
			convertInteger.close();
			return result;
		}
		
		else if (r.getOp().getTokenKind() == Kind.FLOAT) {
			Scanner convertFloat = new Scanner(r.getOp().getTokenText());
			Double result = convertFloat.nextDouble();
			convertFloat.close();
			return result;
		}
		
		// If it is not a recognized element, treat it as a value of zero
		else return 0.0;
		
	}
	
	
	/**********
	 * This is the mainline that drives the demonstration
	 */
	public static void main(String[] args) {

		// Show the source of the expression
		System.out.println();
		System.out.println("The expression is: " + theExpression);
		System.out.println();

		// Set up the Scanner and the Lexer
		lexer = new Lexer(theReader);
		current = lexer.accept();
		next = lexer.accept();

		// Invoke the parser and the tree builder
		boolean isValid = addSubExpr();
		System.out.println("The expression is valid: " + isValid + "\n");
		if (isValid) {
			
			// Display the expression tree
			ExprNode theTree = exprStack.pop();
			System.out.println(theTree);
			
			// Evaluate the expression tree
			System.out.println("\nThe evaluation of the tree:");
			System.out.println("\nThe resulting value is: " + compute(theTree));
		}
	}

}
