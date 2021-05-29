package com.franciscoaguiar.cursomc.services;

import com.franciscoaguiar.cursomc.domain.Cliente;
import com.franciscoaguiar.cursomc.repositories.ClienteRepository;
import com.franciscoaguiar.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email){
        Cliente cliente = repository.findByEmail(email);
        if(cliente == null){
            throw new ObjectNotFoundException("Email não encontrado");
        }
        String newPass = newPassWord();
        cliente.setSenha(passwordEncoder.encode(newPass));
        //verificar se precisa salvar de novo no banco
        repository.save(cliente);

        emailService.sendNewPasswordEmail(cliente, newPass);

    }
    private String newPassWord(){
        char[] vet = new char[10];
        for(int i=0 ; i<10 ; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    //codigos dos intervalos randomicos são da tabela unicode
    private char randomChar() {
        int opt = random.nextInt(3);
        if(opt == 0){ //gera um dígito
            return (char) (random.nextInt(10) + 48);
        }
        else if(opt == 1){ // gera uma letra maiúscula
            return (char) (random.nextInt(26) + 65);
        }
        else{ //gera uma letra minuscula
            return (char) (random.nextInt(10) + 48);
        }
    }


}
