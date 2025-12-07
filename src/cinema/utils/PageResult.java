package cinema.utils;

public class PageResult {
    public static enum Navigation {
        BACK_TO_START,
        BACK_TO_MAIN,
        BACK_TO_PREVIOUS,
        BACK_TO_EXIT,
        NEXT
    }

    public static abstract class DataType {
        private final PageResult pageResult;
        private final String prompt;

        DataType(PageResult pageResult, String prompt) {
            this.pageResult = pageResult;
            this.prompt = prompt;
        }

        public PageResult getPageResult() { return this.pageResult; }
        public String getPrompt() { return this.prompt; }

        @Override 
        public abstract String toString();
    }

    public static class Char extends DataType {
        private final char value;

        Char(char value, String prompt) {
            super(null, prompt);
            this.value = value;
        }

        Char(PageResult pageResult, String prompt) {
            super(pageResult, prompt);
            this.value = 0;
        }

        public char getValue() { return value; }

        @Override 
        public String toString() { return String.valueOf(this.value); }
    }

    public static class Int extends DataType {
        private final int value;

        Int(int value, String prompt) {
            super(null, prompt);
            this.value = value;
        }

        Int(PageResult pageResult, String prompt) {
            super(pageResult, prompt);
            this.value = 0;
        }

        public int getValue() { return value; }

        @Override 
        public String toString() { return String.valueOf(this.value); }
    }

    public static class Str extends DataType {
        private final String value;

        Str(String value, String prompt) {
            super(null, prompt);
            this.value = value;
        }

        Str(PageResult pageResult, String prompt) {
            super(pageResult, prompt);
            this.value = "";
        }

        public String getValue() { return value; }

        @Override 
        public String toString() { return this.value; }
    }



    private final Navigation direction;
    private final PageType nextPage;

    private PageResult(Navigation direction, PageType nextPage) {
        if (direction != Navigation.NEXT && nextPage != null) {
            throw new IllegalArgumentException("Only NEXT direction can have a nextPage");
        }

        if (direction == Navigation.NEXT && nextPage == null) {
            throw new IllegalArgumentException("NEXT direction must have a target screen");
        }
        this.direction = direction;
        this.nextPage = nextPage;
    }

    public Navigation getDirection() { return this.direction; }

    public PageType getNextPage() { return this.nextPage; }

    /**
     * Create a PageResult to navigate to the next page.
     * 
     * @param nextPage the page to navigate to
     * @return a PageResult with the NEXT navigation direction and the specified page
     */
    public static PageResult createResultNextPage(PageType nextPage) {
        return new PageResult(Navigation.NEXT, nextPage);
    }

    /**
     * Create a PageResult to navigate to a previous or start page.
     * 
     * @param direction the navigation direction to use (BACK or START)
     * @return a PageResult with the specified navigation direction and no target page
     * @throws IllegalArgumentException if the navigation direction is NEXT
     */
    public static PageResult createResultJump(Navigation direction) {
        if (direction == Navigation.NEXT) {
            throw new IllegalArgumentException("BACK directions cannot be NEXT");
        }
        return new PageResult(direction, null);
    }
}
