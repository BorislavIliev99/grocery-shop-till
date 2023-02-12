package com.example.groceryshoptill.repositories;

import com.example.groceryshoptill.models.Admin;
import com.example.groceryshoptill.repositories.contracts.AdminRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

    private final SessionFactory sessionFactory;

    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Admin register(Admin admin) {
        try (Session session = sessionFactory.openSession()) {
            session.save(admin);

            return admin;
        }
    }

    @Override
    public Optional<Admin> getAdminByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Admin> query = session.createQuery("from Admin where username = :username", Admin.class);
            query.setParameter("username", username);

            return query.uniqueResultOptional();
        }
    }

}
