package backend;

import java.util.HashMap;

/**
 * Created by DSingh1 on 5/2/2016.
 */
public class Global_Param   {

    private static HashMap<String,String> params = new HashMap<>();

    public static void put(String key, String value){

        params.put(key,value);

    }

    public static String get( String key ){
        return params.get(key);
    }


}
