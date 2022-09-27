package com.SoftFactory.proyecto.cotroller;

import com.SoftFactory.proyecto.entidades.Empleado;
import com.SoftFactory.proyecto.entidades.Empresa;
import com.SoftFactory.proyecto.servicios.EmpleadoServi;
import com.SoftFactory.proyecto.servicios.EmpresaServi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class Controlador {
    @Autowired
    EmpresaServi empresaServi;
    EmpleadoServi empleadoServi;
    @GetMapping ({"/","/VerListaEmpresas"})
    public String viewEmpresas(Model model){
        List<Empresa> listaEmpresas=empresaServi.getAllEmpresa();
        model.addAttribute("factlist",listaEmpresas);
        return "verListaEmpresas"; //llamamos al HTML
    }
    @GetMapping("/AgregarEmpresas")
    public String newEmpresa(Model model){
        Empresa empr= new Empresa();
        model.addAttribute("empr", empr);
        return "agregarEmpresa";
    }

    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa empr, RedirectAttributes redirectAttributes){
        if(empresaServi.saveOrUpdateEmpresa(empr)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/VerListaEmpresas";
        }

        return "redirect:/AgregarEmpresas";
    }

    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empresa empr=empresaServi.getEmpresaById(id);
        //Creamos un atributo para el modelo, que se llame igualmente emp y es el que ira al html para llenar o alimentar campos
        model.addAttribute("empr", empr);
        model.addAttribute("mensaje", mensaje);
        return "editarEmpresa";
    }


    @PostMapping("/ActualizarEmpresa")
    public String updateEmpresa(@ModelAttribute("empr") Empresa empr, RedirectAttributes redirectAttributes){
        if(empresaServi.saveOrUpdateEmpresa(empr)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/VerListaEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarEmpresa/"+empr.getId();

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

    @GetMapping("/VerListaEmpleados")
    public String verEmpresas(Model model, @ModelAttribute("mensaje") String mensaje) {
        List<Empleado> listaEmpleados = empleadoServi.getAllEmpleados();
        model.addAttribute("listEmpl", listaEmpleados);
        model.addAttribute("mensaje", mensaje);
        return "verListaEmpleados"; //llamamos al HTML
    }

    @GetMapping("/AgregarEmpleado")
    public String newEmpleado(Model model, @ModelAttribute ("mensaje") String mensaje) {
        Empleado empl = new Empleado();
        model.addAttribute("empl", empl);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listEmpresas = empresaServi.getAllEmpresa();
        model.addAttribute("empList", listEmpresas);
        return "agregarEmpleado";
    }

    @PostMapping("/GuardarEmpleado")
    public String guardarEmpleado(Empleado empl, RedirectAttributes redirectAttributes) {
        if(empleadoServi.saveOrUpdateEmpleado(empl)) {
            redirectAttributes.addFlashAttribute("mensaje", "guaradado");
            return "redirect:/VerListaEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "errorGuardar" );
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
    public String actualizarEmpresa(@ModelAttribute("empl") Empleado empl, RedirectAttributes redirectAttributes){
        if(empleadoServi.saveOrUpdateEmpleado(empl)){
            redirectAttributes.addFlashAttribute("mensaje","actualizado");
            return "redirect:/VerListaEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje","Error al actualizar la empresa");
        return "redirect:/EditarEmpleado/" + empl.getId();
    }


    @GetMapping("/EliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if(empleadoServi.deleteEmpleado(id)){
            redirectAttributes.addFlashAttribute("mensaje","eliminado");
            return "redirect:/VerListaEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje","errorDelete");
        return "redirect:/VerListaEmpleados";
    }

    //Filtrar empleados por empresa
    @GetMapping("/Empresa/{id}/Empleados")
    public String verEmpleadosByEmpresa(@PathVariable("id") Integer id, Model model){
        List<Empleado> listaEmpleados = empleadoServi.empleadosByEmpresa(id);
        model.addAttribute("listEmpl", listaEmpleados);

        return "verListaEmpleados";
    }

}
