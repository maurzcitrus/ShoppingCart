package nus.iss.ejava.fruitshop.model;

import java.io.Serializable;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fruit")
public class Fruit implements Serializable{
    @Id
    @GeneratedValue
    private Integer fruitid;
    
    @Column(length = 15)
    private String fruitName;
    
    @Column(length = 255)
    private String fruitImagePath;

    private int quantity;

    public Integer getFruitid() {
        return fruitid;
    }

    public void setFruitid(Integer fruitid) {
        this.fruitid = fruitid;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public String getFruitImagePath() {
        return fruitImagePath;
    }

    public void setFruitImagePath(String fruitImagePath) {
        this.fruitImagePath = fruitImagePath;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
