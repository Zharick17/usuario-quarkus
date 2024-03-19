package co.samtel.usuario.controller;

import jakarta.ws.rs.core.Response;
import co.samtel.usuario.gen.contract.V1UsuarioApi;
import co.samtel.usuario.gen.type.UsuarioTypeInput;
import co.samtel.usuario.gen.type.UsuarioTypeResponse;
import co.samtel.usuario.service.impl.UsuarioServiceImpl;
import co.samtel.usuario.utils.ApplicationException;
import jakarta.inject.Inject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;

public class UsuarioController implements V1UsuarioApi {
    private  static  final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);
    @Inject
    UsuarioServiceImpl usuarioServiceImpl;

    @Override
    public Response crearUsuario(UsuarioTypeInput usuarioTypeInput) {
        LOG.info("Inicia el metodo crearUsuario Controller");
        UsuarioTypeResponse response = null;
        try{
            response = usuarioServiceImpl.crearUsuario(usuarioTypeInput);
        }catch (ApplicationException e){
            LOG.error("Se presento un error en el metodo crearUsuario controller"+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        LOG.info("Finaliza el metodo crearUsuario Controller");
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response editarUsuario(Integer idtblUser, UsuarioTypeInput usuarioTypeInput) {
        LOG.info("Inicia el metodo editarUsuario Controller");
        UsuarioTypeResponse response = null;
        try{
            response = usuarioServiceImpl.editarUsuario(idtblUser, usuarioTypeInput);
        }catch (ApplicationException e){
            LOG.error("Se presento un error en el metodo buscarUsuario controller"+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        LOG.info("Finaliza el metodo editarUsuario Controller");
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response buscarUsuario(Integer idtblUser){
        LOG.info("Inicia el metodo buscarUsuario Controller");
        UsuarioTypeResponse response = null;
        try {
            LOG.info("Inicia listarUsuarioImpl");
            response = usuarioServiceImpl.buscarUsuario(idtblUser);
            LOG.info("Finaliza listarUsuarioImpl");
        } catch (ApplicationException e) {
            LOG.error("Se presento un error en el metodo buscarUsuario controller: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al buscar usuario: " + e.getMessage()).build();
        }
        LOG.info("Finaliza el metodo buscarUsuario Controller");
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response eliminarUsuario(Integer idtblUser) {
        LOG.info("Inicia el metodo eliminarUsuario Controller");
        UsuarioTypeResponse response = null;
        try{
            usuarioServiceImpl.eliminarUsuario(idtblUser);
        }catch (ApplicationException e){
            LOG.error("Se presento un error en el metodo eliminarUsuario Controller"+ e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        LOG.info("Finaliza el metodo eliminarUsuario Controller");
        return Response.status(Response.Status.NO_CONTENT).entity(response).build();
    }

    @Override
    public Response listarTodosLosUsuario() {
        LOG.info("Inicia el metodo listarTodosLosUsuario Controller");
        List<UsuarioTypeResponse> response = null;
        try {
            response = usuarioServiceImpl.listarTodosLosUsuario();
        } catch (ApplicationException e) {
            LOG.error("Se presento un error en el metodo listarTodosLosUsuario controller: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al listar todos los usuarios: " + e.getMessage()).build();
        }
        LOG.info("Finaliza el metodo listarTodosLosUsuario Controller");
        return Response.status(Response.Status.OK).entity(response).build();
    }
}

