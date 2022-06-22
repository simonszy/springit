package com.attipoe.springit.controller;

import com.attipoe.springit.domain.Comment;
import com.attipoe.springit.domain.Link;
import com.attipoe.springit.repository.CommentRepository;
import com.attipoe.springit.repository.LinkRepository;
import com.attipoe.springit.service.CommentService;
import com.attipoe.springit.service.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;


@Controller
public class LinkController {

    public static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    private LinkService linkService;
    private CommentService commentService;

    public LinkController(LinkService linkService, CommentService commentService) {
        this.linkService = linkService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("links", linkService.findAll());
        return "link/list";
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/link/{id}")
    public String read(@PathVariable Long id, Model model) {
        Optional<Link> link = linkService.findById(id);
        if(link.isPresent()) {
            Link currentLink = link.get();
            Comment comment = new Comment();
            comment.setLink(currentLink);
            model.addAttribute("comment", comment);
            model.addAttribute("link", currentLink);
            model.addAttribute("success", model.containsAttribute("success"));
            return "link/view";
        } else {
            return "redirect:/view";
        }
    }

    @GetMapping("/link/submit")
    public String newLinkForm(Model model) {
        model.addAttribute("link", new Link());
        return "link/submit";
    }

    @PostMapping("/link/submit")
    public String createLink(@Valid Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            logger.info("Validation errors were found while submitting a new link.");
            model.addAttribute("link", link);
            return "link/submit";
        } else {
            // save our link
            linkService.save(link);
            redirectAttributes
                    .addAttribute("id", link.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/link/{id}";
        }
    }

    @PostMapping("/link/comments")
    public String addComments(@Valid Comment comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("There was a problem adding a new link.");
        } else {
            commentService.save(comment);
            logger.info("New comment was saved successfully.");
        }
        return "redirect:/link/" + comment.getLink().getId();
    }

}



