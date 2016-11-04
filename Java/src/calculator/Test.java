package calculator;

import java.util.Scanner;

/**
 * Created by 大男孩 on 2016/11/3.
 */
public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("输入：");
        String string = in.next();
        Calculator calculator = new Calculator(string);
        System.out.println("输出："+calculator.Result());
    }
}
