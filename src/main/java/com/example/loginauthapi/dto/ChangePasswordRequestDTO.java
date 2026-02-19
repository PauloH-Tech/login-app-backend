package com.example.loginauthapi.dto;

public record ChangePasswordRequestDTO (String email, String oldPassword, String newPassword, String descriptionApp){
}
