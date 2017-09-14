package com.example.demo.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.CommentRepository;
import com.example.demo.dao.MyUserRepository;
import com.example.demo.dao.NoteRepository;
import com.example.demo.domain.Comment;
import com.example.demo.domain.MyUser;
import com.example.demo.domain.Note;




@Controller
public class NoteController {
	
	@Autowired
	NoteRepository np;
	
	@Autowired
	CommentRepository cp; 
	
	@Autowired
	MyUserRepository mup;
	
	static String editedNoteName;
	
	@RequestMapping("/upload")
	String upload(Model model) {
		Note note=new Note();
		model.addAttribute("note",note);
		return "upload";
	}
	
    @RequestMapping(value = "/processUpload", method=RequestMethod.POST)
    @CachePut(value="note",key="#note.name")
    public String processUpload(@ModelAttribute(value="note") Note note,Model model) throws IOException {
    	if(note.getCategory()=="" || note.getName()=="") {
    		System.out.println("fail to upload");
    		model.addAttribute("note",note);
    		return "upload";
    	}
    	if(np.findByName(note.getName()) != null) {
    		System.out.println("duplicate");
    		model.addAttribute("note",note);
    		return "upload";
    	}
      Note newNote=note;
      String content=newNote.getContent();
      String regexPattern = "<!--.*-->";
      Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(content);
      content = matcher.replaceAll("");
      newNote.setContent(content);
      np.save(newNote);
      model.addAttribute("note",newNote);
      System.out.println("为上传的note做了缓存");
      File file=new File("E:\\EclipseProject\\demo\\src\\main\\resources\\templates\\header.html");
      BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      StringBuilder newHtml=new StringBuilder();
      String tempLine;
      int step=3;
      while((tempLine=br.readLine())!=null) {
    	  newHtml.append(tempLine+"\n");
    	  if(tempLine.contains(newNote.getCategory())) {
    		  while((tempLine=br.readLine())!=null && step!=0) {
    			  newHtml.append(tempLine+"\n");
    			  step--;
    		  }
    		  newHtml.append("\t\t\t\t\t\t<li><a href=\"/note?name="+newNote.getName()+"\">"+newNote.getName()+"</a></li>\n");
    		  newHtml.append(tempLine+"\n");
    	  }
      }
      PrintWriter pw=new PrintWriter(new FileOutputStream(file));
      pw.print(newHtml.toString());
      br.close();
      pw.close();
      return "note";
    }  
    
    
    @RequestMapping(value="/note")
    public String getNote(Model model,@RequestParam String name) {
    	Note note=np.findByName(name);
    	model.addAttribute("note",note);
    	return "note";
    }
    
    
    @RequestMapping(value="/remove")
    public synchronized String removeNote(@RequestParam String name,RedirectAttributes model) throws IOException {
    	Note note=np.findByName(name);
    	String category=note.getCategory();
    	File file=new File("E:\\EclipseProject\\demo\\src\\main\\resources\\templates\\header.html");
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder newHtml=new StringBuilder();
        String tempLine;
        while((tempLine=br.readLine())!=null) {
      	  newHtml.append(tempLine+"\n");
      	  if(tempLine.contains(category)) {
      		  while((tempLine=br.readLine())!=null) {
      			  if(tempLine.contains(name)) {
      				  break;
      			  }
      			  else {
      				 newHtml.append(tempLine+"\n");
      			  }	  
      		  }
      	  }
        }
        PrintWriter pw=new PrintWriter(new FileOutputStream(file));
        pw.print(newHtml.toString());
        br.close();
        pw.close();
        np.delete(note);
        model.addAttribute("refresh", 1);
    	return "redirect:/";
    }
    
    @RequestMapping("/edit")
    String editNote(@RequestParam String name,Model model) {
    	Note oldNote=np.findByName(name);
    	Note note=new Note();
    	note=oldNote;
    	editedNoteName=note.getName();
    	np.delete(oldNote);
    	model.addAttribute("note",note);
    	return "edit";
    }
    
    //总结一下，从form里出来的就算原先有id也会被去除，无id在save时有mongo给出，但不可remove
    @RequestMapping(value = "/processUpdate", method=RequestMethod.POST)
    public synchronized String processUpdate(@ModelAttribute(value="note") Note note,RedirectAttributes model) throws IOException {
    	np.save(note);
    	if(editedNoteName.equals(note.getName())) {
    		
    	}
    	else {
    		File file=new File("E:\\EclipseProject\\demo\\src\\main\\resources\\templates\\header.html");
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuilder newHtml=new StringBuilder();
            String tempLine;
            while((tempLine=br.readLine())!=null) {
          	  newHtml.append(tempLine+"\n");
          	  if(tempLine.contains(note.getCategory())) {
          		  while((tempLine=br.readLine())!=null) {
          			  if(tempLine.contains(editedNoteName)) {
          				newHtml.append("\t\t\t\t\t\t<li><a href=\"/note?name="+note.getName()+"\">"+note.getName()+"</a></li>\n");
          				break;
          			  }
          			  else {
          				 newHtml.append(tempLine+"\n");
          			  }	  
          		  }
          	  }
            }
            PrintWriter pw=new PrintWriter(new FileOutputStream(file));
            pw.print(newHtml.toString());
            br.close();
            pw.close();
            model.addAttribute("refresh", 1);
        	return "redirect:/";
    	}
    	return "index";
    }  
    
    @RequestMapping("/checkname")
    @ResponseBody String checkName(@RequestParam String name) {
    	if(np.findByName(name) != null) {
    		return "false";
    	}
    	return "true";
    }
    //没有重启不生效？或者是页面刷新问题？
    @RequestMapping("/checkac")
    @ResponseBody String checkAc(@RequestParam String ac) {
    	if(ac.equals("13025248595")) {
    		System.out.println("true");
    		return "true";
    	}
    	else {
    	System.out.println("false");
    	return "false";
    	}
    }
    
    @RequestMapping("/comment")
    public String comment(Model model) {
    	Comment comment=new Comment();
    	model.addAttribute("singlecomment",comment);
    	 ArrayList<Comment> comments=new ArrayList<Comment>();
         comments.addAll(cp.findAll());
         model.addAttribute("comments",comments);
    	return "comment";
    }
    
    
    
    @RequestMapping(value = "/processComment", method=RequestMethod.POST)
    public String processComment(@ModelAttribute(value="comment") Comment comment,Model model) throws IOException {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
    		    .getAuthentication()
    		    .getPrincipal();
        comment.setUsername(userDetails.getUsername());
        comment.setTotal(userDetails.getUsername()+":"+comment.getContent());
        cp.save(comment);
        ArrayList<Comment> comments=new ArrayList<Comment>();
        comments.addAll(cp.findAll());
        model.addAttribute("comments",comments);
        Comment singlecomment=new Comment();
    	model.addAttribute("singlecomment",singlecomment);
    	model.addAttribute("refresh","once");
      return "comment";
    }
    
    @RequestMapping("/register")
    public String register(Model model) {
    	MyUser user=new MyUser();
    	model.addAttribute("user",user);
    	return "register";
    }
    
    
    @RequestMapping(value = "/registerUser", method=RequestMethod.POST)
    public String registerUser(@ModelAttribute(value="user") MyUser  user) {
    	mup.save(user);
    	System.out.println("注册成功");
    	return "index";
    }
    
      
	
}
