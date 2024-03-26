package org.lily.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.lily.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public CourseDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Course> getAllCourses() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Course", Course.class).list();
        }
    }

    public Course getCourse(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Course where id = :id", Course.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }
}