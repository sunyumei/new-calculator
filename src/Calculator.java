import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by 28713 on 2016/9/11.
 */
public class Calculator extends JFrame{

    private Stack<Double> operandStack= new Stack<>();
    private Stack<String> operatorStack = new Stack<>();

    private Calculator(){

        setTitle("计算器");
        setSize(266,340);
        Container c=getContentPane();
        c.setLayout(null);


        JTextArea jt=new JTextArea(100,100);
        jt.setFont(new Font("Aria",Font.BOLD,34));
        jt.setLineWrap(true);
        JScrollPane sp=new JScrollPane(jt);
        jt.setCaretPosition(jt.getDocument().getLength());
        sp.setBounds(0,0,250,100);
        c.add(sp);

        JPanel p=new JPanel();
        p.setLayout(new GridLayout(8,6,0,0));

        p.setBounds(0,100,250,200);
        String[] num={"(",")","AC","/","7","8","9","*","4","5","6","-","1","2","3","+","0",".","DEL","=","abs","int","sin","cos","tan","1/x","sqrt","x^2","x^3","ln"};
        
        JButton[] jb=new JButton[num.length];
        for(int i=0;i<num.length;i++){
            jb[i]=new JButton(num[i]);
            p.add(jb[i]);
        }
        c.add(p);

        for(int i=0;i<18;i++){
            if(i!=2){
                final int j=i;
                jb[i].addActionListener(e-> jt.append(num[j]));
            }
        }

        jb[2].addActionListener(e->{
            jt.setText("");
            operandStack.clear();
            operatorStack.clear();
        });
        jb[18].addActionListener(e->{
            try{
                jt.setText(jt.getText().substring(0,jt.getText().length()-1));
            }catch(Exception ignored) { }//忽略这个异常   IDEA就是好用！！！
        });
        jb[19].addActionListener(e->{
            try{
                double x= calculate(jt.getText()+"#");
                jt.setText("");
                jt.append(String.valueOf(x));
            }catch(Exception ex){
                if(ex.getMessage()==null)
                    jt.setText("ERROR!");
                else
                    jt.setText(ex.getMessage());
            }
        });
        jb[20].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf(Math.abs(x)));
        });
        jb[21].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf((int)x));
        });
        jb[22].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf(Math.sin(x)));
        });
        jb[23].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf(Math.cos(x)));
        });
        jb[24].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf(Math.tan(x)));
        });
        jb[25].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf(1/x));
        });
        jb[26].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf(Math.sqrt(x)));
        });
        jb[27].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf(x*x));
        });
        jb[28].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf(x*x*x));
        });
        jb[29].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf(Math.log(x)));
        });
        //禁止文本域的enter换行
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        jt.getInputMap().put(enter, "none");

        this.getRootPane().setDefaultButton(jb[19]);
        //太累！不想研究了
        //下面的，不知道主体是什么，当只有焦点在那个主体上才会响应，哎，不知道怎么弄。
        /*c.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    System.out.println("adssdf");
                    double x= calculate(jt.getText()+"#");
                    jt.setText("");
                    jt.append(String.valueOf(x));
                }
            }
        });*/
        //不知道为什么响应不了键盘事件？？？
        //不知道为什么下面的的这个不行？？？

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void calculate(){
        String b = operatorStack.pop();
        double c = operandStack.pop();
        double d = operandStack.pop();
        double e;
        if (b.equals("+")) {
            e = d + c;
            operandStack.push(e);
        }
        if (b.equals("-")) {
            e = d - c;
            operandStack.push(e);
        }
        if (b.equals("*")) {
            e = d * c;
            operandStack.push(e);
        }
//        if (b.equals("abs")) {
//            e = Math.abs(c);
//            operandStack.push(e);
//        }
        if (b.equals("/")) {
            if(c==0)
                throw new ArithmeticException("DivideByZero!");//不可修改为Exception
            // Exception的异常是必须处理的,是受控异常;而ArithmeticException 不是必须处理的 ,受控异常必须强制处理
            e = d / c;
            operandStack.push(e);
        }
    }

    private Double calculate(String text){
        HashMap<String,Integer> precede=new HashMap<>();
        precede.put("(",0);
        precede.put(")",0);
        precede.put("/",2);
        precede.put("*",2);
        precede.put("-",1);
        precede.put("+",1);
        precede.put("#",0);

        operatorStack.push("#");

        int flag=0;
        for(int i=0;i<text.length();i++){
            String a=String.valueOf(text.charAt(i));
            if(!a.matches("[0-9.]")){
                if(flag!=i)
                    operandStack.push(Double.parseDouble(text.substring(flag,i)));
                flag=i+1;
                while(!(a.equals("#")&&operatorStack.peek().equals("#"))){
                    if(precede.get(a)>precede.get(operatorStack.peek())||a.equals("(")){
                        operatorStack.push(a);
                        break;
                    }else {
                        if(a.equals(")")) {
                            while(!operatorStack.peek().equals("("))
                                calculate();
                            operatorStack.pop();
                            break;
                        }
                        calculate();
                    }
                }

            }
        }

        return(operandStack.pop());
    }


    public static void main(String[] args){
        new Calculator();
    }
}