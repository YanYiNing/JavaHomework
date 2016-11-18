package PhilosopherProblem;

/**
 * Created by 大男孩 on 2016/11/17.
 */
public class Philosopher4Run implements Runnable{
    public void run() {
        Philosoper4 philosoper4 = new Philosoper4("philosoper4");
        for(int i = 1; i <= 200; i++){
            if(philosoper4.hp <= 10) {
                philosoper4.eat();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                philosoper4.afterEat();
            }
            else {
                philosoper4.thinking();
                try {
                    Thread.sleep(140);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
