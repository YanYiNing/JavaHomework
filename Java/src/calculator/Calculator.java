package calculator;

import java.util.Stack;

/**
 * Created by 大男孩 on 2016/11/3.
 */
public class Calculator {
    private String s = new String();
    private TransferToRPN rpn = new TransferToRPN();

    public Calculator(String s) {
        this.s = s;
    }
    public  double Result(){
        Stack<Double>number = new Stack();
        double right = 0,left = 0,result = 0;
        char c;
        String rpn = this.rpn.transferToRPN(this.s);
        for(int i = 0; i < rpn.length(); i++){
            c = rpn.charAt(i);
            if(c == '+' || c == '-' || c == '/' || c == '*'){
                right = number.pop().doubleValue();
                left = number.pop().doubleValue();
                result = this.SimpleCalculator(right, left, c);
                number.push(Double.valueOf(result));
            }//不是数字，调用简单计算方法计算栈顶前两步的数字运算结果后，入栈。
            else
                number.push(Double.valueOf(c - 48));//如果是数字，入栈。
        }
        return result;
    }
    public double SimpleCalculator(double r, double l, char op){
        double result = 0;
        switch (op) {
            case '+':return l + r;
            case '-':return l - r;
            case '*':return l * r;
            case '/':return l / r;
        }
        return 0;
    }//简单计算器
}
