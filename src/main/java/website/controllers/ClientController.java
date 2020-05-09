package website.controllers;




import java.lang.module.FindException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import website.controllers.ActiveUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import application.ContainerApp;
import application.data.Element;
import application.data.QueryHashSet;
import application.data.QueryLinkedList;
import application.models.Client;
import application.models.Container;
import application.models.LogisticCompany;
import application.models.Port;
import application.models.User;
import website.model.CredentialForm;
import website.model.JourneyForm;
import website.model.KeywordForm;
import website.model.UserForm;
/**/
@Controller
public class ClientController {
	
	Collection<Element> list = new LinkedList<Element>((Collection<? extends Element>) ContainerApp.getInstance().getPorts());
	Collection<Element> list2 = new LinkedList<Element>(((Client)ActiveUser.getUser()).getClientContainers());
	
	@ModelAttribute("userForm")
	public UserForm populateUser() {
	  UserForm userForm = new UserForm(ActiveUser.getUser().get("clientName"), ActiveUser.getUser().get("address"), ActiveUser.getUser().get("email"), ActiveUser.getUser().get("contactPerson"), ActiveUser.getUser().get("password"));
	  return userForm;
	}
	
	@ModelAttribute("ports")
	public Collection<Element> portList() {
		return list;
	}
	
	@ModelAttribute("containers")
	public Collection<Element> containerList() {
	  return list2;
	}
	
	@ModelAttribute("findportmessage")
	public String findPortMessage(String string) {
	  return string;
	}
	
	
	@GetMapping("/clientview")
	public String clientView(Model model) {
		model.addAttribute("test", ActiveUser.getUser().get("clientName"));
		model.addAttribute("journeyForm", new JourneyForm());
		model.addAttribute("keywordForm", new KeywordForm());
		
		
		try {
			model.addAttribute("containers", ((Client) ActiveUser.getUser()).getClientContainers());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "clientview";
		
		
	}
	
	@GetMapping("/logisitccompanyview")
	public String logisticCompany(Model model) {
//		model.addAttribute("logisitccompanyForm", (LogisitcCompanyForm) user);
		
		return "logisitccompanyview";
	}
	
	@PostMapping("/registercontainer")
	public String registerContainer(@Valid JourneyForm journeyForm, BindingResult result, Model model, UserForm userForm, KeywordForm keywordForm) {
		try { 
			ContainerApp.getInstance().registerContainer(journeyForm.getPortOfOrigin(), journeyForm.getDestination(), journeyForm.getContent(), (Client) ActiveUser.getUser());
		} catch (Exception e) {
			model.addAttribute("registercontainermessage", e.getMessage());
			e.printStackTrace();
			System.out.println(ActiveUser.getUser().get("clientName"));
			return "clientview";	
		}
		
		model.addAttribute("registercontainermessage", "Container successfully registered");
		return clientView(model);
	}

	@PostMapping("/updateclient")
	public String updateClient(@Valid UserForm userForm, BindingResult result, Model model, JourneyForm journeyForm, KeywordForm keywordForm) {
		try {
			ContainerApp.getInstance().updateClient((Client) ActiveUser.getUser(), "clientName", userForm.getClientName());
			ContainerApp.getInstance().updateClient((Client) ActiveUser.getUser(), "address", userForm.getAddress());
			ContainerApp.getInstance().updateClient((Client) ActiveUser.getUser(), "email", userForm.getEmail());
			ContainerApp.getInstance().updateClient((Client) ActiveUser.getUser(), "contactPerson", userForm.getContactPerson());
			ContainerApp.getInstance().updateClient((Client) ActiveUser.getUser(), "password", userForm.getPassword());
		} catch (Exception e) {
			model.addAttribute("updateclientmessage", e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("updateclientmessage", "Your information has been updated");
		return "clientview";
		
	}
	
	@PostMapping("/findport")
	public String findPort(@Valid UserForm userForm, BindingResult result, Model model, JourneyForm journeyForm, KeywordForm keywordForm) {
		try {
			list.clear();
			list.addAll(ContainerApp.getInstance().findPorts(keywordForm.getKeyword().split(" ")));
			
		} catch (Exception e) {
			model.addAttribute("findportmessage", e.getMessage());
			return "clientview";
		}
		
		return "redirect:/clientview";
	}
	
	@PostMapping("/findcontainer")
	public String findContainer(@Valid UserForm userForm, BindingResult result, Model model, JourneyForm journeyForm, KeywordForm keywordForm) {
		try {
			list2.clear();
			list2.addAll(((QueryLinkedList<Element>) ((Client)ActiveUser.getUser()).getClientContainers()).findElements(keywordForm.getKeyword().split(" ")));
			System.out.println(list2);
		} catch (Exception e) {
			model.addAttribute("findcontainermessage", e.getMessage());
			return "clientview";
		}
		
		return "clientview";
	}
	
	
	
	
	
	 
	
	
	
	
	
	
}
