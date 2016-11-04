package calculator;

import java.util.Stack;

/**
 * Created by 大男孩 on 2016/11/3.
 */
public class TransferToRPN {
    //判断符号优先级
    public int prior(char op){
        if(op == '+'|| op == '-')
            return 1;
        else if (op == '*' || op == '/')
            return 2;
        return 0;
    }

    public String transferToRPN(String s){
        Stack op = new Stack();
        String rpn = new String();
        char c[] = new char[50];
        char ch;
        int j = 0;
        for(int i = 0; i < s.length();i++){
            ch = s.charAt(i);
            if(ch >= '0' && ch <='9'){
                c[j] = ch;
                j++;
            }//如果是数字，直接入栈。
            else{
                if(ch == '(')
                    op.push(Character.valueOf(ch));//如果是(号，直接入栈。
                else{
                    if(ch == ')'){
                        while(((Character)op.peek()).charValue()!='('){
                            c[j] = ((Character)op.peek()).charValue();
                            j++;
                            op.pop();
                        }//如果是)号，将栈中(前的符号依次给ch[]并且删除(号。
                        op.pop();
                    }
                    else if(ch == '+' || ch == '-' || ch == '*' || ch == '/'){
                        if(op.empty())
                            op.push(Character.valueOf(ch));//如果栈为空，符号直接入栈。
                        else{
                            if(prior(ch) > prior(((Character)op.peek()).charValue()))
                                op.push(Character.valueOf(ch));//若是比栈顶的运算符号优先级高，入栈。
                            else{
                                while(!op.empty() && prior(ch) <= prior(((Character)op.peek()).charValue())){
                                    c[j] = ((Character)op.peek()).charValue();
                                    j++;
                                    op.pop();//栈顶符号写入c[j]，j++，删除栈顶,最后把该符号入栈。
                                }
                                op.push(Character.valueOf(ch));
                            }
                        }
                    }
                }
            }
        }
        while (!op.empty()){
            c[j] = ((Character)op.peek()).charValue();
            j++;
            op.pop();
        }
        rpn = String.valueOf(c, 0, j);
        return rpn;
    }
}
