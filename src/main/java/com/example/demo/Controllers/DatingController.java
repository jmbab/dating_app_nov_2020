package com.example.demo.Controllers;

import com.example.demo.Models.Profile;
import com.example.demo.Repositories.ProfileRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DatingController {

    ProfileRepository rp = new ProfileRepository();
    List<Profile> allProfiles = new ArrayList<>();
    List<Profile> searchLogin = new ArrayList<>();
    // Profile profile = new Profile(0,null,null,null,null,null, 0, null);


    // Root
    @GetMapping("/")
    public String index(Model profileModel){
        profileModel.addAttribute("profile", rp.listAllProfiles());
        return "index";
    }

    // Create profile
    @PostMapping("/createprofile")
    public String createProfile(WebRequest createProfileData) throws SQLException {
        String gender = null;
        int admin = 0;
        String name = createProfileData.getParameter("pName");
        if (createProfileData.getParameter("pGender").equals(createProfileData.getParameterValues("pGenderMand"))) {
            gender = "Mand";
        } else {
            gender = "Kvinde";
        }
       // String gender = createProfileData.getParameter("pGender");
        String email = createProfileData.getParameter("pEmail");
        String description = createProfileData.getParameter("pDescription");
        String kodeord = createProfileData.getParameter("pKodeord");
        rp.createProfile(name, kodeord, gender,email,description,admin);
        return "redirect:/login";
    }

    @PostMapping("/correctlogin")
    public String login(WebRequest loginData)  throws SQLException{

        String email = loginData.getParameter("pEmail");
        String kodeord = loginData.getParameter("pKodeord");
        allProfiles = rp.searchLogin(email,kodeord);

        try {
            Profile p = allProfiles.get(0);
            System.out.println(p.getId());
        } catch (IndexOutOfBoundsException e) {
            return "errorlogin";
        }
        return "main";
    }

    // Delete Profile
    @PostMapping("/deleteprofile")
    public String deleteProfile(WebRequest deleteProfile) {
        try {
            int id = Integer.parseInt(deleteProfile.getParameter("delete"));
            rp.deleteProfile(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "redirect:/";
    }

    // Edit Profile
    @PostMapping("/editprofile")
    public String editProfile(WebRequest editProfile) {
        try {
            String gender = null;
            int id = Integer.parseInt(editProfile.getParameter("eId"));
            String name = editProfile.getParameter("eName");
            //String gender = editProfile.getParameter("eGender");
            if (editProfile.getParameter("pGender") == editProfile.getParameter("pGenderMand")) {
                gender = "Mand";
            } else {
                gender = "Kvinde";
            }
            String email = editProfile.getParameter("eEmail");
            String description = editProfile.getParameter("eDescription");
            rp.editProfile(id,name,gender,email,description);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "redirect:/";
    }

    // Search Profiles
    @GetMapping("/main")
    public String searchProfiles(Model searchModel, WebRequest searchProfile){
        String gender = null;

        if (searchProfile.getParameter("pGender").equals(searchProfile.getParameterValues("pGenderMand")))  {
            gender = "Mand";
        } else {
            gender = "Kvinde";
        }
        try {
            allProfiles = rp.searchProfile(gender);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        searchModel.addAttribute("profileList",allProfiles);
        return "main";
    }

    //Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    //Admin
    @GetMapping("/admin")
    public String admin(Model model) {
        //model.addAttribute("pLogin", new login());
        //model.addAttribute("pName", "pKodeord");
        return "admin";
    }

    //Om os
    @GetMapping("/omos")
    public String omos(Model model) {
        return "omos";
    }

    //Sugar Mommy
    @GetMapping("/sugarmommy")
    public String sugarmommy(Model model) {
        return "sugarmommy";
    }

    //Sugar Daddy
    @GetMapping("/sugardaddy")
    public String sugardaddy(Model model) {
        return "sugardaddy";
    }


    @GetMapping("/profile")
    public String profile(Model profileModel) {
        profileModel.addAttribute("profileList",allProfiles);
        return "profile";
    }

    @PostMapping("/getProfile")
    public String uniqueProfile(WebRequest profile){
        int profileId = Integer.parseInt(profile.getParameter("profileId"));

        try {
            allProfiles = rp.profile(profileId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "redirect:/profile";

    }

    /*
   @PostMapping("/login")
    public String login(WebRequest login) throws SQLException {
        String
        model.addAttribute("contactForm", contactForm);
        // Tilføj ArrayList og / eller FileWriter her?
        return "contactReceipt";
    } */

   //         String name = createProfileData.getParameter("pName");

    }




