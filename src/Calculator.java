import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Stack;

public class Calculator extends JFrame{

    public  Stack<Double> operandStack= new Stack<>();
    public Stack<String> operatorStack = new Stack<>();

    public Calculator(){

        setTitle("计算器");
        setSize(266,340);
        //# 因为JFrame只是一个框架，用Content Pane来装窗口能显示的所有组件
        //此方法先将组件放入容器中，再置为JFrame面板
        Container c=getContentPane();  
        c.setLayout(null);


        JTextArea jt=new JTextArea(100,100);
        jt.setFont(new Font("Aria",Font.BOLD,34));
        jt.setLineWrap(true);
        // JScrollPane是带有滚动条的面板，是一种容器，只能添加一个组件
        JScrollPane sp=new JScrollPane(jt);
        jt.setCaretPosition(jt.getDocument().getLength());
        // 相对于容器最左边的距离，相对于容器最上面的距离，宽高
        sp.setBounds(0,0,250,100);
        c.add(sp);

        JPanel p=new JPanel();
        // 创建具有指定行数、列数以及组件水平、纵向一定间距的网格布局。
        p.setLayout(new GridLayout(8,6,0,0));
        
        // 确定位置以及宽高
        p.setBounds(0,100,250,200);
        String[] num={"(",")","AC","/","7","8","9","*","4","5","6","-","1","2","3","+","0",".","DEL","=","abs","int","sin","cos","tan","1/x","sqrt","x^2","x^3","ln","e^x"};
        
        JButton[] jb=new JButton[num.length];
        for(int i=0;i<num.length;i++){
        	
        	// 给每个按钮附上对应的符号
            jb[i]=new JButton(num[i]);
            p.add(jb[i]);
        }
        c.add(p);
        
        
        // 添加监听器并对一元操作进行运算
        for(int i=0;i<18;i++){
            if(i!=2){
                final int j=i;
                jb[i].addActionListener(e-> jt.append(num[j]));
            }
        }
        // 清零
        jb[2].addActionListener(e->{
            jt.setText("");
            operandStack.clear();
            operatorStack.clear();
        });
        
        // 删除操作，获取子字符串
        jb[18].addActionListener(e->{
            try{
                jt.setText(jt.getText().substring(0,jt.getText().length()-1));
            }catch(Exception ignored) { }//忽略这个异常   IDEA就是好用！！！
        });
        // “=”时，以字符串形式输出界面上的结果，并自动加"#"
        jb[19].addActionListener(e->{
            try{
                double x= calculateString(jt.getText()+"#");
                jt.setText("");
                jt.append(String.valueOf(x));
            }catch(Exception ex){
                if(ex.getMessage()==null)
                    jt.setText("ERROR!");
                else
                    jt.setText(ex.getMessage());
            }
        });
        
        // 一些基本的一元操作
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
        jb[30].addActionListener(e->{
            double x= Double.valueOf(jt.getText());
            jt.setText("");
            jt.append(String.valueOf(Math.pow(Math.E,x)));
        });
        //禁止文本域的enter换行
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        jt.getInputMap().put(enter, "none");

        this.getRootPane().setDefaultButton(jb[19]);
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

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void calculate(){
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
        if (b.equals("/")) {
            if(c==0)
                throw new ArithmeticException("DivideByZero!");//不可修改为Exception
            // Exception的异常是必须处理的,是受控异常;而ArithmeticException 不是必须处理的 ,受控异常必须强制处理
            e = d / c;
            operandStack.push(e);
        }
    }
    
    // 主要处理优先级
    public Double calculateString(String text){
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
//                            System.out.println(operatorStack.pop());
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