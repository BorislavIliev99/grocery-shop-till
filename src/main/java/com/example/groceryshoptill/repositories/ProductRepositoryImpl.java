package com.example.groceryshoptill.repositories;

import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.repositories.contracts.ProductRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final SessionFactory sessionFactory;

    public ProductRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Product> getAllProducts() {
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery("from Product", Product.class);

            return query.list();
        }
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery("from Product where name = :name", Product.class);
            query.setParameter("name", name);

            return query.uniqueResultOptional();
        }
    }

    @Override
    public Optional<Product> getProductById(int productId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery("from Product where id = :id", Product.class);

            query.setParameter("id", productId);

            return query.uniqueResultOptional();
        }
    }

    @Override
    public Product createProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.save(product);

            return product;
        }
    }

    @Override
    public Product updateProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
            return product;
        }
    }

    @Override
    public void deleteProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        }
    }

}
