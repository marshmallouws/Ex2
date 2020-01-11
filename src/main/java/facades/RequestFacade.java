/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Category;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Annika
 */
public class RequestFacade {

    private static RequestFacade instance;
    private static EntityManagerFactory emf;

    public static RequestFacade getRequestFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RequestFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void saveRequest(String[] categories) {
        EntityManager em = getEntityManager();
        for(int i = 0; i < categories.length; i++) {
            TypedQuery tq = em.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class);
            tq.setParameter("name", categories[i]);
            
            try {
                tq.getSingleResult();
            } catch (NoResultException e) {
                
            }
        }
    }
            
            
        

    private List<Category> getCategories() {
        EntityManager em = getEntityManager();
        List<Category> categories;

        try {
            categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        } finally {
            em.close();
        }
        return categories;
    }

}
