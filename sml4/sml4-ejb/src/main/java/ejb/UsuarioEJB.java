/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Area;
import entity.Cargo;
import entity.TipoUsuario;
import entity.Usuario;
import facade.TipoUsuarioFacade;
import facade.UsuarioFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Aracelly
 */
@Stateless
public class UsuarioEJB implements UsuarioEJBLocal {

    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    @EJB
    private TipoUsuarioFacade tipoUsuarioFacade;

    static final Logger logger = Logger.getLogger(UsuarioEJB.class.getName());

    //Función para verificar la existencia del usuario en el sistema
    @Override
    public String verificarUsuario(String user, String pass) {

        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "Función verificarUsuario", user);
        //Buscamos al usuario segun su cuenta usuario
        Usuario foundUser = usuarioFacade.findByCuentaUsuario(user);
        String direccion = "";
        //Si lo encuentro verifico si la contraseña es igual a la que se ingreso
        if (foundUser != null) {
            if (foundUser.getPassUsuario().equals(pass)) {
                //Redirecciono según el cargo a su respectiva vista
                if (foundUser.getCargoidCargo().getNombreCargo().equals("Perito")) {
                    direccion = "/perito/peritoFormulario.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Chofer")) {
                    direccion = "/chofer/choferFormulario.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Digitador")) {
                    direccion = "/digitador/digitadorFormularioHU11.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Tecnico")) {
                    direccion = "/tecnico/buscadorTecnico.xhtml?faces-redirect=true";
                } else if (foundUser.getCargoidCargo().getNombreCargo().equals("Jefe de area")){
                    direccion = "/jefeArea/buscadorJefeArea.xhtml?faces-redirect=true";
                }
            }
        }
        logger.exiting(this.getClass().getName(), "Función verificarUsuario", direccion);
        return direccion;
    }

    @Override
    public Usuario findUsuarioSesionByCuenta(String cuentaUsuario) {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "findUsuarioSesionByCuenta", cuentaUsuario);
        Usuario foundUser = usuarioFacade.findByCuentaUsuario(cuentaUsuario);
        if (foundUser != null) {
            logger.exiting(this.getClass().getName(), "findUsuarioSesionByCuenta", foundUser.toString());
            return foundUser;
        } else {
            logger.exiting(this.getClass().getName(), "findUsuarioSesionByCuenta", "Error con usuario");
            return null;
        }
    }
    
    
    //Función que verifica el rut, entrega true solo con el siguiente formato (18486956k) sin puntos ni guión.
    private boolean val(String rut) {

        int contadorPuntos = 0;
        int contadorGuion = 0;

        int largoR = rut.length();

        //Verifico que no tenga puntos y que tenga 1 solo guion
        for (int i = 0; i < largoR; i++) {
            if (rut.charAt(i) == 46) {
                contadorPuntos++;
            }
            if (rut.charAt(i) == 45) {
                contadorGuion++;
            }

        }

        if (contadorPuntos > 0 || contadorGuion > 0) {
            return false;
        }

        try {
            rut = rut.toUpperCase();
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                return true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return false;
    }

    private boolean soloCaracteres(String palabra) {

        Pattern patron = Pattern.compile("[^A-Za-z ]");
        Matcher encaja = patron.matcher(palabra);

        if (!encaja.find()) {
            System.out.println("solo tiene letras y espacio!");
            return true;
        } else {
            System.out.println("tiene otra cosa");
            return false;
        }

    }
    
    //minimo 8 caracteres
    private boolean validarCuentaUsuario(String cuentaUsuario){
    
        int largo = cuentaUsuario.length();
        if(largo < 8){
            return false;
        }
        return true;
    }

     private boolean validarPassUsuario(String passUsuario){
    
        int largo = passUsuario.length();
        if(largo < 8){
            return false;
        }
        return true;
    }
    /*
     OJO CON LA UNIDAD HAY QUE ACTUALIZAR LA UNIDAD
     A ESTE EJB HAY QUE AGREGAR LAS FUNCIONES VALIDAR CUENTA,PASS, EL EMAIL, RUT Y SOLO CARACTERES
     ADEMAS CREE UNA QUERY EN EL FACADE DE USUARIO BUSCANDO POR EL EMAIL
     DISCUTIR EL TEMA DE LA VERIFICACION DE LA PASS Y CUENTA DE USUARIO
     */
    @Override
    public String crearUsuario(String nombreUsuario, String apellidoUsuario, String rut, String pass, String mail, String cuentaUsuario, String estado, Cargo cargo, Area area) {

        //Verifico el rut
        if (!val(rut)) {
            return "Rut inválido";
        }

        //Verifico si existe ese rut en la BD
        if (usuarioFacade.findByRUN(rut) != null) {
            return "Rut existente";
        }

        //Verifico el nombreUsuario
        if (!soloCaracteres(nombreUsuario)) {
            return "Nombre ingresado incorrecto";
        }
        //Verifico el apellido
        if (!soloCaracteres(apellidoUsuario)) {
            return "Apellido ingresado incorrecto";
        }

        //Tengo que verificar si el email existe
        //Verifico el email
        if (!validarEmail(mail) || usuarioFacade.findByEmail(mail) != null) {
            return "Email inválido";
        }
        
        
        //Tengo que verificar si la cuenta de usuario existe
        //Validar cuenta de usuario
        if(!validarCuentaUsuario(cuentaUsuario) || usuarioFacade.findByCuentaUsuario(cuentaUsuario) != null){
            return "Cuenta de usuario inválida";
        }
        
        if(!validarPassUsuario(pass)){
            return "Contraseña inválida";
        }
        
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(nombreUsuario);
        nuevoUsuario.setApellidoUsuario(apellidoUsuario);
        nuevoUsuario.setRutUsuario(rut);
        nuevoUsuario.setPassUsuario(pass);
        nuevoUsuario.setMailUsuario(mail);
        nuevoUsuario.setCuentaUsuario(cuentaUsuario);
        if(estado.equals("Activo")){
             nuevoUsuario.setEstadoUsuario(Boolean.TRUE);
        }else{
            nuevoUsuario.setEstadoUsuario(Boolean.FALSE);
        }
        nuevoUsuario.setCargoidCargo(cargo);
        nuevoUsuario.setAreaidArea(area);
        
        TipoUsuario tu = new TipoUsuario();
        tu = tipoUsuarioFacade.findByTipo("sml");
        if(tu == null){
            return "Tipo de usuario no existe";
        }
        nuevoUsuario.setTipoUsuarioidTipoUsuario(tu);
       

        return "Exito";
    }

    //Funcion para validar el email
    //Retorna true en el caso que sea valido
    private boolean validarEmail(String email) {
        String patronEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(patronEmail);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
    
    //OJO ARREGLAR EL INICIO DE SESION SI EL USUARIO ESTA INACTIVO NO PUEDE INGRESAR EN SU CUENTA OJOJOJOJOJOJOJOJO !!!!!!!!!!!!
    @Override
    public boolean edicionEstadoUsuario(String rut, String estado){
    
        Usuario usuario = new Usuario();
        usuario = usuarioFacade.findByRUN(rut);
        if(usuario == null){
            return false;
        }
        if(estado.equals("Activo")){
            usuario.setEstadoUsuario(Boolean.TRUE);
        }
        else{
            usuario.setEstadoUsuario(Boolean.FALSE);
        }
        
        usuarioFacade.edit(usuario);
        return true;
    }
    

}
