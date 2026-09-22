package com.example.controller;

import com.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecureLoginController {

    private final UserService userService;

    public SecureLoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("Usuário logado: " + authentication.getName());
            model.addAttribute("usuario", authentication.getName());
        }
        return "home";
    }

    @GetMapping("/login")
    public String login(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("usuario", authentication.getName());
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("cpf") String cpf,
            @RequestParam("rg") String rg,
            @RequestParam("campus") String campus,
            @RequestParam("instituicao") String instituicao,
            @RequestParam("senha") String senha,
            @RequestParam("confirmarSenha") String confirmarSenha)
        {

        if (!senha.equals(confirmarSenha)) {
            return "redirect:/register?erro=senha";
        }

        if (userService.exists(email)) {
            System.out.println("Usuário já cadastrado: " + email);
            return "redirect:/register?erro=true";
        }

        userService.createUser(email, senha, nome);
        System.out.println("Usuário cadastrado com sucesso!");
        System.out.println("Nome cadastrado: " + nome);
        return "redirect:/login?cadastro=sucesso";
    }

    @GetMapping("/recoverpassword")
    public String recoverpassword() {
        return "recoverpassword";
    }

    @PostMapping("/recoverpassword")
    public String handleRecoverpassword(@RequestParam("email") String email) {
        if (!userService.exists(email)) {
            System.out.println("Email não encontrado: " + email);
            return "redirect:/recoverpassword?erro=email";
        }

        String nome = userService.getName(email);
        if (nome == null || nome.isBlank()) {
            nome = email;
        }

        System.out.println("Recuperação solicitada para: " + email + " (" + nome + ")");
        return "redirect:/recoverpassword?sucesso=email";
    }

    @GetMapping("/resetpassword")
    public String resetpassword() {
        return "resetpassword";
    }

    @PostMapping("/resetpassword")
    public String handleResetpassword(
            @RequestParam("email") String email,
            @RequestParam("senha") String senha,
            @RequestParam("confirmarSenha") String confirmarSenha) {

        if (!senha.equals(confirmarSenha)) {
            return "redirect:/resetpassword?erro=true";
        }

        userService.updatePassword(email, senha);
        System.out.println("Senha alterada com sucesso!");
        return "redirect:/login?senha=alterada";
    }
}
