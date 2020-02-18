package pl.lodz.p.it.vehiclerental;

public class StringUtils {

    public static boolean containsIgnoreCase(String base, String filter) {
        return base.toLowerCase().contains(filter.toLowerCase());
    }
}
