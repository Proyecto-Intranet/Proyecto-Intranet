package com.intranetstemdo.intranet.chatbot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaChatRepository extends JpaRepository<ConsultaChat, Long> {

    List<ConsultaChat> findTop50ByRespondidaFalseOrderByFechaDesc();
}
