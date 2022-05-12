package com.platzi.market.web.security.filter;

import com.platzi.market.domain.service.PlatziUserDetailService;
import com.platzi.market.web.security.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
//Para que se ejecute cada vez que se hace una petición
public class JwtFilterRequest extends OncePerRequestFilter {

    private JWTUtil jwtUtil;

    private PlatziUserDetailService platziUserDetailService;
    public JwtFilterRequest(JWTUtil jwtUtil,PlatziUserDetailService platziUserDetailService) {
        this.jwtUtil = jwtUtil;
        this.platziUserDetailService=platziUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer")){
            //Descontamos el Bearer
            String jwt = authorizationHeader.substring(7);
            //Verificar el usuario del JWT
            String username = jwtUtil.getUsername(jwt);
            //Verificamos que no hay autorizacion para el usuario
            if(username!=null && SecurityContextHolder.getContext().getAuthentication() ==null){
                //Verificamos el usuario, si existe en el sistema de autenticacion
                UserDetails userDetails = platziUserDetailService.loadUserByUsername(username);
                if(jwtUtil.validateToken(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
