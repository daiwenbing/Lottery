package utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Json格式数据的工具类
 *
 * @author dwb
 * @version 1.0
 * @since BBF v3.0
 */
public class JsonUtil {
    private static ObjectMapper objectMapper;
    //  public static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    // JSON输出
    private static final Gson gson;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 将json格式转换为指定类型格式 eg：fromJson("{"aa":"2015","bb":"01"}", Map.class)
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * 获取Jackson的ObjectMapper对象，此对象初始化代价比较高，通过此方式共享此对象以提高效率。
     *
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static <T> T readValue(String resp, Class<T> classType)
            throws Exception {
        try {
            return JsonUtil.getObjectMapper().readValue(resp, classType);
        } catch (Exception e) {
            throw e;
        }
    }

    public static <T> T readValueIgnoreProperty(String resp, Class<T> classType)
            throws Exception {
        try {
            ObjectMapper objmap = JsonUtil.getObjectMapper();
            objmap.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            objmap.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
            objmap.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            objmap.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
            return objmap.readValue(resp, classType);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 将对象（map,set,list..）转换为Json格式 eg：JsonUtil.toJson(map集合)
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static String toJsonObject(Object o) throws IOException {
        String ret = "";
        ObjectMapper objectMapper = new ObjectMapper();
        ret = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(o);
        return ret;
    }

    public static String Object2String(Object obj) {
        String result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            //logger.error("json转换异常", e);
        }
        return result;
    }

    public static <T> T String2Object(String json, Class<T> classObj)
            throws Exception {
        try {
            return objectMapper.readValue(json, classObj);
        } catch (Exception e) {
            //logger.error("json转换异常", e);
            throw e;
        }
    }
    /**
     * 字符串转换成较复杂的对象
     *
     * @param json
     * @return
     */
/*    @SuppressWarnings("unchecked")
    public static <T> T String2Object(String json) throws Exception {
        try {
            return (T) objectMapper.readValue(json, getCollectionType(classObj, valueType));
        } catch (Exception e) {
            //logger.error("json转换异常", e);
            throw e;
        }
    }*/

    /**
     * 字符串转换为List<T>
     *
     * @param json
     * @param classObj
     * @return
     */
    public static <T> List<T> String2List(String json, Class<T> classObj) {
        List<T> result = null;
        try {
            result = objectMapper.readValue(json,
                    getCollectionType(ArrayList.class, classObj));
        } catch (Exception e) {
        }
        return result;
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(
                collectionClass, elementClasses);
    }
}
