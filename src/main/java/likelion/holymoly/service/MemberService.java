package likelion.holymoly.service;

import likelion.holymoly.dto.CharacterItemRequest;
import likelion.holymoly.dto.KakaoLoginRequest;
import likelion.holymoly.dto.MemberUpdateRequest;
import likelion.holymoly.dto.TokenResponse;
import likelion.holymoly.entity.*;
import likelion.holymoly.entity.Character;
import likelion.holymoly.exception.CustomException;
import likelion.holymoly.exception.ErrorCode;
import likelion.holymoly.repository.*;
import likelion.holymoly.utils.JwtProviderUtil;
import likelion.holymoly.utils.KakaoOAuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CharacterRepository characterRepository;
    private final JwtProviderUtil jwtProviderUtil;
    private final KakaoOAuthUtil kakaoOAuthUtil;
    private final UserDetailsService userDetailsService;

    public Member kakaoLogin(KakaoLoginRequest request) throws CustomException {
        String kakaoAccessToken;
        try {
            kakaoAccessToken = kakaoOAuthUtil.fetchKakaoAccessToken(request.getCode());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.KAKAO_FETCH_ACCESS_TOKEN_FAIL);
        }

        LinkedHashMap<String, Object> response;
        try {
            response = kakaoOAuthUtil.fetchKakaoUserData(kakaoAccessToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(ErrorCode.KAKAO_FETCH_USER_DATA_FAIL);
        }

        String kakaoId = response.get("id").toString();

        LinkedHashMap<String, Object> kakaoAccount = (LinkedHashMap<String, Object>) response.get("kakao_account");
        LinkedHashMap<String, Object> profile = (LinkedHashMap<String, Object>) kakaoAccount.get("profile");

        // kakaoId가 같은 Member가 존재 : 기존 멤버
        Member existedMember = memberRepository.findByKakaoId(kakaoId).orElse(null);
        if (existedMember != null) {
            return existedMember;
        }

        // kakaoId가 같은 Member가 없음 : 새로 가입
        Member newMember = Member.builder()
                .name(profile.get("nickname").toString())
                .kakaoId(kakaoId)
                .profileImage(profile.get("profile_image_url").toString())
                .build();

        memberRepository.save(newMember);
        return newMember;
    }

    public TokenResponse generateToken(Member member) throws CustomException {
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(member.getKakaoId());
        } catch (UsernameNotFoundException e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        String accessToken = jwtProviderUtil.generateToken(authentication);

        return TokenResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .build();
    }

    public Character getMyCharacter(Member member) throws CustomException {
        if (member.getCharacter() == null) {
            throw new CustomException(ErrorCode.CHARACTER_NOT_FOUND);
        }
        return member.getCharacter();
    }

    public Character createMyCharacter(Member member, CharacterItemRequest request) throws CustomException {
        if (member.getCharacter() != null) {
            throw new CustomException(ErrorCode.CHARACTER_ALREADY_EXISTS);
        }
        Character myCharacter = Character.builder().build();
        if (request.getBgId() != null) {
            myCharacter.setBackgroundId(request.getBgId());
        }
        if (request.getHeadId() != null) {
            myCharacter.setHeadId(request.getHeadId());
        }
        if (request.getFaceId() != null) {
            myCharacter.setFaceId(request.getFaceId());
        }
        if (request.getClothesId() != null) {
            myCharacter.setClothesId(request.getClothesId());
        }
        if (request.getAccessoryId() != null) {
            myCharacter.setAccessoryId(request.getAccessoryId());
        }
        characterRepository.save(myCharacter);
        member.setCharacter(myCharacter);
        memberRepository.save(member);
        return myCharacter;
    }

    public Member updateMyMember(Member member, MemberUpdateRequest request) throws CustomException {
        if (request.getName() != null) {
            member.setName(request.getName());
        }
        if (request.getProfileImage() != null) {
            member.setProfileImage(request.getProfileImage());
        }
        memberRepository.save(member);
        return member;
    }

    public Character updateMyCharacter(Character myCharacter, CharacterItemRequest request) throws CustomException {
        if (request.getBgId() != null) {
            myCharacter.setBackgroundId(request.getBgId());
        }
        if (request.getHeadId() != null) {
            myCharacter.setHeadId(request.getHeadId());
        }
        if (request.getFaceId() != null) {
            myCharacter.setFaceId(request.getFaceId());
        }
        if (request.getClothesId() != null) {
            myCharacter.setClothesId(request.getClothesId());
        }
        if (request.getAccessoryId() != null) {
            myCharacter.setAccessoryId(request.getAccessoryId());
        }
        characterRepository.save(myCharacter);
        return myCharacter;
    }

    public Member getMemberByPrincipal(UserDetails userDetails) {
        return memberRepository.findByKakaoId(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
