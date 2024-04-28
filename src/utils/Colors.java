package utils;

public enum Colors {
    //Color end string, color reset
    RESET("\033[0m", null),
    RED("\033[1;91m", "красный"),      // RED
    GREEN("\033[1;92m", "зеленый"),    // GREEN
    YELLOW("\033[1;93m", "желтый"),   // YELLOW
    BLUE("\033[1;94m", "синий"),     // BLUE
    MAGENTA("\033[1;95m", null),  // MAGENTA
//    CYAN_BOLD_BRIGHT("\033[1;96m"),     // CYAN
    WHITE("\033[1;97m", null),   // WHITE
    WHITE_BACKGROUND("\033[0;107m", null);     // WHITE

    public final String code;

    public final String label;

    Colors(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String toString() {
        return code;
    }

    public String colorize(String str){
        return code + str + Colors.RESET;
    }

    public String colorize(char c){
        return code + c + Colors.RESET;
    }


}
