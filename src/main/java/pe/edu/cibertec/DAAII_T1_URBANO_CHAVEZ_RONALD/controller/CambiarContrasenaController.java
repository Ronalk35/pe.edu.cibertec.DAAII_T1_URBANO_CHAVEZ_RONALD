package pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.service.UsuarioService;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.bd.CambiarContrasenaRequest;

@RestController
public class CambiarContrasenaController {

    @Autowired
    private UsuarioService usuarioService;

    @PutMapping("/seguridad/cambiar-contrasena")
    public ResponseEntity<String> cambiarContrasena(@RequestBody CambiarContrasenaRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        try {
            usuarioService.cambiarContrasena(username, request.getNuevaContrasena());
            return ResponseEntity.ok("Contraseña cambiada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cambiar la contraseña. Inténtalo de nuevo más tarde.");
        }
    }
}