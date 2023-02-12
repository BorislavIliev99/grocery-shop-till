package com.example.groceryshoptill.repositories;

import com.example.groceryshoptill.enums.DealType;
import com.example.groceryshoptill.models.Deal;
import com.example.groceryshoptill.models.Product;
import com.example.groceryshoptill.repositories.contracts.DealRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DealRepositoryImpl implements DealRepository {

    private final SessionFactory sessionFactory;

    public DealRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Deal> getDeal(int productId, DealType dealType) {
        try (Session session = sessionFactory.openSession()) {
            Query<Deal> query = session.createQuery(
                    "from Deal where product.id = :id and dealType = :dealType", Deal.class);
            query.setParameter("id", productId);
            query.setParameter("dealType", dealType);

            return query.uniqueResultOptional();
        }
    }

    @Override
    public Deal addProductToDeal(Deal deal) {
        try (Session session = sessionFactory.openSession()) {
            session.save(deal);

            return deal;
        }
    }

    @Override
    public boolean isProduct2For3(Product product) {
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery(
                    "from Deal where product.name = :name and dealType = 'TWO_FOR_THREE'", Product.class);
            query.setParameter("name", product.getName());

            return query.uniqueResultOptional().isPresent();
        }
    }

    @Override
    public boolean isProductBuyOneGetOneHalfPrice(Product product) {
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery(
                    "from Deal where product.name = :name and dealType = 'BUY_ONE_GET_ONE_HALF_PRICE'",
                    Product.class);
            query.setParameter("name", product.getName());

            return query.uniqueResultOptional().isPresent();
        }
    }

}
