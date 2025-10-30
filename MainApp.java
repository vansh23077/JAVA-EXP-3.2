package com.example.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // CREATE
            Student s1 = new Student("John", 22);
            session.save(s1);
            System.out.println("Insert Success!");

            // READ
            List<Student> students = session.createQuery("from Student", Student.class).list();
            System.out.println("Student List:");
            for (Student s : students) {
                System.out.println("ID: " + s.getId() + ", Name: " + s.getName() + ", Age: " + s.getAge());
            }

            // UPDATE
            Student existing = session.get(Student.class, s1.getId());
            if (existing != null) {
                existing.setAge(23);
                session.update(existing);
                System.out.println("Update Success!");
            }

            // DELETE
            Student toDelete = session.get(Student.class, s1.getId());
            if (toDelete != null) {
                session.delete(toDelete);
                System.out.println("Delete Success!");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
