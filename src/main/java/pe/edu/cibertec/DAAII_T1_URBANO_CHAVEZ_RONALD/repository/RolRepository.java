package pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.bd.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    // select * from rol where nomrol = ''
    Rol findByNomrol(String nomrol);
}
