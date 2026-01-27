package ssurent.ssurentbe.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String studentNum) throws UsernameNotFoundException {
        Users user = userRepository.findByStudentNum(studentNum)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + studentNum));

        return new User(
                user.getStudentNum(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
