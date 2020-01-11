/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Category;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Annika
 */
public class CategoryFacade {

    private static CategoryFacade instance;
    private static EntityManagerFactory emf;

    public static CategoryFacade getCategoryFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CategoryFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Category> getCategories() {
        EntityManager em = getEntityManager();
        List<Category> categories;

        try {
            categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        } finally {
            em.close();
        }
        return categories;
    }
    
    public Category getCategory(String name) {
        EntityManager em = getEntityManager();
        Category res;
        
        try {
            TypedQuery<Category> tq 
                    = em.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class);
            tq.setParameter("name", name);
            res = tq.getSingleResult();
        } catch(NoResultException e) {
            return null;
        } finally {
            em.close();
        }
        return res;
    }
}
