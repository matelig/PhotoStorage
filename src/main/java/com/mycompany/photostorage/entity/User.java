package com.mycompany.photostorage.entity;
// Generated 2017-04-25 22:04:09 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name="user"
    ,catalog="photokeeper"
    , uniqueConstraints = @UniqueConstraint(columnNames="nickname") 
)
public class User  implements java.io.Serializable {


     private Integer idu;
     private String nickname;
     private String password;
     private Set<Category> categories = new HashSet<Category>(0);
     private Set<Photo> photos = new HashSet<Photo>(0);

    public User() {
    }

	
    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
    public User(String nickname, String password, Set<Category> categories, Set<Photo> photos) {
       this.nickname = nickname;
       this.password = password;
       this.categories = categories;
       this.photos = photos;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idu", unique=true, nullable=false)
    public Integer getIdu() {
        return this.idu;
    }
    
    public void setIdu(Integer idu) {
        this.idu = idu;
    }

    
    @Column(name="nickname", unique=true, nullable=false, length=45)
    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    
    @Column(name="password", nullable=false, length=45)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<Category> getCategories() {
        return this.categories;
    }
    
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<Photo> getPhotos() {
        return this.photos;
    }
    
    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }




}


