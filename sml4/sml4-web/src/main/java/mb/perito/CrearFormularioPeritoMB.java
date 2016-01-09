/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.perito;
 
import ejb.FormularioEJBLocal;
import ejb.UsuarioEJBLocal;
import entity.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
 
/**
 *
 * @author sebastian
 */
@Named(value = "crearFormularioPeritoMB")
@RequestScoped
@ManagedBean
public class CrearFormularioPeritoMB {
 
    @EJB
    private UsuarioEJBLocal usuarioEJB;
    @EJB
    private FormularioEJBLocal formularioEJB;
 
    static final Logger logger = Logger.getLogger(CrearFormularioPeritoMB.class.getName());
 
    //Guardamos el usuario que inicia sesion
    private Usuario usuarioSesion;
 
    //Atributos del formulario
    private String ruc;
    private String rit;
    private int nue;
    private String cargo;
    private String delito;
    private String direccionSS;
    private String lugar;
    private String unidadPolicial;
    private String levantadaPor;
    private String rut;
    private Date fecha;
    private String observacion;
    private String descripcion;
    private int parte;
    
    private String motivo;
 
    //Guardamos la cuenta del usuario que entrego la vista del login
    private String usuarioSis;
 
    //Captura al usuario proveniente del inicio de sesión
    private HttpServletRequest httpServletRequest1;
    private FacesContext facesContext1;
 
    //Envio del nue
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
 
    private String evidencias;
    private String codTipoEvidencia;
    private String depa;
    private List<String> listEvidencias = new ArrayList<>();
    private List<String> listEvidencias2 = new ArrayList<>();
    private List<String> listEvidencias3 = new ArrayList<>();
    private List<String> listEvidencias4 = new ArrayList<>();
    private List<String> listEvidencias5 = new ArrayList<>();
    private List<String> listEvidenciasx = new ArrayList<>();
 
    public void iniciarListas() {
 
        listEvidencias.add("Contenido bucal");
        listEvidencias.add("Contenido vaginal");
        listEvidencias.add("Contenido rectal");
        listEvidencias.add("Lecho ungeal");
        listEvidencias.add("Secreciones");
        listEvidencias.add("Sangre");
        listEvidencias.add("Orina");
        listEvidencias.add("Tejido cerebro");
        listEvidencias.add("Tejido corazón");
        listEvidencias.add("Tejido pulmón");
        listEvidencias.add("Tejido hígado");
        listEvidencias.add("Tejido baso");
        listEvidencias.add("Tejido diafragma");
        listEvidencias.add("Tejido intestino");
        listEvidencias.add("Tejido piel");
        listEvidencias.add("Tejido otros");
        listEvidencias.add("Otros");
 
        listEvidencias2.add("Vestido");
        listEvidencias2.add("Blusa");
        listEvidencias2.add("Camisa");
        listEvidencias2.add("Pantalón");
        listEvidencias2.add("Polera");
        listEvidencias2.add("Chaqueta");
        listEvidencias2.add("Chaleco");
        listEvidencias2.add("Calzado");
        listEvidencias2.add("Otros");
 
        listEvidencias3.add("Protector");
        listEvidencias3.add("Toalla higiénica");
        listEvidencias3.add("Otros");
 
        listEvidencias4.add("Arma blanca");
        listEvidencias4.add("Cuchillo");
        listEvidencias4.add("Sable");
        listEvidencias4.add("Bala");
        listEvidencias4.add("Otros");
       
        listEvidencias5.add("Otros");
       
        listEvidenciasx.add("Seleccione");
        listEvidenciasx.add("Contenido bucal");
        listEvidenciasx.add("Contenido vaginal");
        listEvidenciasx.add("Contenido rectal");
        listEvidenciasx.add("Lecho ungeal");
        listEvidenciasx.add("Secreciones");
        listEvidenciasx.add("Sangre");
        listEvidenciasx.add("Orina");
        listEvidenciasx.add("Tejido cerebro");
        listEvidenciasx.add("Tejido corazón");
        listEvidenciasx.add("Tejido pulmón");
        listEvidenciasx.add("Tejido hígado");
        listEvidenciasx.add("Tejido baso");
        listEvidenciasx.add("Tejido diafragma");
        listEvidenciasx.add("Tejido intestino");
        listEvidenciasx.add("Tejido piel");
        listEvidenciasx.add("Tejido otros");
        listEvidenciasx.add("Vestido");
        listEvidenciasx.add("Blusa");
        listEvidenciasx.add("Camisa");
        listEvidenciasx.add("Pantalón");
        listEvidenciasx.add("Polera");
        listEvidenciasx.add("Chaqueta");
        listEvidenciasx.add("Chaleco");
        listEvidenciasx.add("Calzado");
        listEvidenciasx.add("Protector");
        listEvidenciasx.add("Toalla higiénica");
        listEvidenciasx.add("Arma blanca");
        listEvidenciasx.add("Cuchillo");
        listEvidenciasx.add("Sable");
        listEvidenciasx.add("Bala");
        listEvidenciasx.add("Otros");
       
    }
 
