
public class test {

	public static void main(String[] args) {
		Calculator calculator = new Calculator();
		String a1="123+234";
		calculator.calculateString(a1);
		double res = calculator.operandStack.peek();
		System.out.println(res);
	}
}
