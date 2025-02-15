package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
 public class BurgerDaoImpl implements BurgerDao{
    public final EntityManager entityManager;

    @Autowired
    public BurgerDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    public Burger findById(long id) {
       Burger burger =  entityManager.find(Burger.class,id);
       if(burger == null){
           throw new BurgerException("Burger not found: " + id, HttpStatus.NOT_FOUND);
       }
       return burger;
    }

    @Override
    public List<Burger> findAll() {
        TypedQuery<Burger> allFind = entityManager.createQuery("SELECT e FROM Burger e",Burger.class);
        return allFind.getResultList();
    }

    @Override
    public List<Burger> findByPrice(Integer price) {
        TypedQuery<Burger> query = entityManager.createQuery("SELECT b FROM Burger b WHERE b.price> :price ORDER BY b.price DESC", Burger.class);
        query.setParameter("price",price);
        return query.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
       TypedQuery<Burger> query = entityManager.createQuery("SELECT b FROM Burger b WHERE b.breadType> :breadType ORDER BY b.name DESC", Burger.class);
       query.setParameter("breadType", breadType);
       return query.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
        TypedQuery<Burger> query = entityManager.createQuery("SELECT b FROM Burger b WHERE b.contents ILIKE CONCAT('%',:content,'%') ", Burger.class);
        query.setParameter("content", content);
        return query.getResultList();

    }

    @Transactional
    @Override
    public Burger update(Burger burger) {
        entityManager.merge(burger);
        return burger;
    }

    @Transactional
    @Override
    public Burger remove(long id) {
        Burger burger = entityManager.find(Burger.class, id);
        if (burger == null) {
            throw new BurgerException("Burger not found", HttpStatus.NOT_FOUND);
        }
        entityManager.remove(burger);
        return burger;
    }
}
