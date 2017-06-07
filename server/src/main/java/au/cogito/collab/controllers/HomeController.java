package au.cogito.collab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by pavankumarjoshi on 29/04/2017.
 */
@Controller
public class HomeController {

    @RequestMapping(name="HomeController",value="/",method= RequestMethod.GET)
    public String getHome(){
        return "index";
    }
}
