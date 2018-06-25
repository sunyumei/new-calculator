import static org.junit.Assert.*;

import org.junit.Test;

public class CalculatorTest {
	
	Calculator calculator = new Calculator();

//	@Test
//	public void testCalculator() {
////		fail("Not yet implemented");
//		
//	}

	@Test
	public void testCalculate() {
//		fail("Not yet implemented");
		String b = "+";
        double c = 3.5;
        double d = 4.5;
//        result = calculator.calculate();
        assertEquals(8,calculator.operandStack.pop(),0);
		
	}

	@Test
	public void testCalculateString() {
//		fail("Not yet implemented");
//		String b = "+";
//        double c = 3.5;
//        double d = 4.5;
		System.out.println(1111111);
//        double result = calculator.operandStack.pop();
//        System.out.println(1111111);
        assertEquals(3.5,calculator.calculateString("3.5+4.5"),0);
	}

}
