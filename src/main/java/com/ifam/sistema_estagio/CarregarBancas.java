package com.ifam.sistema_estagio;

import com.ifam.sistema_estagio.entity.Funcao;
import com.ifam.sistema_estagio.entity.Papel;
import com.ifam.sistema_estagio.repository.FuncaoRepository;
import com.ifam.sistema_estagio.repository.PapelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Order(1)
public class CarregarBancas implements ApplicationRunner{

	@Autowired
	private PapelRepository papelRepository;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

	}
	
	private Funcao criarFuncaoSeNaoExiste(String nome) {
		Optional<Funcao> funcao = funcaoRepository.findByNome(nome);
	
		if(funcao.isPresent()) {
			return funcao.get();
		}
		
		return funcaoRepository.save(new Funcao(nome));
	}

	private Papel criarPapelSeNaoExiste(String nome, List<Funcao> funcao) {
		Optional<Papel> papel = papelRepository.findByNome(nome);
		
		if(papel.isPresent()) {
			return papel.get();
		}
		
		return papelRepository.save(new Papel(nome, funcao));
	}

}
