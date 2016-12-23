package www.atomato.com.tomato.data;
public class Person {
    private int id;
    private String name;
    private String age;

    public Person() {
        this.id = -1;
        this.name = "";
        this.age = "";
    }

    public Person(int id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Person(Person person) {
        this.id = person.id;
        this.name = person.name;
        this.age = person.age;
    }

    public Person getPerson(){
        return this;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAge(String blog) {
        this.age = blog;
    }

    public String getAge() {
        return this.age;
    }

    public String toString() {
        return "Person \nid = " + id + "\nname = " + name + "\nblog = " + age + "\n";
    }
}