package it.eugenio.gestioneDispositivi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.eugenio.gestioneDispositivi.dao.UtenteService;
import it.eugenio.gestioneDispositivi.models.Utente;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UtenteService serv;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()					
			.antMatchers("/")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic()
			.and()
			.formLogin()
			.successForwardUrl("/loginSuccess")
			.and()
			.logout()
			.and()
			.csrf()
			.disable();
	}
	
	
	//VIENE EFFETTUATO UN CONTROLLO SU TUTTI GLI UTENTI PRESENTI NEL DATABASE E SUI LORO RUOLI
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		List<Utente> authUsersObj = serv.getAll();
		
		for ( Utente u : authUsersObj) {
			
			auth.inMemoryAuthentication()
				.withUser( u.getUsername() )
				.password( passwordEncoder().encode( u.getPassword() ) )
				.roles(u.getRuolo().toString());
		}
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
