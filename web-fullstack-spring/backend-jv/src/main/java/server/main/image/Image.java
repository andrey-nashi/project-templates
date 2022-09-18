package server.main.image;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="IMAGES")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long owner;
    private String name;

    public Image() {

    }

    public Image (Long owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

}