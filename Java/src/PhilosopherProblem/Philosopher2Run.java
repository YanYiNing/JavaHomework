package PhilosopherProblem;

/**
 * Created by 大男孩 on 2016/11/17.
 */
public class Philosopher2Run implements Runnable{
    public void run() {
        Philosoper2 philosoper2 = new Philosoper2("philosoper2");
        for(int i = 1; i <= 200; i++){
            if(philosoper2.hp <= 10) {
                philosoper2.eat();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                philosoper2.afterEat();
            }
            else {
                philosoper2.thinking();
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
