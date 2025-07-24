package com.forumhub.dto;

import java.time.LocalDateTime;

public class TopicoResponseDTO {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String status;
    private String autor;
    private String curso;

    public TopicoResponseDTO(Long id, String titulo, String mensagem,
                             LocalDateTime dataCriacao, String status,
                             String autor, String curso) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.autor = autor;
        this.curso = curso;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensagem() { return mensagem; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public String getStatus() { return status; }
    public String getAutor() { return autor; }
    public String getCurso() { return curso; }
}
