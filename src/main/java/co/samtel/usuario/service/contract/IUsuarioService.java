package co.samtel.usuario.service.contract;


import co.samtel.usuario.gen.type.UsuarioTypeInput;
import co.samtel.usuario.gen.type.UsuarioTypeResponse;

import java.util.List;

public interface IUsuarioService {
    UsuarioTypeResponse crearUsuario(UsuarioTypeInput usuarioTypeInput);
    UsuarioTypeResponse editarUsuario(Integer idtblUser, UsuarioTypeInput usuarioTypeInput);
    UsuarioTypeResponse buscarUsuario(Integer idtblUser);
    void eliminarUsuario(Integer idtblUser);
    List<UsuarioTypeResponse> listarTodosLosUsuario();
}
