/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Annika
 * Immutable: https://allaroundjava.com/immutable-entities-hibernate/
 */
@Entity
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @ManyToMany(mappedBy = "requests")
    private List<Category> categories;

    /*
    Useful if the database should contain an 'updated-field'
    @PreUpdate
    public void setLastUpdate() {  this.updated = new Date(); }
     */
    public Request() {
    }

    public Request(List<Category> categories) {
        this.timestamp = new Date();
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    /*
    No setter for timestamp due to the fact that it should never be changed
     */
    public Date getTimestamp() {
        return timestamp;
    }
}
