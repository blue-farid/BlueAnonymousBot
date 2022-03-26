package properties;

/**
 * Commands enum
 * @author Negar Anabestani
 */
public enum Commands {
    CANCEL("cancel"),
    START("start"),
    RESTART("restart"),
    ANONYMOUS_CONNECTION("anonymous_connection"),
    ANSWER("answer"),
    BLOCK("block"),
    LINK("link"),
    SPECIFIC_CONNECTION("specific_connection"),
    ANONYMOUS_LINK("anonymous_link"),
    ANONYMOUS_TO_GROUP("anonymous_to_group"),
    HELP("help"),
    SCORE("score"),
    CHOOSE_CONTACT_SEX_FEMALE("choose_contact_sex_female"),
    CHOOSE_CONTACT_SEX_MALE("choose_contact_sex_male"),
    CHOOSE_CONTACT_SEX_BI("choose_contact_sex_bi"),
    HELP_WHAT_FOR("help.what_for"),
    HELP_RANDOM_ANONYMOUS("help.connect_random_anonymous"),
    HELP_FREE_VIP("help.free_vip"),
    HELP_SPECIFIC_CONNECTION("help.specific_connection"),
    HELP_RECEIVE_ANONYMOUS_MESSAGE("help.receive_anonymous_message"),
    HELP_ANONYMOUS_TO_GROUP("help.send_anonymous_message_group"),
    PRINT_ALL_USERS("print_all_users"),
    GET_DATABASE("get_database"),
    HELP_BACK_MAIN_MENU("back_help_main_menu");
    private final String key;
    Commands(String key){
        this.key=key;
    }
    public String get(){
       return Property.COMMANDS_P.get(key);
    }

}
