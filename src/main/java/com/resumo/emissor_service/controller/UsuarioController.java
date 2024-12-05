package com.resumo.emissor_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resumo.emissor_service.dto.UsuarioDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper; // Garante a injeção do ObjectMapper pelo Spring


    @PostMapping
    public String enviarUsuario(@RequestBody UsuarioDTO usuario) {
        try {
            // Converte o UsuarioDTO para JSON
            String usuarioJson = objectMapper.writeValueAsString(usuario);

            // Envia a mensagem JSON para a fila
            rabbitTemplate.convertAndSend("fila.usuarios", usuarioJson);

            System.out.println("Mensagem enviada para a fila: " + usuarioJson);
            return "Usuário enviado para a fila com sucesso!";
        } catch (Exception e) {
            System.err.println("Erro ao enviar usuário para a fila: " + e.getMessage());
            return "Erro ao enviar usuário para a fila.";
        }
    }
}
