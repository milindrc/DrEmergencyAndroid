package com.matrixdev.dremergency.Utils;

/**
 * Created by Milind on 7/2/2018.
 */

public class Constants {



    enum Mode{PRODUCTION,TESTING,LOCAL};

    static Mode mode = Mode.LOCAL;

    public static final String PRODUCTION_URL                                                       = "http://-";
    public static final String TESTING_URL                                                          = "http://ec2-18-191-197-73.us-east-2.compute.amazonaws.com/-/public/index.php";
    public static final String LOCAL_URL                                                            = "http://10.177.12.213/DrEmergency/public/index.php";
    public static final String BASE_URL                                                             = ((mode== Mode.PRODUCTION)?PRODUCTION_URL:"")   +   ((mode== Mode.TESTING)?TESTING_URL:"")  +  ((mode== Mode.LOCAL)?LOCAL_URL:"");
    public static final String URL_LOGIN                                                            = BASE_URL + "/user/login";
    public static final String URL_EDIT_POST                                                        = BASE_URL +"/posts/update" ;
    public static final String URL_REGISTER                                                         = BASE_URL + "/user/register";
    public static final String URL_CARDS                                                            = BASE_URL + "/cards/list";
    public static final String URL_CARDS_ALL                                                        = BASE_URL + "/cards/list/all";
    public static final String URL_ADD_CARD                                                         = BASE_URL + "/cards/add";
    public static final String URL_CREATE_LINK                                                      = BASE_URL + "/cards/link/create";
    public static final String URL_ITEMS                                                            = BASE_URL + "/cards/items/list";
    public static final String URL_ADD_ITEM                                                         = BASE_URL + "/cards/items/add";
    public static final String URL_UPDATE_DEVICE                                                    = BASE_URL + "/user/device/update";
    public static final String URL_REMOVE_ITEM                                                      = BASE_URL + "/cards/items/remove";
    public static final String URL_UPLOAD_CARD_PROFILE                                              = BASE_URL + "/cards/profile/add" ;
    public static final String URL_UPLOAD_CARD_LOGO                                                 = BASE_URL + "/cards/logo/add";
    public static final String URL_UPDATE_ITEM                                                      = BASE_URL + "/cards/items/update";
    public static final String URL_REMOVE_CARD                                                      = BASE_URL + "/cards/remove";
    public static final String URL_GET_USER                                                         = BASE_URL + "/user/get";
    public static final String URL_PROBLEMS                                                         = BASE_URL + "/problems";
    public static final String URL_USER_UPDATE                                                      = BASE_URL + "/user/update";
    public static final String URL_CONFIG                                                           = BASE_URL + "/user/config";
    public static final String URL_RESET_PASS                                                       = BASE_URL + "/user/reset_password";
    public static final String URL_SOS                                                              = BASE_URL + "/request";
    public static final String URL_DOCTOR_REQUESTS                                                  = BASE_URL + "/doctor/requests";
    public static final String URL_PROBLEM_DOCTORS                                                  = BASE_URL + "/problem/doctors";
    public static final String URL_DOCTOR_REQUESTS_HISTORY                                          = BASE_URL + "/doctor/request/history";
    public static final String URL_DOCTOR_REQUEST_ACCEPT                                            = BASE_URL + "/doctor/request/accept";
    public static final String URL_DOCTOR_REQUEST_BLACKLIST                                         = BASE_URL + "/doctor/request/blacklist";
    public static final String URL_DOCTOR_PROBLEMS                                                  = BASE_URL + "/doctor/problems";
    public static final String URL_DOCTOR_PROBLEM_SELECT                                            = BASE_URL + "/doctor/problem/select";
    public static final String URL_DOCTOR_PROBLEMS_REMOVE                                           = BASE_URL + "/doctor/problem/remove";
    public static final String URL_TIPS                                                             = BASE_URL + "/problem/tips";
    public static final String URL_DOC_SOS                                                          = BASE_URL + "/doctor/request";

    public static final String IMAGE_URL_INTENT = "image_intent";
    public static final String INTENT_CROP_IMAGE = "intent_crop";
    public static int SUCCESS_CODE              = 55;
    public static final String SHARE_LINK                                                           = BASE_URL+"/cards/link/";

