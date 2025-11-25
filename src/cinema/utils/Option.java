package cinema.utils;

public class Option {
    private PageResult pageResult;
    private String label;

    public Option(PageResult.Navigation direction, String label) {
        this.pageResult = PageResult.createResultJump(direction);
        this.label = label;
    }

    public Option(PageType page, String label) {
        this.pageResult = PageResult.createResultNextPage(page);
        this.label = label;
    } 

    public PageResult getPageResult() { return this.pageResult; }

    public String getLabel() { return this.label; }
}
