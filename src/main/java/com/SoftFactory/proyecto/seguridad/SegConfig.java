
package com.SoftFactory.proyecto.seguridad;

import com.SoftFactory.proyecto.handler.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.activation.DataSource;

@Deprecated
@Configuration
@EnableWebSecurity
public class SegConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public DataSource dataSource;

    @Autowired
    CustomSuccessHandler customSuccessHandler;


    @Autowired
      public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
          auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                  .dataSource ((javax.sql.DataSource) dataSource)
                  .usersByUsernameQuery("select correo,password,estado from empleado where correo=?")
                  .authoritiesByUsernameQuery("select correo, rol from empleado where correo=?");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","VerListaEmpresas/**").hasRole("ADMIN")
                .antMatchers("/VerListaEmpleados/**").hasRole("ADMIN")
                .antMatchers("/Empresa/**").hasRole("ADMIN")
                .antMatchers("/Empleado/**").hasRole("ADMIN")
                .antMatchers("/VerMovimientos/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/AgregarMovimiento/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/EditarMovimiento/**").hasAnyRole("ADMIN","USER")
                .and().formLogin().successHandler(customSuccessHandler)
                .and().exceptionHandling().accessDeniedPage("/Denegado")
                .and().logout().permitAll();
    }


}
