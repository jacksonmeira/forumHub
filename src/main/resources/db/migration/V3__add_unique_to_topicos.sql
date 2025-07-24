
CREATE UNIQUE INDEX uq_topico_titulo_mensagem
  ON topicos (titulo(200), mensagem(255));
