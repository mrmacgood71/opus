package it.macgood.opus.works;

import com.fasterxml.jackson.annotation.JsonView;
import it.macgood.opus.views.View;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(unique = false)
    @JsonView(View.GetProfileInfo.class)
    private String name;

    @Column
    @JsonView(View.GetProfileInfo.class)
    private String description;

    @Column
    @JsonView(View.GetProfileInfo.class)
    private String path;

    @Column(unique = false)
    @JsonView(View.GetProfileInfo.class)
    private String originalFileName;

    @Column
    @JsonView(View.GetProfileInfo.class)
    private String previewPhoto;
}
