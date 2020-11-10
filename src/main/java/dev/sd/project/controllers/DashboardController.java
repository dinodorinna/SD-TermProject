package dev.sd.project.controllers;

import dev.sd.project.model.User;
import dev.sd.project.repository.UserRepository;
import dev.sd.project.service.ArticleService;
import dev.sd.project.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Level;

@Controller
@Log
@AllArgsConstructor
public class DashboardController {
    private final UserService userService;
    private final ArticleService articleService;



    @GetMapping (value = {"/","/dashboard"})  //multiple path
    public ModelAndView dashboardView(){
        /*try {
            userService.createUser("admin","admin@web.com","1234"); //add user
        }catch (Exception e){
        }*/
        //log.log(Level.INFO, String.valueOf(userService.checkLogin("admin","1234"))); // change object to string
        return new ModelAndView("dashboard");
    }
}
