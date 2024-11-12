package likelion.holymoly.service;

import likelion.holymoly.dto.KakaoLoginRequest;
import likelion.holymoly.dto.MemberResponse;
import likelion.holymoly.dto.TokenResponse;
import likelion.holymoly.entity.Member;
import likelion.holymoly.exception.CustomException;
import likelion.holymoly.exception.ErrorCode;
import likelion.holymoly.repository.MemberRepository;
import likelion.holymoly.utils.JwtProviderUtil;
import likelion.holymoly.utils.KakaoOAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final JwtProviderUtil jwtProviderUtil;
    private MemberRepository memberRepository;
    private KakaoOAuthUtil kakaoOAuthUtil;
    private UserDetailsService userDetailsService;

    public Member kakaoLogin(KakaoLoginRequest request) throws CustomException {
        String kakaoAccessToken = null;
        try {
            kakaoAccessToken = kakaoOAuthUtil.fetchKakaoAccessToken(request.getCode());
        } catch (URISyntaxException e) {
            throw new CustomException(ErrorCode.KAKAO_FETCH_ACCESS_TOKEN_FAIL);
        }

        LinkedHashMap<String, Object> response = null;
        try {
            response = kakaoOAuthUtil.fetchKakaoUserData(kakaoAccessToken);
        } catch (URISyntaxException e) {
            throw new CustomException(ErrorCode.KAKAO_FETCH_USER_DATA_FAIL);
        }

        LinkedHashMap<String, Object> kakaoAccount = (LinkedHashMap<String, Object>) response.get("kako_account");

        LinkedHashMap<String, Object> profile = (LinkedHashMap<String, Object>) kakaoAccount.get("profile");
        String email = kakaoAccount.get("email").toString();

        // email이 같은 Member가 존재 : 기존 멤버
        Member existedMember = memberRepository.findByEmail(email).orElse(null);
        if (existedMember != null) {
            return existedMember;
        }

        // email이 같은 Member가 없음 : 새로 가입
        Member newMember = Member.builder()
                .name(profile.get("nickname").toString())
                .email(email)
                .profileImage(profile.get("thumbnail_image_url").toString())
                .build();

        memberRepository.save(newMember);
        return newMember;
    }

    public TokenResponse generateToken(Member member) throws CustomException {
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(member.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND_IN_DB);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        String accessToken = jwtProviderUtil.generateToken(authentication);

        return TokenResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public MemberResponse getMemberResponse(Member member) {
        return MemberResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .build();
    }
}
