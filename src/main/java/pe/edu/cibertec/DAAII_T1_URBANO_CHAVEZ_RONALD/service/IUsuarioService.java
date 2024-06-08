package pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.service;

import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.bd.Usuario;

import java.util.List;

public interface IUsuarioService {


    Usuario buscarUsuarioXNomUsuario(String nomusuario);
    Usuario guardarUsuario(Usuario usuario);

    void actualizarUsuario(Usuario usuario);
    List<Usuario> listarUsuario();
    Usuario buscarUsuarioXIdUsuario(Integer idusuario);


    void cambiarContrasena(String username, String nuevaContrasena);
}