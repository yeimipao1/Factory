package com.SoftFactory.proyecto.cotroller;

import com.SoftFactory.proyecto.entidades.Empleado;
import com.SoftFactory.proyecto.entidades.Empresa;
import com.SoftFactory.proyecto.entidades.MovimientoDeDinero;
import com.SoftFactory.proyecto.repositorios.MovimientosRepo;
import com.SoftFactory.proyecto.servicios.EmpleadoServi;
import com.SoftFactory.proyecto.servicios.EmpresaServi;
import com.SoftFactory.proyecto.servicios.MovimientoServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class Controlador {
    @Autowired
    EmpresaServi empresaServi;
    @Autowired
    EmpleadoServi empleadoServi;
    @Autowired
    MovimientoServi movimientosServi;

    @Autowired
    MovimientosRepo movimientosRepo;
    @GetMapping ({"/","/VerListaEmpresas"})
    public String viewEmpresas(Model model){
        List<Empresa> listaEmpresas=empresaServi.getAllEmpresa();
        model.addAttribute("factlist",listaEmpresas);
        return "verListaEmpresas"; //llamamos al HTML
    }
    @GetMapping("/AgregarEmpresas")
    public String newEmpresa(Model model){
        Empresa emp= new Empresa();
        model.addAttribute("emp", emp);
        return "agregarEmpresa";
    }

    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa emp, RedirectAttributes redirectAttributes){
        if(empresaServi.saveOrUpdateEmpresa(emp)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerListaEmpresas";
        }

        return "redirect:/AgregarEmpresas";
    }

    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empresa emp=empresaServi.getEmpresaById(id);
        //Creamos un atributo para el modelo, que se llame igualmente emp y es el que ira al html para llenar o alimentar campos
        model.addAttribute("emp", emp);
        model.addAttribute("mensaje", mensaje);
        return "editarEmpresa";
    }


    @PostMapping("/ActualizarEmpresa")
    public String updateEmpresa(@ModelAttribute("emp") Empresa emp, RedirectAttributes redirectAttributes){
        if(empresaServi.saveOrUpdateEmpresa(emp)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/VerListaEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarEmpresa/"+emp.getId();

    }

    @GetMapping("/EliminarEmpresa/{id}")
    public String eliminarEmpresa(@PathVariable Integer id){
        try {
            empresaServi.deleEmpresa(id);
        }catch(Exception e){
                return "redirect:/VerListaEmpresas";
            }
                return "redirect:/VerListaEmpresas";

        }
//empleado

    @GetMapping("/VerListaEmpleados")
    public String verEmpresas(Model model, @ModelAttribute("mensaje") String mensaje) {
        List<Empleado> listaEmpleados = empleadoServi.getAllEmpleados();
        model.addAttribute("listEmpl", listaEmpleados);
        model.addAttribute("mensaje", mensaje);
        return "verListaEmpleados"; //llamamos al HTML
    }

    @GetMapping("/AgregarEmpleado")
    public String newEmpleado(Model model, @ModelAttribute("mensaje") String mensaje) {
        Empleado empl = new Empleado();
        model.addAttribute("empl", empl);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listEmpresas = empresaServi.getAllEmpresa();
        model.addAttribute("empList", listEmpresas);
        return "agregarEmpleado";
    }

    @PostMapping("/GuardarEmpleado")
    public String guardarEmpleado(Empleado empl, RedirectAttributes redirectAttributes){
        String passEncriptada=passwordEncoder().encode(empl.getPassword());
        empl.setPassword(passEncriptada);
        if(empleadoServi.saveOrUpdateEmpleado(empl)==true) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/VerListaEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError" );
        return "redirect:/AgregarEmpleados";
    }


    @GetMapping ("/EditarEmpleado/{id}")
    public String editarEmpleado (Model model, @PathVariable Integer id, @ModelAttribute ("mensaje") String mensaje) {
        Empleado empl = empleadoServi.getEmpleadoById(id);
        model.addAttribute ("empl" , empl );
        model.addAttribute("mensaje", mensaje);
        //Para ver las empresas en el HTML
        List<Empresa> listaEmpresas= empresaServi.getAllEmpresa();
        model.addAttribute("empList",listaEmpresas);
        return "editarEmpleado" ;
    }


    @PostMapping("/ActualizarEmpleado")
    public String updateEmpleado(@ModelAttribute("empl") Empleado empl, RedirectAttributes redirectAttributes) {
        Integer id = empl.getId();
        String Oldpass=empleadoServi.getEmpleadoById(id).getPassword(); /*get*/
        if (!empl.getPassword().equals(Oldpass)) {
            String passEncriptada=passwordEncoder().encode(empl.getPassword());
            empl.setPassword(passEncriptada);
        }
        if (empleadoServi.saveOrUpdateEmpleado(empl)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/VerListaEmpleados";
            }
            redirectAttributes.addFlashAttribute("mensaje", "updateError");
            return "redirect:/EditarEmpleado/" + empl.getId();
        }


    @GetMapping("/EliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if(empleadoServi.deleteEmpleado(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/VerListaEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje","deleteError");
        return "redirect:/VerListaEmpleados";
    }

    //Filtrar empleados por empresa
    @GetMapping("/Empresa/{id}/Empleados")
    public String verEmpleadosByEmpresa(@PathVariable("id") Integer id, Model model){
        List<Empleado> listaEmpleados = empleadoServi.obtenerPorEmpresa(id);;
        model.addAttribute("listEmpl", listaEmpleados);

        return "verListaEmpleados";
    }

    @GetMapping("/VerMovimientos")
    public String verMovimientos(Model model, @ModelAttribute("mensaje") String mensaje) {
        List<MovimientoDeDinero> listaMovimientos = movimientosServi.getAllMovimientos();
        model.addAttribute("listMov", listaMovimientos);
        model.addAttribute("mensaje", mensaje);
        return "verMovimientos"; //llamamos al HTML
    }


    @GetMapping("/AgregarMovimiento")
    public String nuevoEmpleado(Model model, @ModelAttribute ("mensaje") String mensaje) {
        MovimientoDeDinero movi = new MovimientoDeDinero();
        model.addAttribute("movi", movi);
        model.addAttribute("mensaje", mensaje);
        List<Empleado> empleadoList = empleadoServi.getAllEmpleados();
        model.addAttribute("empleList", empleadoList);
        return "agregarMovimiento";
    }



    @PostMapping("/GuardarMovimiento")
    public String guardarMovimiento(MovimientoDeDinero mov, RedirectAttributes redirectAttributes) {
        if(movimientosServi.saveOrUpdateMovimiento(mov)) {
            redirectAttributes.addFlashAttribute("mensaje", "guardado");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "errorGuardar" );
        return "redirect:/AgregarMovimiento";
    }


    @GetMapping ("/EditarMovimiento/{id}")
    public String editarMovimiento (Model model, @PathVariable Integer id, @ModelAttribute ("mensaje") String mensaje) {
        MovimientoDeDinero movi = movimientosServi.getMovimientoById(id);
        model.addAttribute ("movi" , movi );
        model.addAttribute("mensaje", mensaje);
        //Para ver las empresas en el HTML
        List<Empleado> empleadoList = empleadoServi.getAllEmpleados();
        model.addAttribute("empleList", empleadoList);
        return "editarMovimiento" ;
    }


    @PostMapping("/ActualizarMovimiento")
    public String actualizarMovimiento(@ModelAttribute("empl") MovimientoDeDinero movi, RedirectAttributes redirectAttributes){
        if(movimientosServi.saveOrUpdateMovimiento(movi)){
            redirectAttributes.addFlashAttribute("mensaje","actualizado");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje","Error al actualizar la empresa");
        return "redirect:/EditarMovimiento/" + movi.getId();
    }


    @GetMapping("/EliminarMovimiento/{id}")
    public String eliminarMovimiento(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if(movimientosServi.deleteMovimiento(id)){
            redirectAttributes.addFlashAttribute("mensaje","eliminado");
            return "redirect:/VerMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje","errorDelete");
        return "redirect:/VerMovimientos";
    }


    @GetMapping("/Empleado/{id}/Movimientos") //Filtro de movimientos por empleados
    public String movimientosPorEmpleado(@PathVariable("id")Integer id, Model model){
        List<MovimientoDeDinero> movlist = movimientosServi.obtenerPorEmpleado(id);
        model.addAttribute("listMov",movlist);
        Long sumaMonto=movimientosServi.MontosPorEmpleado(id);
        model.addAttribute("SumaMontos",sumaMonto);
        return "verMovimientos"; //Llamamos al HTML
    }


    @GetMapping("/Empresa/{id}/Movimientos") //Filtro de movimientos por empresa
    public String movimientosPorEmpresa(@PathVariable("id")Integer id, Model model){
        List<MovimientoDeDinero> movlist = movimientosServi.obtenerPorEmpresa(id);
        model.addAttribute("listMov",movlist);
        Long sumaMonto=movimientosServi.MontosPorEmpresa(id);
        model.addAttribute("SumaMontos",sumaMonto);
        return "verMovimientos"; //Llamamos al HTML
    }




    //Controlador que me lleva al template de No autorizado
    @RequestMapping(value="/Denegado")
    public String accesoDenegado(){
        return "accessDenied";
    }


    //Metodo para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
