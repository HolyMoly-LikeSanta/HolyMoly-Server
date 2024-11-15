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
    private final MemberRepository memberRepository;
    private final CharacterRepository characterRepository;
    private final BackgroundRepository bgRepository;
    private final HeadRepository headRepository;
    private final FaceRepository faceRepository;
    private final ClothesRepository clothesRepository;
    private final AccessoryRepository accessoryRepository;

    private final JwtProviderUtil jwtProviderUtil;
    private final KakaoOAuthUtil kakaoOAuthUtil;
    private final UserDetailsService userDetailsService;

    public Member kakaoLogin(KakaoLoginRequest request) throws CustomException {
        String kakaoAccessToken = null;
        try {
            kakaoAccessToken = kakaoOAuthUtil.fetchKakaoAccessToken(request.getCode());
        } catch (Exception e) {
            throw new CustomException(ErrorCode.KAKAO_FETCH_ACCESS_TOKEN_FAIL);
        }

        LinkedHashMap<String, Object> response = null;
        try {
            response = kakaoOAuthUtil.fetchKakaoUserData(kakaoAccessToken);
        } catch (Exception e) {
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
        Character myCharacter = Character.builder().build();
        if (request.getBgId() != null) {
            Background background = bgRepository.findById(request.getBgId())
                    .orElseThrow(() -> new CustomException(ErrorCode.BACKGROUND_NOT_FOUND));
            myCharacter.setBackground(background);
        }
        if (request.getHeadId() != null) {
            Head head = headRepository.findById(request.getHeadId())
                    .orElseThrow(() -> new CustomException(ErrorCode.HEAD_NOT_FOUND));
            myCharacter.setHead(head);
        }
        if (request.getFaceId() != null) {
            Face face = faceRepository.findById(request.getFaceId())
                    .orElseThrow(() -> new CustomException(ErrorCode.FACE_NOT_FOUND));
            myCharacter.setFace(face);
        }
        if (request.getClothesId() != null) {
            Clothes clothes = clothesRepository.findById(request.getClothesId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CLOTHES_NOT_FOUND));
            myCharacter.setClothes(clothes);
        }
        if (request.getAccessoryId() != null) {
            Accessory accessory = accessoryRepository.findById(request.getAccessoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.ACCESSORY_NOT_FOUND));
            myCharacter.setAccessory(accessory);
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
            Background background = bgRepository.findById(request.getBgId())
                    .orElseThrow(() -> new CustomException(ErrorCode.BACKGROUND_NOT_FOUND));
            myCharacter.setBackground(background);
        }
        if (request.getHeadId() != null) {
            Head head = headRepository.findById(request.getHeadId())
                    .orElseThrow(() -> new CustomException(ErrorCode.HEAD_NOT_FOUND));
            myCharacter.setHead(head);
        }
        if (request.getFaceId() != null) {
            Face face = faceRepository.findById(request.getFaceId())
                    .orElseThrow(() -> new CustomException(ErrorCode.FACE_NOT_FOUND));
            myCharacter.setFace(face);
        }
        if (request.getClothesId() != null) {
            Clothes clothes = clothesRepository.findById(request.getClothesId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CLOTHES_NOT_FOUND));
            myCharacter.setClothes(clothes);
        }
        if (request.getAccessoryId() != null) {
            Accessory accessory = accessoryRepository.findById(request.getAccessoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.ACCESSORY_NOT_FOUND));
            myCharacter.setAccessory(accessory);
        }
        characterRepository.save(myCharacter);
        return myCharacter;
    }

    public Member getMemberByPrincipal(UserDetails userDetails) {
        return memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
