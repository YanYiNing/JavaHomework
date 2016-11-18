package PhilosopherProblem;

/**
 * Created by 大男孩 on 2016/11/17.
 */
public class Philosopher3Run implements Runnable{
    public void run() {
        Philosoper3 philosoper3 = new Philosoper3("philosoper3");
        for(int i = 1; i <= 200; i++){
            if(philosoper3.hp <= 10) {
                philosoper3.eat();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                philosoper3.afterEat();
            }
            else {
                philosoper3.thinking();
                try {
                    Thread.sleep(130);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
