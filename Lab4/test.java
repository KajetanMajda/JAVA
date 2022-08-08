package Lab4;

public class test {
    public static void main(String[] args) {
        Dog dog = new Dog("labrador", 1);
        dog.setAge(-10);
        System.out.println(dog.getAge());
        dog.hauHau();
    }
}