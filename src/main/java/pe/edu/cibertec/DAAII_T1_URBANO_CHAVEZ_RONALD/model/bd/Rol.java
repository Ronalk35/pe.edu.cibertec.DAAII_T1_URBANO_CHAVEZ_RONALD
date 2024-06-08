package pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.bd;

import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer idrol;
    private String nomrol;

}
