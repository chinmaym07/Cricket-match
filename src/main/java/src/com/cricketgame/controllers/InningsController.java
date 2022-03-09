package src.com.cricketgame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import src.com.cricketgame.services.InningsService;

@RestController
public class InningsController {
    @Autowired
    private InningsService inningsService;

}
