package likelion.holymoly.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import likelion.holymoly.dto.*;
import likelion.holymoly.entity.Character;
import likelion.holymoly.entity.Member;
import likelion.holymoly.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/user")
@Tag(name = "유저 관리", description = "유저 정보 및 캐릭터 관리 API")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class UserController {
    private MemberService memberService;

    @Operation(summary = "유저 정보 조회", description = "자신의 유저 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Member myMember = memberService.getMemberByPrincipal(userDetails);
        MemberResponse response = MemberResponse.getMemberResponse(myMember);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "유저 캐릭터 정보 조회", description = "자신의 캐릭터 정보가 있으면 조회합니다.")
    @GetMapping("/me/character")
    public ResponseEntity<CharacterResponse> getUserCharacterInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Member myMember = memberService.getMemberByPrincipal(userDetails);
        Character myCharacter = memberService.getMyCharacter(myMember);
        CharacterResponse response = CharacterResponse.getCharacterResponse(myCharacter);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 통해 JWT를 발급합니다.")
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest request) {
        Member member = memberService.kakaoLogin(request);
        TokenResponse response = memberService.generateToken(member);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "캐릭터 생성", description = "자신의 캐릭터가 없다면 캐릭터를 생성합니다.")
    @PostMapping("/me/character")
    public ResponseEntity<CharacterResponse> createCharacter(CharacterItemRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Member myMember = memberService.getMemberByPrincipal(userDetails);
        Character myCharacter = memberService.createMyCharacter(myMember, request);
        CharacterResponse response = CharacterResponse.getCharacterResponse(myCharacter);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "유저 정보 수정", description = "자신의 유저 정보를 수정합니다.")
    @PatchMapping("/me")
    public ResponseEntity<MemberResponse> updateUserInfo(MemberUpdateRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Member myMember = memberService.getMemberByPrincipal(userDetails);
        Member updatedMember = memberService.updateMyMember(myMember, request);
        MemberResponse response = MemberResponse.getMemberResponse(updatedMember);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "캐릭터 소품 변경", description = "자신의 캐릭터에 장착된 소품을 변경합니다.")
    @PatchMapping("/me/character")
    public ResponseEntity<CharacterResponse> updateCharacterEquipment(CharacterItemRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Member myMember = memberService.getMemberByPrincipal(userDetails);
        Character myCharacter = memberService.getMyCharacter(myMember);
        Character updatedCharacter = memberService.updateMyCharacter(myCharacter, request);
        CharacterResponse response = CharacterResponse.getCharacterResponse(updatedCharacter);
        return ResponseEntity.ok(response);
    }
}
