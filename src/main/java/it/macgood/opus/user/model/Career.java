package it.macgood.opus.user.model;

import com.fasterxml.jackson.annotation.JsonView;
import it.macgood.opus.views.View;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "career")
public class Career {

    @Id
    @GeneratedValue
    @JsonView(View.GetProfileInfo.class)
    private Integer id;

    @JsonView(View.GetProfileInfo.class)
    private String name;

}
