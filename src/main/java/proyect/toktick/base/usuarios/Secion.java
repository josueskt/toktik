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
@Table(name = "secion", schema = "public")
@Data
public class Secion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "navegador", length = 400)
    private String navegador;

    @Column(name = "activa")
    private Boolean activa;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
}