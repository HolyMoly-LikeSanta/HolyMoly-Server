package likelion.holymoly.service;

import jakarta.transaction.Transactional;
import likelion.holymoly.dto.LetterDto;
import likelion.holymoly.entity.Letter;
import likelion.holymoly.entity.Member;
import likelion.holymoly.exception.CustomException;
import likelion.holymoly.exception.ErrorCode;
import likelion.holymoly.repository.LetterRepository;
import likelion.holymoly.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final LetterRepository letterRepository;


    public LetterDto createLetterByMemberId(Long memberId, LetterDto letterDto) {
        // memberId로 Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)); // 멤버가 없을 경우 예외 처리

        // 편지 내용 검증
        if (letterDto.getContent() == null || letterDto.getContent().isBlank()) {
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND); // 내용이 비어있을 경우 예외 처리
        }

        // 편지 생성 및 저장
        Letter letter = Letter.builder()
                .member(member)
                .content(letterDto.getContent())
                .authorNickname(letterDto.getAuthorNickname())
                .build();
        letterRepository.save(letter);

        // LetterDto로 변환 후 반환
        return new LetterDto(
                letter.getLetterId(),
                letter.getContent(),
                letter.getAuthorNickname()
        );
    }

    /**
     * 사용자 ID와 편지 ID를 기반으로 편지 삭제
     */
    @Transactional
    public void deleteLetterByMemberId(Long memberId, Long letterId) {
        // memberId로 Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)); // 멤버가 없을 경우 예외 처리

        // letterId로 Letter 조회
        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new CustomException(ErrorCode.LETTER_NOT_FOUND)); // 편지가 없을 경우 예외 처리

        // 편지가 해당 멤버에 속해 있는지 검증
        if (!letter.getMember().equals(member)) {
            throw new CustomException(ErrorCode.LETTER_NOT_BELONG_TO_BOARD); // 편지가 해당 멤버에 속하지 않을 경우 예외
        }

        // 편지 삭제
        letterRepository.delete(letter);
    }

    /**
     * 사용자 ID를 기반으로 모든 편지 조회
     */
    public List<LetterDto> getLettersByMemberId(Long memberId) {
        // memberId로 Member 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)); // 멤버가 없을 경우 예외 처리

        // 멤버의 편지 리스트를 DTO로 변환하여 반환
        return member.getLetters().stream()
                .map(letter -> new LetterDto(
                        letter.getLetterId(),
                        letter.getContent(),
                        letter.getAuthorNickname()))
                .toList();
    }
}
