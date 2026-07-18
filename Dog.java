 class Dog extends Animal implements Swimmer {

    Dog(String name) {
        super(name);
    }

    @Override
    void makeSound() {
        System.out.println(name + " says Woof!");
    }

    @Override
    public void swim() {
        System.out.println(name + " is swimming");
    }

}
