package com.sparta.newsfeed.user.util;

import com.sparta.newsfeed.user.config.PasswordEncoder;
import com.sparta.newsfeed.user.entity.UserEntity;
import java.util.Map;
import java.util.Objects;

public class UpdateUtil {

    public static void updateUserFields(UserEntity user, Map<String, Object> updateFields, PasswordEncoder passwordEncoder) {
        if (updateFields.containsKey("email")) {
            String newEmail = (String) updateFields.get("email");
            user.setEmail(newEmail);
        }

        // 비밀번호 수정 시 조건 추가
        if (updateFields.containsKey("currentPassword") && updateFields.containsKey("newPassword")) {
            String currentPassword = (String) updateFields.get("currentPassword");
            String newPassword = (String) updateFields.get("newPassword");
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
            }
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new IllegalArgumentException("새 비밀번호는 현재 비밀번호와 동일할 수 없습니다.");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            updateFields.remove("currentPassword");
            updateFields.remove("newPassword");
        }

        if (updateFields.containsKey("username")) {
            String newUsername = (String) updateFields.get("username");
            user.setUsername(newUsername);
        }

        if (updateFields.containsKey("gender")) {
            UserEntity.Gender newGender = UserEntity.Gender.valueOf((String) updateFields.get("gender"));
            user.setGender(newGender);
        }

        if (updateFields.containsKey("introduce")) {
            String newIntroduce = (String) updateFields.get("introduce");
            user.setIntroduce(newIntroduce);
        }
    }

}
