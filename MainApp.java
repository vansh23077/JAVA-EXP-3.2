import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;


@Entity
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;

    public Student() {}

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
}


public class MainApp {

    public static void main(String[] args) {

        
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Student.class);

        
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();

       
        Transaction tx = session.beginTransaction();

        Student s1 = new Student("John", 22);
        session.save(s1);
        System.out.println("Insert Success!");

       
        List<Student> students = session.createQuery("from Student", Student.class).list();
        System.out.println("\nStudent List:");
        for (Student s : students) {
            System.out.println("ID: " + s.getId() + ", Name: " + s.getName() + ", Age: " + s.getAge());
        }

        
        Student existing = session.get(Student.class, s1.getId());
        if (existing != null) {
            existing.setAge(23);
            session.update(existing);
            System.out.println("\nUpdate Success!");
        }

       
        Student toDelete = session.get(Student.class, s1.getId());
        if (toDelete != null) {
            session.delete(toDelete);
            System.out.println("Delete Success!");
        }

       
        tx.commit();
        session.close();
        factory.close();
    }
}
