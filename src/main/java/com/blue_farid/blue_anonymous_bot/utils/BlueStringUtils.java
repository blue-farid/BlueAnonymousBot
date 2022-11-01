package com.blue_farid.blue_anonymous_bot.utils;

public class BlueStringUtils {

    /**
     * returns the initial message after running the bot.
     *
     * @param version the version of the bot.
     */
    public static String initMessage(String version) {
        return "- the bot is running!\n".concat("- Version: ".concat(version.concat("\n")).concat("- OS: ".concat(CommonUtils.getOsName())));
    }

    public static String asciiBlueAnonymousBot() {
        return " ____  _                 _                                                        ____   " +
                "     _   \n" + "| __ )| |_   _  ___     / \\   _ __   ___  _ __  _   _ _ __ ___   ___  _   _ ___  | " +
                "__ )  ___ | |_ \n" + "|  _ \\| | | | |/ _ \\   / _ \\ | '_ \\ / _ \\| '_ \\| | | | '_ ` _ \\ / _ \\| | | / __| |" +
                "  _ \\ / _ \\| __|\n" + "| |_) | | |_| |  __/  / ___ \\| | | | (_) | | | | |_| | | | | | | (_) | |_| \\__ \\ | |_) | (_) " +
                "| |_ \n" + "|____/|_|\\__,_|\\___| /_/   \\_\\_| |_|\\___/|_| |_|\\__, |_| |_| |_|\\___/ \\__,_|___/ |____/ \\___/ \\__|\n" + "                                 " +
                "               |___/     ";
    }

    public static String asciiLogo() {
        return "               .:lodoc:.               " +
                "           \n" + "             cOKKKKKKOxKk;                   " +
                "     \n" + "            xKKKKKKKKKdkKKk'                      \n" + "         " +
                "  :KKKKKKKKKK0OKKK0,                     \n" + "           OKKKKKKKKKKKKKKKK0'               " +
                "     \n" + "          'KKKKKKKKKKKKKKKKKK0'                   \n" + "          lKKKKKOxoxO:c.ldx;dxk.       " +
                "           \n" + "          dKKK0d..x,',  l' :KKKo                  \n" + "          xKKKk .; ,;;cll;;0KKd.                  \n" + "   " +
                "       oKKK0l:   .''','kK0;                    \n" + "          .0KKo:oo,.  ;k.kKKl                     \n" + "     " +
                "    .'dkOk' .c;;d:00OK0:x:ll;..              \n" + "        c00d0kxkk, ,x. xc00cldddddddoood:c        \n" + "   " +
                "    .0KK:0KKK0x. :. .00x0KKKKKKK00KKKO;c.      \n" + "       lKKK:kKKKKKO. . dxKK0'.....''..x:,,:o      \n" + "      " +
                ".0K0k,dKKKKKKk: 'c,KK0  .  ...  :c'':d      \n" + "      dKkoxkclxdOKKc.lc .0K0       ..  k00x       \n" + "   " +
                "   dKKKo,cl::OKKo  '  xK0       .   kKKd       \n" + "       xlc00OOl,lKKo  ,  :K0  .  ..    xKKc       \n" + "       " +
                ".:dl;.'d00KKl  c. .00.          xOK.       \n" + "         ,dccl..:okc      dK0kkxxddooloOoO        \n" + "                     " +
                "     .''',;:codxO0:Ol        \n" + "                                       .;   ";
    }
}
