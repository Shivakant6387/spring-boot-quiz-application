package com.shivakant.main.controller;

import java.util.List;

import com.shivakant.main.model.QuestionForm;
import com.shivakant.main.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.shivakant.main.model.Result;

@Controller
public class MainController {
	
	@Autowired
	Result result;
	@Autowired
	QuizService qService;
	
	Boolean submitted = false;
	
	@ModelAttribute("result")
	public Result getResult() {
		return result;
	}

	@GetMapping("/")
	public String home() {
		return "index.html";
	}
	@GetMapping("/start")
	public String startQuiz(){
		return "quizStart.html";
	}
	
	@PostMapping("/quiz")
	public String quiz(@RequestParam String username, Model m, RedirectAttributes ra) {
		if(username.equals("")) {
			ra.addFlashAttribute("warning", "You must enter your name");
			return "redirect:/";
		}
		
		submitted = false;
		result.setUsername(username);
		
		QuestionForm qForm = qService.getQuestions();
		m.addAttribute("qForm", qForm);
		
		return "quiz.html";
	}
	
	@PostMapping("/submit")
	public String submit(@ModelAttribute QuestionForm qForm, Model m) {
		if(!submitted) {
			result.setTotalCorrect(qService.getResult(qForm));
			qService.saveScore(result);
			submitted = true;
		}
		
		return "result.html";
	}
	
	@GetMapping("/score")
	public String score(Model m) {
		List<Result> sList = qService.getTopScore();
		m.addAttribute("sList", sList);
		
		return "scoreboard.html";
	}
	@GetMapping("/login")
	public String login(Model m) {
		return "login.html";
	}
}
