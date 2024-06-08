package pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.bd.Rol;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.bd.Usuario;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.repository.RolRepository;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.repository.UsuarioRepository;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.util.RandomPassword;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    private RandomPassword randomPassword;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );


    @Override
    public Usuario buscarUsuarioXNomUsuario(String nomusuario) {
        return usuarioRepository.findByNomusuario(nomusuario);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        try {
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                throw new IllegalArgumentException("La contraseña no puede estar vacía.");
            }
            usuario.setActivo(true);
            Rol usuarioRol = rolRepository.findByNomrol("ADMIN");
            usuario.setRoles(new HashSet<>(Arrays.asList(usuarioRol)));
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Codificar la contraseña
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            // Registrar el error
            System.err.println("Error al guardar el usuario: " + e.getMessage());
            e.printStackTrace();
            throw e; // Lanzar la excepción para manejarla en el controlador
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        usuarioRepository.actualizarUsuario(
                usuario.getNombres(),usuario.getApellidos(),
                usuario.getActivo(),usuario.getIdusuario()
        );
    }

    @Override
    public List<Usuario> listarUsuario() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuarioXIdUsuario(Integer idusuario) {
        return usuarioRepository.findById(idusuario).orElse(null);
    }

    @Override
    public void cambiarContrasena(String username, String nuevaContrasena) {
        // Verificar si la nueva contraseña cumple con los requisitos
        if (!PASSWORD_PATTERN.matcher(nuevaContrasena).matches()) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial.");
        }

        // Paso 1: Obtener el usuario por su nombre de usuario
        Usuario usuario = usuarioRepository.findByNomusuario(username);

        if (usuario != null) {
            // Paso 2: Codificar la nueva contraseña
            String encodedPassword = passwordEncoder.encode(nuevaContrasena);

            // Paso 3: Actualizar la contraseña del usuario
            usuario.setPassword(encodedPassword);

            // Paso 4: Guardar los cambios en la base de datos
            usuarioRepository.save(usuario);
        }
    }
}
