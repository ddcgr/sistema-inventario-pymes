package com.pymes.inventario.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final PasswordEncoder passwordEncoder;

    public TestController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/check")
    public String testPassword(@RequestParam String raw) {
        String storedHash = "$2a$10$4Db4EhXMCsBHTed77xUNr.2Zb8vLrNhw4sNCz43ObGzzP/s88ZsGS";
        boolean match = passwordEncoder.matches(raw, storedHash);
        return match ? "✅ Coincide" : "❌ No coincide";
    }
}
