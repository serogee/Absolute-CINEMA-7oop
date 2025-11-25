package cinema.utils;

public class CustomOption extends Option {

    private String token;

    public CustomOption(PageResult.Navigation direction, String label, String token) {
        super(direction, label);
        this.token = token.toUpperCase();
    }

    public CustomOption(PageType page, String label, String token) {
        super(page, label);
        this.token = token.toUpperCase();
    } 

    public String getToken() { return this.token; }

}
