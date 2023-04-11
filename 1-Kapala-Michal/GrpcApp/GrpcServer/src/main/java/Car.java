public class Car {
    String name;
    int age;
    String image;
    boolean isNew;

    public Car(String name, int age, String image, boolean isNew) {
        this.name = name;
        this.age = age;
        this.image = image;
        this.isNew = isNew;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", image='" + image + '\'' +
                ", isNew=" + isNew +
                '}';
    }
}
