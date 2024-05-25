package proyect.toktick.base.usuarios;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Entity
@Table(name = "file", schema = "public")
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private Short nombre;

    @Column(name = "fk_video")
    private Long fk_video;

    @ManyToOne
    @JoinColumn(name = "fk_video", insertable = false, updatable = false)
    private Video video;
}