package calculator;
/**
 * <p> Title: Tester. </p>
 * 
 * <p> Description: This class verifies that all the mathematical functions works properly </p>
 * 
 * <p> Copyright: MD.Ashfak © 2020 </p>
 * 
 * @author  MD.Ashfak Us Salehin
 * 
 *
 */

public class Tester {

	public static void main(String[] args) {
		
	int counter = 0;
	CalculatorValue addition = new CalculatorValue(2);
	CalculatorValue subtraction = new CalculatorValue(2);
	CalculatorValue division = new CalculatorValue(2);
	CalculatorValue multiplication = new CalculatorValue(2);
	CalculatorValue sqrt = new CalculatorValue(9);
	CalculatorValue number = new CalculatorValue(2);
	
	/*****
	 * TEST 1: Testing addition function
	 */
	System.out.println("TEST 1: Testing addition function:\n");
	System.out.println("Adding: 2+2 ");
	System.out.println("Expected result: 4 ");
	addition.add(number);
	System.out.println("Actual result: "+ addition.measuredValue);
	if(addition.measuredValue == 4)
	{
		System.out.println("\n TEST 1 PASSED !!! \n");
		counter++;
	}
	else
		System.out.println("\n TEST 1 FAILED !!! \n");
	
	
	/*****
	 * TEST 2: Testing subtraction function
	 */
	System.out.println("\n TEST 2: Testing subtraction function: \n");
	System.out.println("Subtracting: 2-2 ");
	System.out.println("Expected result: 0 ");
	subtraction.sub(number);
	System.out.println("Actual result: "+ subtraction.measuredValue);
	if(subtraction.measuredValue == 0)
	{
		System.out.println("\n TEST 2 PASSED !!! \n ");
		counter++;
	}
	else
		System.out.println("\n TEST 2 FAILED !!! \n");
	
	/*****
	 * TEST 3: Testing division function
	 */
	System.out.println("\n TEST 3: Testing division function: \n");
	System.out.println("Dividing: 2/2 ");
	System.out.println("Expected result: 1 ");
	division.div(number);
	System.out.println("Actual result: "+ division.measuredValue);
	if(division.measuredValue == 1)
	{
		System.out.println("\n TEST 3 PASSED !!! \n ");
		counter++;
	}
	else
		System.out.println("\n TEST 3 FAILED !!! \n");
	
	
	/*****
	 * TEST 4: Testing multiplication function
	 */
	System.out.println("\n TEST 4: Testing multiplication function: \n");
	System.out.println("Multiplying: 2x2 ");
	System.out.println("Expected result: 4 ");
	multiplication.mpy(number);
	System.out.println("Actual result: "+ multiplication.measuredValue);
	if(multiplication.measuredValue == 4)
	{
		System.out.println("\n TEST 4 PASSED !!! \n ");
		counter++;
	}
	else
		System.out.println("\n TEST 4 FAILED !!! \n");
	
	/*****
	 * TEST 5: Testing Square Root function
	 */
	System.out.println("\n TEST 5: Testing Square Root function: \n");
	System.out.println("Finding Square Root of : 9 ");
	System.out.println("Expected result: 3 ");
	sqrt.square_root();
	System.out.println("Actual result: "+ sqrt.measuredValue);
	if(sqrt.measuredValue == 3)
	{
		System.out.println("\n TEST 5 PASSED !!! \n ");
		counter++;
	}
	else
		System.out.println("\n TEST 5 FAILED !!! \n");
	
	System.out.println("\nNumber of test preformed : 5 ");
	System.out.println("Number of test passed : "+counter);
	System.out.println("Number of test failed : "+(5-counter));
	}
}
