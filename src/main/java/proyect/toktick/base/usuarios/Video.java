package proyect.toktick.base.usuarios;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "video", schema = "public")
@Data
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", length = 50)
    private String titulo;

    @Column(name = "description")
    private String description;

    @Column(name = "archivo")
    private String archivo;


    @Column(name = "fecha_pl")
    private java.sql.Timestamp fecha_pl;

    @ManyToOne
    @JoinColumn(name = "fk_usuario",referencedColumnName = "correo")
    private Usuario usuario;

        

}