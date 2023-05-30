package crud.board.BoardAuthority.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SearchDto {
    private String searchWord;
    private String searchOption;

    public SearchDto(String searchWord, String searchOption) {
        this.searchWord = checkNull(searchWord);
        this.searchOption = checkNull(searchOption);
    }

    private String checkNull(String s){
        if (s == null){
            return "";
        }
        return s;
    }
}
