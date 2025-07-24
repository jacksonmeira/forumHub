package com.forumhub.controller;

import com.forumhub.dto.LoginDTO;
import com.forumhub.dto.TokenDTO;
import com.forumhub.dto.RegisterDTO;
import com.forumhub.model.Perfil;
import com.forumhub.model.Usuario;
import com.forumhub.repository.PerfilRepository;
import com.forumhub.repository.UsuarioRepository;
import com.forumhub.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final PerfilRepository perfilRepository;

    public AuthController(AuthenticationManager authManager,
                          UsuarioRepository usuarioRepository,
                          TokenService tokenService,
                          PasswordEncoder passwordEncoder,
                          PerfilRepository perfilRepository) {
        this.authManager = authManager;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.perfilRepository = perfilRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dto) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
            var auth = authManager.authenticate(authToken);
            Usuario usuario = (Usuario) auth.getPrincipal();
            String token = tokenService.gerarToken(usuario);
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody @Valid RegisterDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        String senhaHash = passwordEncoder.encode(dto.getSenha());
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenha(senhaHash);


        Perfil perfilUser = perfilRepository.findByNome("ROLE_USER")
                .orElseGet(() -> perfilRepository.save(new Perfil("ROLE_USER")));

        novoUsuario.setPerfis(Set.of(perfilUser));
        usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}
