package com.SoftFactory.proyecto.cotroller;

import com.SoftFactory.proyecto.entidades.Empleado;
import com.SoftFactory.proyecto.entidades.Empresa;
import com.SoftFactory.proyecto.entidades.MovimientoDeDinero;
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
        EmpleadoServi empleadoServi;

        MovimientoServi movimientoServi;
        @GetMapping ({"/","/VerListaEmpresas"})
        public String viewEmpresas(Model model,@ModelAttribute("mensaje")String mensaje){
            List<Empresa> listaEmpresas=empresaServi.getAllEmpresa();
            model.addAttribute("factlist",listaEmpresas);
            model.addAttribute("mensaje", mensaje);
            return "verListaEmpresas"; //llamamos al HTML
        }
        @GetMapping("/AgregarEmpresas")
        public String newEmpresa(Model model, @ModelAttribute("mensaje")String mensaje){
            Empresa empr= new Empresa();
            model.addAttribute("empr", empr);
            model.addAttribute("mensaje", mensaje);
            return "agregarEmpresa";
        }

        @PostMapping("/GuardarEmpresa")
        public String guardarEmpresa(Empresa empr, RedirectAttributes redirectAttributes){
            if(empresaServi.saveOrUpdateEmpresa(empr)==true){
                redirectAttributes.addFlashAttribute("mensaje","saveOK ");
                return "redirect:/VerListaEmpresas";
            }
            redirectAttributes.addFlashAttribute("mensaje","error");
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
        public String eliminarEmpresa(@PathVariable Integer id, RedirectAttributes redirectAttributes){
            if (empresaServi.deleEmpresa(id)==true){
                redirectAttributes.addFlashAttribute("mensaje","deleteOK");
                return "redirect:/VerListaEmpresas";
            }
            redirectAttributes.addFlashAttribute("mensaje", "deleteError");
            return "redirect:/VerListaEmpresas";
        }

    //EMPLEADOS
    @GetMapping("/VerListaEmpleados")
    public String verEmpresas(Model model, @ModelAttribute("mensaje") String mensaje) {
        List<Empleado> listaEmpleados = empleadoServi.getAllEmpleados();
        model.addAttribute("listEmpl", listaEmpleados);
        model.addAttribute("mensaje", mensaje);
        return "verListaEmpleados"; //llamamos al HTML
    }

        @GetMapping("/AgregarEmpleado")
        public String newmpleado(Model model,@ModelAttribute("mensaje") String mensaje) {
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


    //MOVIMIENTOS

        @GetMapping("/VerMovimientos")
        public String verMovimientos(Model model, @ModelAttribute("mensaje") String mensaje) {
            List<MovimientoDeDinero> listaMovimientos = movimientoServi.getAllMovimientos();
            model.addAttribute("listMov", listaMovimientos);
            model.addAttribute("mensaje", mensaje);
            return "verMovimientos"; //llamamos al HTML
        }


        @GetMapping("/AgregarMovimiento")
        public String newEmpleado(Model model, @ModelAttribute ("mensaje") String mensaje) {
            MovimientoDeDinero movi = new MovimientoDeDinero();
            model.addAttribute("movi", movi);
            model.addAttribute("mensaje", mensaje);
            List<Empleado> empleadoList = empleadoServi.getAllEmpleados();
            model.addAttribute("empleList", empleadoList);
            return "agregarMovimiento";
        }



        @PostMapping("/GuardarMovimiento")
        public String guardarMovimiento(MovimientoDeDinero mov, RedirectAttributes redirectAttributes) {
            if(movimientoServi.saveOrUpdateMovimiento(mov)) {
                redirectAttributes.addFlashAttribute("mensaje", "guaradado");
                return "redirect:/VerMovimientos";
            }
            redirectAttributes.addFlashAttribute("mensaje", "errorGuardar" );
            return "redirect:/AgregarMovimiento";
        }


        @GetMapping ("/EditarMovimiento/{id}")
        public String editarMovimiento (Model model, @PathVariable Integer id, @ModelAttribute ("mensaje") String mensaje) {
            MovimientoDeDinero movi = movimientoServi.getMovimientoById(id);
            model.addAttribute ("movi" , movi );
            model.addAttribute("mensaje", mensaje);
            //Para ver las empresas en el HTML
            List<Empleado> empleadoList = empleadoServi.getAllEmpleados();
            model.addAttribute("empleList", empleadoList);
            return "editarMovimiento" ;
        }


        @PostMapping("/ActualizarMovimiento")
        public String actualizarMovimiento(@ModelAttribute("empl") MovimientoDeDinero movi, RedirectAttributes redirectAttributes){
            if(movimientoServi.saveOrUpdateMovimiento(movi)){
                redirectAttributes.addFlashAttribute("mensaje","actualizado");
                return "redirect:/VerMovimientos";
            }
            redirectAttributes.addFlashAttribute("mensaje","Error al actualizar la empresa");
            return "redirect:/EditarMovimiento/" + movi.getId();
        }


        @GetMapping("/EliminarMovimiento/{id}")
        public String eliminarMovimiento(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
            if(movimientoServi.deleteMovimiento(id)){
                redirectAttributes.addFlashAttribute("mensaje","eliminado");
                return "redirect:/VerMovimientos";
            }
            redirectAttributes.addFlashAttribute("mensaje","errorDelete");
            return "redirect:/VerMovimientos";
        }


        @GetMapping("/Empleado/{id}/Movimientos") //Filtro de movimientos por empleados
        public String movimientosPorEmpleado(@PathVariable("id")Integer id, Model model){
            List<MovimientoDeDinero> movlist = movimientoServi.obtenerPorEmpleado(id);
            model.addAttribute("listMov",movlist);
            Long sumaMonto=movimientoServi.MontosPorEmpleado(id);
            model.addAttribute("SumaMontos",sumaMonto);
            return "verMovimientos"; //Llamamos al HTML
        }


        @GetMapping("/Empresa/{id}/Movimientos") //Filtro de movimientos por empresa
        public String movimientosPorEmpresa(@PathVariable("id")Integer id, Model model){
            List<MovimientoDeDinero> movlist = movimientoServi.obtenerPorEmpresa(id);
            model.addAttribute("listMov",movlist);
            Long sumaMonto=movimientoServi.MontosPorEmpresa(id);
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
