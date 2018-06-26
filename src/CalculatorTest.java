import static org.junit.Assert.*;

import java.util.Stack;

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
//		String b = "+";
//        double c = 3.5;
//        double d = 4.5;
		calculator.operandStack.push(3.5);
		calculator.operatorStack.push("+");
		calculator.operandStack.push(4.5);
        calculator.calculate();
//		calculate();
        assertEquals(8,calculator.operandStack.pop(),0);
//        System.out.println("成功运行结果为：" + calculator.operandStack.pop());
        
        
        calculator.operandStack.push(7.0);
		calculator.operatorStack.push("*");
		calculator.operandStack.push(3.0);
        calculator.calculate();
//		calculate();
        assertEquals(2.0,calculator.operandStack.pop(),0);
//        
        calculator.operandStack.push(7.0);
		calculator.operatorStack.push("/");
		calculator.operandStack.push(0.0);
        calculator.calculate();
//		calculate();
        System.out.println("成功运行结果为：" + calculator.operandStack.pop());
		
	}
}