/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Tienda.service.impl;


import com.Tienda.db.IRolRepository;
import com.Tienda.db.IUserRepository;
import com.Tienda.entities.Rol;
import com.Tienda.entities.Usuario;
import com.Tienda.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 

@Service("userDetailsService")
public class UsuarioService implements IUsuarioService, UserDetailsService {
    @Autowired
    private IUserRepository usuarioDao;
     @Autowired
    private IRolRepository rolDao;
    @Autowired
    private HttpSession session;

 

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Busca el usuario por el username en la tabla
        Usuario usuario = usuarioDao.findByUsername(username);
        //Si no existe el usuario lanza una excepción
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        session.removeAttribute("usuarioImagen");
        session.setAttribute("usuarioImagen", usuario.getRutaImagen());
        //Si está acá es porque existe el usuario... sacamos los roles que tiene
        var roles = new ArrayList<GrantedAuthority>();
        for (Rol rol : usuario.getRoles()) {   //Se sacan los roles
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }
        //Se devuelve User (clase de userDetails)
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return (List<Usuario>) usuarioDao.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuario(Usuario usuario) {
        return usuarioDao.findById(usuario.getIdUsuario()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameYPassword(String username, String password) {
        return usuarioDao.findByUsernameAndPassword(username, password);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameOCorreo(String username, String correo) {
        return usuarioDao.findByUsernameOrCorreo(username, correo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeUsuarioPorUsernameOCorreo(String username, String correo) {
        return usuarioDao.existsByUsernameOrCorreo(username, correo);
    }

    @Override
    @Transactional
    public void save(Usuario usuario, boolean crearRolUser) {
        usuario=usuarioDao.save(usuario);
        if (crearRolUser) {//Si se está creando el usuario, se crea el rol por defecto "USER"
            Rol rol = new Rol();
            rol.setNombre("ROLE_USER");
            rol.setIdUsuario(usuario.getIdUsuario());
            rolDao.save(rol);
        }
    }

    @Override
    @Transactional
    public void delete(Usuario usuario) {
        usuarioDao.delete(usuario);
    }

}

