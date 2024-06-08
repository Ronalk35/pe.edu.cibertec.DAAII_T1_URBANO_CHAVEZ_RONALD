package pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.bd.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //select * from usuario where nomusuario = ''
    Usuario findByNomusuario(String nomusuario);
    // Store Procedure
    //@Query(value = "{call spActualizarUsuario(:nombres, :apellidos, :activo, :idusuario)}")
    @Transactional
    @Modifying
    @Query(value = "Update usuario set nombres=:nombres, apellidos=:apellidos, " +
            " activo=:activo where idusuario=:idusuario",
            nativeQuery = true)
    void actualizarUsuario(@Param("nombres") String nombres,
                           @Param("apellidos") String apellidos,
                           @Param("activo") Boolean activo,
                           @Param("idusuario") Integer idusuario


    );
}

