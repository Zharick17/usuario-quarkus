package co.samtel.usuario.service.impl;

import co.samtel.usuario.dao.UsuarioDao;
import co.samtel.usuario.entity.Usuario;
import co.samtel.usuario.gen.type.UsuarioTypeInput;
import co.samtel.usuario.gen.type.UsuarioTypeResponse;
import co.samtel.usuario.service.contract.IUsuarioService;
import co.samtel.usuario.utils.ApplicationException;
import co.samtel.usuario.utils.UsuarioMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

import static co.samtel.usuario.constant.Constant.ERROR_SERVICIO;
@ApplicationScoped
public class UsuarioServiceImpl implements IUsuarioService {
    private static final Logger LOG = LoggerFactory.getLogger(UsuarioServiceImpl.class);
    @Inject
    UsuarioMapper usuarioMapper;
    @Inject
    UsuarioDao usuarioDao;

    @Transactional
    public UsuarioTypeResponse crearUsuario(UsuarioTypeInput usuarioTypeInput) {
        LOG.info("Inicia crear usuario");
        UsuarioTypeResponse response = null;
        try {
            Usuario usuario = usuarioMapper.usuarioTypeToEntity(usuarioTypeInput);
            usuarioDao.persist(usuario);
            response = usuarioMapper.usuarioEntityToType(usuario);
            return response;
        }catch (ApplicationException e){
            LOG.error("Error al crear el usuario");
            throw new ApplicationException(ERROR_SERVICIO + e.getMessage());
        }
    }

    @Transactional
    public UsuarioTypeResponse buscarUsuario(Integer idtblUser){
        LOG.info("Inicia la parte del metodo buscarUsuario Impl");
        try {
            Usuario user = usuarioDao.findById(idtblUser.longValue());
            UsuarioTypeResponse response = usuarioMapper.usuarioEntityToType(user);
            LOG.info("Finaliza el metodo buscarUsuario Impl");
            return response;
        }catch (ApplicationException e){
            LOG.error("Se presento un error en el metodo buscarUsuario Impl"+ e.getMessage());
            throw new ApplicationException(ERROR_SERVICIO + e.getMessage());
        }
    }

    @Transactional
    public void eliminarUsuario(Integer idtblUser){
        LOG.info("Inicia el metodo eliminarUsuario Impl");
        try {
            Long id = Long.valueOf(idtblUser);
            usuarioDao.deleteById(id);
            LOG.info("Termina el metodo eliminarUsuario Impl");
        }catch(ApplicationException e){
            LOG.error("Se presento un error en el metodo eliminarUsuario Impl"+ e.getMessage());
            throw new ApplicationException(ERROR_SERVICIO + e.getMessage());
        }
    }

    @Transactional
    public UsuarioTypeResponse editarUsuario(Integer idtblUser, UsuarioTypeInput usuarioTypeInput) {
        LOG.info("Inicia el metodo editarUsuario Impl");
        try{
            Usuario usuario = usuarioDao.findById(idtblUser.longValue());
            Usuario usuarioCambio = usuarioMapper.usuarioTypeToEntity(usuarioTypeInput);
            usuario.setName(usuarioCambio.getName());
            usuario.setLastname(usuarioCambio.getLastname());
            usuario.setCreateat(usuarioCambio.getCreateat());
            UsuarioTypeResponse response = usuarioMapper.usuarioEntityToType(usuarioCambio);
            LOG.info("Finaliza el metodo editarUsuario Impl");
            return response;

        }catch(ApplicationException e){
            LOG.error("Se presento un error en el metodo editarUsuario Impl"+ e.getMessage());
            throw new ApplicationException(ERROR_SERVICIO + e.getMessage());
        }
    }

    @Transactional
    public List<UsuarioTypeResponse> listarTodosLosUsuario() {
        LOG.info("Inicia el metodo listarTodosLosUsuarios");
        try{
            PanacheQuery listQuery = usuarioDao.findAll();
            List<Usuario> usuario = listQuery.list();
            LOG.info("Finaliza el metodo listarTodosLosUsuarios Impl");
            return usuarioMapper.usuariosTypeListEntityToTypeResponse(usuario);
        }catch(ApplicationException e){
            LOG.error("Se presento un error al listar todos los usuario"+ e.getMessage());
            throw new ApplicationException(ERROR_SERVICIO + e.getMessage());
        }
    }

}