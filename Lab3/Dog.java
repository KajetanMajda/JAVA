package Lab3;

public class Dog {
    int breed;
    int age;
    int color;

    public Dog(int breed, int age, int color) {
        this.breed = breed;
        this.age = age;
        this.color = color;
    }

    public String bark(){
        return "Hau Hau";
    }

    public String sleep() {
        return "Spij";
    }
}


/*Stwórz klasę Dog. Niech ma trzy pola:
breed (rasa), age i color. Stwórz
konstruktor. Niech ma
dwie metody: bark() i sleep().
Zademonstruj jak działa.*/