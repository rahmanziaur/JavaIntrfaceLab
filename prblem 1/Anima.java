abstract class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    void sleep() {
        System.out.println(name + " is sleeping");
    }

    abstract void makeSound(); 
}

interface Swimmer {
    void swim();
}

interface Flyer {
    void fly();
}

class Dog extends Animal implements Swimmer {
    Dog(String name) {
        super(name);
    }

    void makeSound() {
        System.out.println(name + " says Woof!");
    }

    public void swim() {
        System.out.println(name + " is swimming");
    }
}

class Cat extends Animal {
    Cat(String name) {
        super(name);
    }

    void makeSound() {
        System.out.println(name + " says Meow!");
    }
}

class Bird extends Animal implements Flyer {
    Bird(String name) {
        super(name);
    }

    void makeSound() {
        System.out.println(name + " says Tweet!");
    }

    public void fly() {
        System.out.println(name + " is flying");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog("Buddy");
        dog.eat();
        dog.makeSound();
        dog.sleep();
        dog.swim();

        Cat cat = new Cat("Whiskers");
        cat.eat();
        cat.makeSound();
        cat.sleep();

        Bird bird = new Bird("Tweety");
        bird.eat();
        bird.makeSound();
        bird.sleep();
        bird.fly();
    }
}