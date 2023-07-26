package com.example.trackAdmin;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class AuthController {

    private final String USERS_FILE_PATH = "users.txt";
    private final String LOG_FILE_PATH = "AuditLog.txt";
    private List<AppUser> usersList;

    public AuthController() {
        usersList = loadUsers();
    }

    @GetMapping("/auth")
    public String showAuthForm() {
        return "auth";
    }

@PostMapping("/auth")
public String processAuth(@RequestParam("action") String action,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Model model, HttpServletResponse response) {
    LocalDateTime currentTime = LocalDateTime.now();
    String logMessage;

    if ("login".equals(action)) {
        if (isValidCredentials(username, password)) {
            logMessage = username + " logged in at " + currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writeToLogFile(logMessage);

            AppUser user = findUserByUsername(username);
            if ("Admin".equals(username)) {
                return "redirect:/Szlachta";
            } else if (user != null && user.isAdmin()) {
                Cookie cookie = null;
                try {
                    cookie = new Cookie("loggedInUser", URLEncoder.encode(username, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/Mieszczanstwo";
            } else {
                Cookie cookie = null;
                try {
                    cookie = new Cookie("loggedInUser", URLEncoder.encode(username, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/Chlopi";
            }
        } else {
            model.addAttribute("loginError", true);
            return "auth";
        }
    } else if ("register".equals(action)) {
        // Rejestracja
        if (!userExists(username)) {
            addUser(username, password);
            model.addAttribute("registrationSuccess", true);

            logMessage = username + " registered at " + currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writeToLogFile(logMessage);
        } else {
            model.addAttribute("registrationError", true);
        }
        return "auth";
    }


    return "auth";
}


    private String hashPassword(String plainPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(plainPassword);
    }

    private boolean checkPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }


    @GetMapping("/logi")
    public String showLogi(Model model) {
        List<LogEntry> logs = readLogsFromFile();
        model.addAttribute("logs", logs);

        String username = "Admin";
        LocalDateTime currentTime = LocalDateTime.now();
        String logMessage = username + " opened logs at " + currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        writeToLogFile(logMessage);
        return "/logi";
    }



    private List<LogEntry> readLogsFromFile() {
        List<LogEntry> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String dateTime = parts[0];
                    String message = line.substring(dateTime.length() + 1);
                    logs.add(new LogEntry(dateTime, message));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logs;
    }

    public class LogEntry {
        private String dateTime;
        private String message;

        public LogEntry(String dateTime, String message) {
            this.dateTime = dateTime;
            this.message = message;
        }

        public String getDateTime() {
            return dateTime;
        }

        public String getMessage() {
            return message;
        }
    }

    @GetMapping("/Szlachta")
    public String showSzlachtaPage(Model model) {
        model.addAttribute("users", usersList);
        return "Szlachta";
    }

    @GetMapping("/Chlopi")
    public String showChlopiPage(Model model) {
        return "Chlopi";
    }


    @GetMapping("/Mieszczanstwo")
    public String showMieszczanstwoPage(Model model) {
        return "Mieszczanstwo";
    }


    @GetMapping("/")
    public String redirectToSzlachta() {
        return "redirect:/Szlachta";
    }

    @PostMapping("/toggleAdmin/{username}")
    public String toggleAdmin(@PathVariable("username") String username) {
        AppUser user = findUserByUsername(username);
        if (user != null) {
            boolean isAdmin = user.isAdmin();
            user.setAdmin(!isAdmin);
            saveUsersToFile();

            String action = isAdmin ? "set to false" : "set to true";
            LocalDateTime currentTime = LocalDateTime.now();
            String logMessage = "Admin status for user " + username + " " + action + " at " + currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writeToLogFile(logMessage);
        }
        return "redirect:/Szlachta";
    }

    @PostMapping("/removeUser/{username}")
    public String removeUser(@PathVariable("username") String username) {
        AppUser user = findUserByUsername(username);
        if (user != null && !user.isAdmin()) {
            usersList.remove(user);
            saveUsersToFile();

            LocalDateTime currentTime = LocalDateTime.now();
            String logMessage = "User " + username + " removed at " + currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writeToLogFile(logMessage);
        }
        return "redirect:/Szlachta";
    }

private boolean isValidCredentials(String username, String password) {
    for (AppUser user : usersList) {
        if (user.getUsername().equals(username)) {
            return checkPassword(password, user.getPassword());
        }
    }
    return false;
}

    private boolean userExists(String username) {
        for (AppUser user : usersList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH))) {
            for (AppUser user : usersList) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.isAdmin());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<AppUser> loadUsers() {
        List<AppUser> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    boolean isAdmin = Boolean.parseBoolean(parts[2]);
                    users.add(new AppUser(username, password, isAdmin));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void writeToLogFile(String logMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Getter
    @Setter
    public static class AppUser {
        private String username;
        private String password;
        private boolean isAdmin;

        public AppUser(String username, String password, boolean isAdmin) {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        }

        public boolean checkPassword(String rawPassword) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(rawPassword, this.password);
        }
    }

    private AppUser findUserByUsername(String username) {
        for (AppUser user : usersList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void addUser(String username, String password) {
        String hashedPassword = hashPassword(password);
        usersList.add(new AppUser(username, hashedPassword, false));
        saveUsersToFile();
    }



}