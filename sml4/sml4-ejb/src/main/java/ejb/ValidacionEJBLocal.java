/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Formulario;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sebastian
 */
@Local
public interface ValidacionEJBLocal {

    boolean compareFechas(Date fechaT, Date fechaFormulario);

    boolean val(String rut);

    boolean soloCaracteres(String palabra);

    boolean checkRucOrRit(String rucOrRit);

    boolean esNumero(String numero);

    List<Formulario> findByNParteRR(String input, String aBuscar);

    boolean validarEmail(String email);

    String verificarUsuario(String user, String pass);
    
    boolean validarCuentaUsuario(String cuentaUsuario);
    
    boolean validarPassUsuario(String passUsuario);
}
