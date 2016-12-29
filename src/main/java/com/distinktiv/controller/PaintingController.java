package com.distinktiv.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by distinktiv on 2016-12-27.
 */

@Controller
@RequestMapping(value = "/painting")
public class PaintingController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String admin(ModelAndView model,
                              HttpServletRequest request){

        //model.setViewName("admin/painting");
        //return(model);
        return "admin/painting.list";

    }

    //{id}
    //add
    //delete
}