    public static final String PARAM_USERNAME                                                       = "username";
    public static final String PARAM_NAME                                                           = "name";
    public static final String PARAM_CARD_ID                                                        = "card_id";
    public static final String PARAM_ROLE_ID                                                        = "role_id";
    public static final String PARAM_PASSWORD                                                       = "password";
    public static final String PARAM_ITEM_ID                                                        = "item_id";
    public static final String PARAM_ICON                                                           = "icon";
    public static final String PARAM_EMAIL                                                          = "email";
    public static final String PARAM_PROBLEM_ID                                                     = "problem_id";
    public static final String PARAM_ADDRESS                                                        = "address";
    public static final String PARAM_GENDER                                                         = "gender";
    public static final String PARAM_AGE                                                            = "age";
    public static final String PARAM_LABEL                                                          = "label";
    public static final String PARAM_NOTE                                                           = "note";
    public static final String PARAM_COLOR                                                          = "color";
    public static final String PARAM_VALUE                                                          = "value";
    public static final String PARAM_ROLL                                                           = "roll";
    public static final String PARAM_MOBILE                                                         = "mobile";
    public static final String PARAM_DOCTOR_REQUEST_ID                                              = "doctor_request_id";
    public static final String PARAM_DOCTOR_ID                                                      = "doctor_id";
    public static final String PARAM_LOCATION                                                       = "lat_lng";
    public static final String PARAM_SUBJECT                                                        = "subject" ;
    public static final String PARAM_USER_ID                                                        = "user_id";
    public static final String PARAM_SEMESTER                                                        = "semester";
    public static final String PARAM_BODY                                                           = "body";
    public static final String PARAM_REQUEST_ID                                                     = "request_id";
    public static final String PARAM_TO                                                             = "to";
    public static final String PARAM_DEVICE                                                         = "token";
    public static final String PARAM_DESIGNATION                                                    = "designation";
    public static final String PARAM_MAIL_ID                                                        = "mail_id";
    public static final String PARAM_IMAGE                                                          = "image";
    public static final String PARAM_POST_ID                                                        =  "post_id";
    public static final String PARAM_POST_TEXT                                                      =  "text";
    public static final String PARAM_QUALIFICATIONS                                                 = "qualifications";
    public static final String PARAM_CATEGORY_ID                                                    = "category_id";
    public static final String PARAM_TYPE_ID                                                        = "type_id";

    public static final String PARAM_TEXT                                                           = "text";
    public static final String PARAM_TYPE                                                           = "type" ;
    public static final String PARAM_FILE                                                           = "file" ;

    public static final String INTENT_POST_ID                                                       ="postID" ;
    public static final String INTENT_PROBLEM                                                       = "problem";
    public static final String INTENT_IMAGES                                                        = "images";
    public static final String INTENT_IMAGES_CATEGORY                                               = "images_category";
    public static final String INTENT_USER_DATA                                                     = "user_data";
    public static final String INTENT_BYTE_ARRAY                                                    = "byte_array";
    public static final String INTENT_IMAGE_URI                                                     = "uri";
    public static final String INTENT_CARD                                                          = "intent_card";


    public static final String INPUT_TYPE_PHOTO                                                     = "2";
    public static final String INPUT_TYPE_TEXT                                                      = "1";
    public static final String INPUT_TYPE_VIDEO                                                     = "3";
    public static final String INTENT_MAIL_CONTENT                                                  = "Mailcontent";
    public static final String INTENT_OUTBOX_NAMES                                                  = "outboxnames";
    public static final String INTENT_MAIL_SEARCH                                                   = "Searchlist";
    public static final String INTENT_SENDER                                                        = "sender";
    public static final String INTENT_SUBJECT                                                       = "replysubject";
    public static final String INTENT_HOME_TARGET                                                   = "target";
    public static final String INTENT_DASHBOARD_TARGET                                              = "target";
    public static final String BIO_STATUS_UPDATE                                                    = "bio_status" ;
    public static final String BIO                                                                  = "bio" ;
    public static final String STATUS                                                               = "status" ;

    public static final int MAIL_ITEM_BODY_LENGTH                                                   = 35;

    public static final String COLOR_DEFAULT                                                        = "#b4d5fa";
}
