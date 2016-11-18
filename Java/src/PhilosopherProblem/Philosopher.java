package PhilosopherProblem;

/**
 * Created by 大男孩 on 2016/11/17.
 */
public class Philosopher {
    protected int hp = 20;
    protected String name;
    static boolean chopsitcks1 = true;
    static boolean chopsitcks2 = true;
    static boolean chopsitcks3 = true;
    static boolean chopsitcks4 = true;
    static boolean chopsitcks5 = true;

    public int getHp() {
        return hp;
    }

    public Philosopher(String name) {
        this.name = name;
    }
    public void thinking(){
        System.out.println(name + " is thinking...His hp is " + hp);
        hp--;
    }
    public void eat(){}
    public void afterEat(){}
}
class Philosoper1 extends Philosopher{
    public Philosoper1(String name) {
        super(name);
    }

    public void eat() {
        if(chopsitcks1 && chopsitcks2){
            hp = 20;
            System.out.println(name + " is eating...His hp is " + hp);
            chopsitcks1 = false;
            chopsitcks2 = false;
        }
        else {
            hp--;
            System.out.println(name + " can't eating...His hp is " + hp);
        }
    }

    public void afterEat() {
        chopsitcks1 = true;
        chopsitcks2 = true;
    }
}

class Philosoper2 extends Philosopher{
    public Philosoper2(String name) {
        super(name);
    }

    public void eat() {
        if(chopsitcks2 && chopsitcks3) {
            hp = 20;
            System.out.println(name + " is eating...His hp is " + hp);
            chopsitcks2 = false;
            chopsitcks3 = false;
        }
        else {
            hp--;
            System.out.println(name + " can't eating...His hp is " + hp);
        }
    }

    public void afterEat() {
        chopsitcks2 = true;
        chopsitcks3 = true;
    }
}

class Philosoper3 extends Philosopher{
    public Philosoper3(String name) {
        super(name);
    }

    public void eat() {
        if(chopsitcks3 && chopsitcks4) {
            hp = 20;
            System.out.println(name + " is eating...His hp is " + hp);
        }
        else {
            hp--;
            System.out.println(name + " can't eating...His hp is " + hp);
        }
    }

    public void afterEat() {
        chopsitcks3 = true;
        chopsitcks4 = true;
    }
}

class Philosoper4 extends Philosopher{
    public Philosoper4(String name) {
        super(name);
    }

    public void eat() {
        if(chopsitcks4 && chopsitcks5) {
            hp = 20;
            System.out.println(name + " is eating...His hp is " + hp);
        }
        else {
            hp--;
            System.out.println(name + " can't eating...His hp is " + hp);
        }
    }

    public void afterEat() {
        chopsitcks4 = true;
        chopsitcks5 = true;
    }
}

class Philosoper5 extends Philosopher{
    public Philosoper5(String name) {
        super(name);
    }

    public void eat() {
        if(chopsitcks5 && chopsitcks1) {
            hp = 20;
            System.out.println(name + " is eating...His hp is " + hp);
        }
        else {
            hp--;
            System.out.println(name + " can't eating...His hp is " + hp);
        }
    }

    public void afterEat() {
        chopsitcks1 = true;
        chopsitcks5 = true;
    }
}
