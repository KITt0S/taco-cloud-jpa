package com.k1ts.web;

import com.k1ts.Ingredient;
import com.k1ts.Order;
import com.k1ts.Taco;
import com.k1ts.dao.IngredientRepository;
import com.k1ts.dao.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import com.k1ts.Ingredient.Type;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping( "/design" )
@SessionAttributes( "order" )
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;

    @Autowired
    public DesignTacoController( IngredientRepository ingredientRepo, TacoRepository designRepo ) {

        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute( name = "order" )
    public Order order() {

        return new Order();
    }

    @ModelAttribute( name = "design" )
    public Taco design() {

        return new Taco();
    }

    @GetMapping
    public String showDesignForm( Model model ) {

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientRepo.findAll().forEach( (n) -> ingredientList.add( n )  );

        Type[] types = Ingredient.Type.values();

        for ( Type t :
                types ) {

            model.addAttribute( t.toString().toLowerCase(), filterByType( ingredientList, t ) );
        }
        model.addAttribute( "design", new Taco() );
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());

    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order ) {

        if( errors.hasErrors() ) {

            log.info( errors.toString() );
            return "/design";
        }
        Taco saved = designRepo.save( design );
        order.addDesign( saved );
        log.info( "Process design " + design );
        return "redirect:/orders/current";
    }
}
