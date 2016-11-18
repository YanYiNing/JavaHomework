package PhilosopherProblem;

/**
 * Created by 大男孩 on 2016/11/17.
 */
public class Philosopher1Run implements Runnable{
    public void run() {
        Philosoper1 philosoper1 = new Philosoper1("philosoper1");
        for(int i = 1; i <= 200; i++){
            if(philosoper1.hp <= 10) {
                philosoper1.eat();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                philosoper1.afterEat();
            }
            else {
                philosoper1.thinking();
                try {
                    Thread.sleep(110);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

