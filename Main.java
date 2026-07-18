 public class Main {

    public static void main(String[] args) {

        Dog dog = new Dog("Buddy");

        System.out.println("--- Dog ---");
        dog.eat();
        dog.makeSound();
        dog.sleep();
        dog.swim();

        System.out.println();

        Cat cat = new Cat("Whiskers");

        System.out.println("--- Cat ---");
        cat.eat();
        cat.makeSound();
        cat.sleep();

        System.out.println();

        Bird bird = new Bird("Tweety");

        System.out.println("--- Bird ---");
        bird.eat();
        bird.makeSound();
        bird.sleep();
        bird.fly();

    }

}
