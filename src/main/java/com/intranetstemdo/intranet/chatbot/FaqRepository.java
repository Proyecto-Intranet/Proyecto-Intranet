package com.intranetstemdo.intranet.chatbot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {

    List<Faq> findByActivaTrue();

    List<Faq> findByActivaTrueOrderByTemaAscPreguntaAsc();
}
