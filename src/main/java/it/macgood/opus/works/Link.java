package it.macgood.opus.works;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Link(String name) {
        this.name = name;
    }
}
