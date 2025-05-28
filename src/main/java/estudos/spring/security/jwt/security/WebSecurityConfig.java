package estudos.spring.security.jwt.security;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer; // Importação para Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Nova anotação
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer; // Importação para HeadersConfigurer
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; // Nova importação para SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.h2.server.web.WebServlet;
// Importação para WebServlet do H2

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Substitui @EnableGlobalMethodSecurity
public class WebSecurityConfig {

    // Lista de URLs que não exigem autenticação (para Swagger e H2 Console)
    private static final String[] SWAGGER_WHITELIST = {
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**"
    };

    /**
     * Define o codificador de senhas a ser usado na aplicação.
     *
     * @return Uma instância de BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura a cadeia de filtros de segurança HTTP. Este método substitui o
     * antigo 'configure(HttpSecurity http)' do WebSecurityConfigurerAdapter.
     *
     * @param http O objeto HttpSecurity para configurar a segurança.
     * @return Uma instância de SecurityFilterChain.
     * @throws Exception Se ocorrer um erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(SWAGGER_WHITELIST).permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users").permitAll()
                    .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USERS", "MANAGERS")
                    .requestMatchers("/managers").hasAnyRole("MANAGERS")
                    .anyRequest().authenticated()
                );
        
        return http.build();
    }

    /**
     * Habilita o acesso ao console do H2 Database na web.
     *
     * @return Um ServletRegistrationBean para o WebServlet do H2.
     */
    /**
     * REMOVA ESTE MÉTODO INTEIRO: Foi necessário remover este metódo por que a
     * versão atual do spring entra em conflito com o
     * ServletRegistrationBean<WebServlet>
     * Que espera um WebServlet do H2, mas a versão atual do H2 não é compatível
     * com o ServletRegistrationBean.
     *
     * Se você precisar acessar o console do H2, certifique-se de que a
     * dependência do H2 está incluída no seu projeto e que a versão é
     * compatível.
     *
     * @return Um ServletRegistrationBean configurado para o WebServlet do H2.
     */
    /*
    @Bean
    public ServletRegistrationBean<WebServlet> h2servletRegistration() {
        ServletRegistrationBean<WebServlet> registrationBean = new ServletRegistrationBean<>(new WebServlet());
        registrationBean.addUrlMappings("/h2-console/*");
        return registrationBean;
    }
     */
}
