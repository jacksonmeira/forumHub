package com.forumhub.controller;

import com.forumhub.dto.TopicoDTO;
import com.forumhub.dto.TopicoResponseDTO;
import com.forumhub.model.Topico;
import com.forumhub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;


    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid TopicoDTO dto) {
        if (topicoRepository.existsByTituloAndMensagem(dto.getTitulo(), dto.getMensagem())) {
            return ResponseEntity
                    .badRequest()
                    .body("Já existe um tópico com este título e mensagem");
        }

        Topico topico = new Topico(
                dto.getTitulo(),
                dto.getMensagem(),
                dto.getStatus(),
                dto.getAutor(),
                dto.getCurso()
        );
        topicoRepository.save(topico);
        return ResponseEntity.ok(toResponseDTO(topico));
    }


    @GetMapping
    public ResponseEntity<List<TopicoResponseDTO>> listar() {
        List<TopicoResponseDTO> lista = topicoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> detalhar(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(topico -> ResponseEntity.ok(toResponseDTO(topico)))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid TopicoDTO dto) {

        return topicoRepository.findById(id)
                .map(topico -> {
                    topico.setTitulo(dto.getTitulo());
                    topico.setMensagem(dto.getMensagem());
                    topico.setStatus(dto.getStatus());
                    topico.setAutor(dto.getAutor());
                    topico.setCurso(dto.getCurso());
                    topicoRepository.save(topico);
                    return ResponseEntity.ok(toResponseDTO(topico));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (topicoRepository.existsById(id)) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    private TopicoResponseDTO toResponseDTO(Topico topico) {
        return new TopicoResponseDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        );
    }

}
