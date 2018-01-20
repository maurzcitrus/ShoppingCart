package nus.iss.ejava.fruitshop.business;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nus.iss.ejava.fruitshop.model.Fruit;

@Stateless
public class FruitBean {
    
    @PersistenceContext(unitName = "fruitshopPU")
    private EntityManager em;
    
    public List<Fruit> findAllFruits() {

        TypedQuery<Fruit> query = em.createQuery("select f from Fruit f", Fruit.class);      
        return (query.getResultList());
    }
    
    public void persist(Object object) {
        em.persist(object);
    }
}
