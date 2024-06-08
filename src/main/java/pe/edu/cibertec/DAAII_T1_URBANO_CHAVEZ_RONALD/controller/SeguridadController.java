package pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.bd.Usuario;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.dto.ResultadoDto;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.model.dto.UsuarioDto;
import pe.edu.cibertec.DAAII_T1_URBANO_CHAVEZ_RONALD.service.UsuarioService;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/seguridad")
public class SeguridadController {
    private UsuarioService usuarioService;
    @GetMapping("/usuario")
    public String frmMantUsuario(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("listaUsuarios", usuarioService.listarUsuario());
        model.addAttribute("username", user.getUsername());
        return "seguridad/formusuario";
    }





    @PostMapping("/usuario")
    @ResponseBody
    public ResultadoDto registrarUsuario(@RequestBody UsuarioDto usuarioDto) {
        String mensaje = "Usuario registrado correctamente";
        boolean respuesta = true;
        try {
            Usuario usuario = new Usuario();
            usuario.setNombres(usuarioDto.getNombres());
            usuario.setApellidos(usuarioDto.getApellidos());
            usuario.setNomusuario(usuarioDto.getNomusuario());
            usuario.setEmail(usuarioDto.getEmail());
            usuario.setPassword(usuarioDto.getPassword()); // Asegurarse de que la contraseña se pase aquí

            if (usuarioDto.getIdusuario() > 0) {
                usuario.setIdusuario(usuarioDto.getIdusuario());
                usuario.setActivo(usuarioDto.getActivo());
                usuarioService.actualizarUsuario(usuario);
            } else {
                usuarioService.guardarUsuario(usuario);
            }
        } catch (Exception ex) {
            mensaje = "Usuario no registrado, error en la BD";
            respuesta = false;
            System.err.println("Error al registrar el usuario: " + ex.getMessage());
            ex.printStackTrace();
        }
        return ResultadoDto.builder().mensaje(mensaje).respuesta(respuesta).build();
    }

    @GetMapping("/usuario/{id}")
    @ResponseBody
    public Usuario frmMantUsuario(@PathVariable("id") int id){
        return usuarioService.buscarUsuarioXIdUsuario(id);
    }
    @GetMapping("/usuario/lista")
    @ResponseBody
    public List<Usuario> listaUsuario(){
        return usuarioService.listarUsuario();
    }



}
