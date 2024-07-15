package qrstampBack;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import lombok.Getter;
//import lombok.Setter;

@Entity
//@Getter
//@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userid;
    private boolean[] stamps = new boolean[10];

    // Getter, Setter

    public Long getId(Long userid) {
        return userid;
    }

    public void setId(String userid) {
        this.userid = userid;
    }

    public String getUserid(String userid) {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}

