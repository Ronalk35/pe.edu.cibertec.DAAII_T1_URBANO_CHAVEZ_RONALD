package pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class UsuarioDto {
    private Integer idusuario;
    private String nomusuario;
    private String nombres;
    private String apellidos;
    private Boolean activo;
    private String email;
    private String password;

}