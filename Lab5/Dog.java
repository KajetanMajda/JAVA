package Lab5;

public class Dog extends Animal {
    public Dog(String name, String color) {
        super(name, color);
    }

    public void makeSound(){
        System.out.printf("[%s:%s] Hau Hau\n", name, color);
    }
}
