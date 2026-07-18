 class Bird extends Animal implements Flyer {

    Bird(String name) {
        super(name);
    }

    @Override
    void makeSound() {
        System.out.println(name + " says Tweet!");
    }

    @Override
    public void fly() {
        System.out.println(name + " is flying");
    }

}
