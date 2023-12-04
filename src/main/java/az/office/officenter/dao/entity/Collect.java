package az.office.officenter.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;


@Entity
@Setter
@Getter
@Table(name = "collection")
public class Collect  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "collection"
            , cascade = CascadeType.ALL
            ,fetch = FetchType.LAZY)
    private List<Category> categories;

    @Override
    public String toString() {
        return "Collection{id=%d, name='%s'}".formatted(id, name);
    }

}