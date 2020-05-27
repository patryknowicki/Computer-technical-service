package pl.pi5518.aplikacja.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pi5518.aplikacja.model.Token;
import pl.pi5518.aplikacja.repository.AppUserRepo;
import pl.pi5518.aplikacja.repository.TokenRepo;
import pl.pi5518.aplikacja.service.UserService;
import pl.pi5518.aplikacja.model.AppUser;

import java.security.Principal;
import java.util.Collection;

@Controller
public class UserController {

    private UserService userService;
    private TokenRepo tokenRepo;
    private AppUserRepo appUserRepo;

    public UserController(UserService userService, TokenRepo tokenRepo, AppUserRepo appUserRepo) {
        this.userService = userService;
        this.tokenRepo = tokenRepo;
        this.appUserRepo = appUserRepo;
    }

        // for REST
//    @GetMapping("/hello")
//    @ResponseBody
//    public String hello(){
//        return "hello dla wszystkich";
//    }

    @RequestMapping("/home")
        public String homePage(){
            return "index";
    }


    @GetMapping("/hello")
    public String forUser(Principal principal, Model model){
        model.addAttribute("name", principal.getName());
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();

        model.addAttribute("authorities", authorities);
        model.addAttribute("details", details);
        principal.getName();
        return "hello";
    }

    @GetMapping("/sing-up")
    public String singup(Model model){
        model.addAttribute("user", new AppUser());
        return "sing-up";
    }

    @PostMapping("/register")
    public String register(AppUser appUser){
        System.out.println(appUser);
        userService.addUser(appUser);
        return "sing-up";
    }

    @GetMapping("/token")
    public String singup(@RequestParam String value ){
        Token byValue = tokenRepo.findByValue(value);
        AppUser appUser = byValue.getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
        return "hello";
    }
    @RequestMapping("/czat")
    public String czat(){
        return "czat";
    }
}