    public CrearFormularioPeritoMB() {        
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "CrearFormularioPeritoMB");
        this.usuarioSesion = new Usuario();
        this.facesContext1 = FacesContext.getCurrentInstance();
        this.httpServletRequest1 = (HttpServletRequest) facesContext1.getExternalContext().getRequest();
        this.facesContext = FacesContext.getCurrentInstance();
        this.httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        if (httpServletRequest1.getSession().getAttribute("cuentaUsuario") != null) {
            this.usuarioSis = (String) httpServletRequest1.getSession().getAttribute("cuentaUsuario");
            logger.log(Level.FINEST, "Usuario recibido {0}", this.usuarioSis);
        }
        iniciarListas();
        logger.exiting(this.getClass().getName(), "CrearFormularioPeritoMB");
    }
 
    @PostConstruct
    public void cargarDatos() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "cargarDatosPerito");
        this.usuarioSesion = (Usuario) usuarioEJB.findUsuarioSesionByCuenta(usuarioSis);
 
        this.cargo = this.usuarioSesion.getCargoidCargo().getNombreCargo();
        this.levantadaPor = this.usuarioSesion.getNombreUsuario();
        this.rut = this.usuarioSesion.getRutUsuario();
 
        GregorianCalendar c = new GregorianCalendar();
        this.fecha = c.getTime();
 
        logger.exiting(this.getClass().getName(), "cargarDatosPerito");
    }
 
    public String iniciarFormulario() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "iniciarFormularioPerito");
        String resultado = formularioEJB.crearFormulario(codTipoEvidencia, evidencias, motivo, ruc, rit, nue, parte, cargo, delito, direccionSS, lugar, unidadPolicial, levantadaPor, rut, fecha, observacion, descripcion, usuarioSesion);
 
        //Enviando nue
        httpServletRequest.getSession().setAttribute("nueF", this.nue);
        //Enviando usuario
        httpServletRequest1.getSession().setAttribute("cuentaUsuario", this.usuarioSis);
 
        if (resultado.equals("Exito")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, resultado, "Datos exitosos"));
            logger.exiting(this.getClass().getName(), "iniciarFormularioPerito", "formularioCreadoPerito");
            return "formularioCreadoPerito?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado, "Datos no válidos"));
        logger.exiting(this.getClass().getName(), "iniciarFormularioPerito", "");
        return "";
    }
 
    public String salir() {
        logger.setLevel(Level.ALL);
        logger.entering(this.getClass().getName(), "salirPerito");
        logger.log(Level.FINEST, "usuario saliente {0}", this.usuarioSesion.getNombreUsuario());
        httpServletRequest1.removeAttribute("cuentaUsuario");
        logger.exiting(this.getClass().getName(), "salirPerito", "/indexListo");
        return "/indexListo?faces-redirect=true";
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
 
 
    public String getRuc() {
        return ruc;
    }
 
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
 
    public String getRit() {
        return rit;
    }
 
    public void setRit(String rit) {
        this.rit = rit;
    }
 
    public int getNue() {
        return nue;
    }
 
    public void setNue(int nue) {
        this.nue = nue;
    }
 
    public String getCargo() {
        return cargo;
    }
 
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
 
    public String getDelito() {
        return delito;
    }
 
    public void setDelito(String delito) {
        this.delito = delito;
    }
 
    public String getDireccionSS() {
        return direccionSS;
    }
 
    public void setDireccionSS(String direccionSS) {
        this.direccionSS = direccionSS;
    }
 
    public String getLugar() {
        return lugar;
    }
 
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
 
    public String getUnidadPolicial() {
        return unidadPolicial;
    }
 
    public void setUnidadPolicial(String unidadPolicial) {
        this.unidadPolicial = unidadPolicial;
    }
 
    public String getLevantadaPor() {
        return levantadaPor;
    }
 
    public void setLevantadaPor(String levantadaPor) {
        this.levantadaPor = levantadaPor;
    }
 
    public String getRut() {
        return rut;
    }
 
    public void setRut(String rut) {
        this.rut = rut;
    }
 
    public Date getFecha() {
        return fecha;
    }
 
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
 
    public String getObservacion() {
        return observacion;
    }
 
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
 
    public String getDescripcion() {
        return descripcion;
    }
 
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
 
    public String getUsuarioSis() {
        return usuarioSis;
    }
 
    public void setUsuarioSis(String usuarioSis) {
        this.usuarioSis = usuarioSis;
    }
 
    public int getParte() {
        return parte;
    }
 
    public void setParte(int parte) {
        this.parte = parte;
    }
 
    public List<String> getListEvidencias() {
        return listEvidencias;
    }
 
    public void setListEvidencias(List<String> listEvidencias) {
        this.listEvidencias = listEvidencias;
    }
 
    public String getEvidencias() {
        return evidencias;
    }
 
    public void setEvidencias(String evidencias) {
        this.evidencias = evidencias;
    }
 
    public String getCodTipoEvidencia() {
        return codTipoEvidencia;
    }
 
    public void setCodTipoEvidencia(String codTipoEvidencia) {
        this.codTipoEvidencia = codTipoEvidencia;
    }
 
    public String getDepa() {
        return depa;
    }
 
    public void setDepa(String depa) {
        this.depa = depa;
    }
 
    public List<String> getListEvidenciasx() {
        return listEvidenciasx;
    }
 
    public void setListEvidenciasx(List<String> listEvidenciasx) {
        this.listEvidenciasx = listEvidenciasx;
    }
 
    public List<String> cargarEvidencias(final AjaxBehaviorEvent event) {
        logger.info("selecciono: " + codTipoEvidencia); 
        switch (codTipoEvidencia) {
            case "1":
                //biologica clinica
                listEvidenciasx = listEvidencias;
                return listEvidenciasx;
            case "6":
                //biologica tanatologia
                listEvidenciasx = listEvidencias;
                return listEvidenciasx;
            case "2":
                // vestuario clinica
                listEvidenciasx = listEvidencias2;
                return listEvidenciasx;
            case "7":
                // vestuario tanatologia
                listEvidenciasx = listEvidencias2;
                return listEvidenciasx;
            //vestuario
            case "3":
                //artefactos clinica
                listEvidenciasx = listEvidencias3;
                return listEvidenciasx;
            case "8":
                //artefactos tanatologia
                listEvidenciasx = listEvidencias3;
                return listEvidenciasx;
            case "5":
                // balistica tanatologia
                listEvidenciasx = listEvidencias4;
                return listEvidenciasx;
            case "4":
                //otros clinica
                listEvidenciasx = listEvidencias5;
                return listEvidenciasx;
            case "9":
                //otros tanatologia
                listEvidenciasx = listEvidencias5;
                return listEvidenciasx;
 
        }
        return listEvidenciasx;
    }
}
