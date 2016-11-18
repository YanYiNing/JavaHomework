package PhilosopherProblem;

/**
 * Created by 大男孩 on 2016/11/17.
 */
public class Philosopher5Run implements Runnable{
    public void run() {
        Philosoper5 philosoper5 = new Philosoper5("philosoper5");
        for(int i = 1; i <= 200; i++){
            if(philosoper5.hp <= 10) {
                philosoper5.eat();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                philosoper5.afterEat();
            }
            else {
                philosoper5.thinking();
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
