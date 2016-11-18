package PhilosopherProblem;

/**
  定义了五个哲学家对象，定义了他们的血量
 */
public class Test {
    public static void main(String[] args) {
        Philosopher1Run run1 = new Philosopher1Run();
        Philosopher2Run run2 = new Philosopher2Run();
        Philosopher3Run run3 = new Philosopher3Run();
        Philosopher4Run run4 = new Philosopher4Run();
        Philosopher5Run run5 = new Philosopher5Run();

        Thread thread1 = new Thread(run1);
        Thread thread2 = new Thread(run2);
        Thread thread3 = new Thread(run3);
        Thread thread4 = new Thread(run4);
        Thread thread5 = new Thread(run5);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}
