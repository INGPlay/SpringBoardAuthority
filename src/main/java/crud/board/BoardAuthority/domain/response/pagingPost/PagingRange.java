package crud.board.BoardAuthority.domain.response.pagingPost;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class PagingRange<T> {
    private Page<T> page;

    private final int realPageNum;
    private final int currentPageNum;

    private final int displayNum;

    private final int startNum;
    private final int endNum;

    private final List<Integer> pageNumList;

    private final boolean isPrevious;
    private final boolean isNext;

    private final int previousNum;
    private final int nextNum;

    public PagingRange(Page<T> page, int displayNum) {

        this.page = page;

        this.realPageNum = page.getNumber();
        this.currentPageNum = page.getNumber() + 1;
        this.displayNum = displayNum;

        this.endNum = (int) (Math.ceil(currentPageNum / (double) displayNum) * displayNum);
        this.startNum = (endNum - displayNum) + 1;

        this.pageNumList = IntStream.rangeClosed(getStartNum(), getEndNum()).boxed().collect(Collectors.toList());

        this.isPrevious = hasPrevious();
        this.isNext = hasNext();

        this.previousNum = currentPageNum - displayNum;
        this.nextNum = currentPageNum + displayNum;
    }

    public int getPreviousNum(){
        if (this.previousNum < 1){
            return 1;
        }

        return this.previousNum;
    }

    public int getNextNum(){
        if (this.nextNum >= page.getTotalPages()){
            return page.getTotalPages();
        }

        return this.nextNum;
    }

    private boolean hasPrevious(){
        if (startNum <= 1){
            return false;
        }
        return true;
    }

    private boolean hasNext(){
        if (endNum > page.getTotalPages()) {
            return false;
        }
        return true;
    }

    public int getStartNum() {
        return startNum;
    }

    public int getEndNum(){
        if (this.endNum >= page.getTotalPages()){
            return page.getTotalPages();
        }
        return endNum;
    }
}
