package utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class APIParsing {

    static String result;
    static int Count = 0;
    static String ptr = null;
    public static void changeJSONTagValue(String path, String tag, String position, String var) {
        try {
            String body = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject json = createJSONObject(body);
            Count = 0;
            replaceKeyInJSONObject(json, tag, var, position);
            PrintWriter pw = new PrintWriter(path);
            pw.write(String.valueOf(json));
            pw.flush();
            pw.close();
        } catch (Exception e) {
            PathAndVariable.error = "Exception while changing json tag value for api";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void replaceKeyInJSONObject(JSONObject jsonObject, String jsonKey,
                                              String jsonValue, String position) {
        int pos = Integer.parseInt(position);
        for (Object key : jsonObject.keySet()) {
            if (key.equals(jsonKey) && ((jsonObject.get(key) instanceof String) || (jsonObject.get(key) instanceof Number) || (jsonObject.get(key) instanceof Boolean) || (jsonObject.get(key) instanceof List) || (jsonObject.get(key) instanceof JSONObject ) ||  jsonObject.get(key) == null)) {
                if (jsonValue.equalsIgnoreCase("true") || jsonValue.equalsIgnoreCase("false")) {
                    Boolean jsonBooleanValue = Boolean.parseBoolean(jsonValue);
                    Count = Count + 1;
                    if (pos == Count) {
                        jsonObject.put(key, jsonBooleanValue);
                        PathAndVariable.flag = "Yes";
                        Browser.generate_logs("INFO", "JSON tag " + key + " changed with value " + jsonBooleanValue + " at position " + position, "");
                    }
                } else if (jsonValue.equalsIgnoreCase("null")) {
                    Count = Count + 1;
                    if (pos == Count) {
                        jsonObject.put(key, null);
                        PathAndVariable.flag = "Yes";
                        Browser.generate_logs("INFO", "JSON tag " + key + " changed as null at position " + position, "");
                    }
                } else {
                    Count = Count + 1;
                    if (pos == Count) {
                        jsonObject.put(key, jsonValue);
                        PathAndVariable.flag = "Yes";
                        Browser.generate_logs("INFO", "JSON tag " + key + " changed with value " + jsonValue + " at position " + position, "");
                    }
                }
            } else if (jsonObject.get(key) instanceof JSONObject) {
                JSONObject modifiedJsonobject = (JSONObject) jsonObject.get(key);
                if (modifiedJsonobject != null) {
                    replaceKeyInJSONObject(modifiedJsonobject, jsonKey, jsonValue, position);
                }
            } else if (jsonObject.get(key) instanceof List) {
                JSONObject modifiedJsonobject = null;
                Number field1 = null;
                String field2 = null;
                for (int i = 0; i < ((List<?>) jsonObject.get(key)).size(); i++) {
                    if (((List<?>) jsonObject.get(key)).get(i) instanceof Long || ((List<?>) jsonObject.get(key)).get(i) instanceof Number) {
                        field1 = ((Number) ((List<?>) jsonObject.get(key)).get(i));
                    } else if (((List<?>) jsonObject.get(key)).get(i) instanceof String) {
                        field2 = (String) ((List<?>) jsonObject.get(key)).get(i);
                    } else {
                        modifiedJsonobject = (JSONObject) ((List<?>) jsonObject.get(key)).get(i);
                    }
                    if (modifiedJsonobject instanceof JSONObject || field1 instanceof Number || field2 instanceof String) {
                        if (modifiedJsonobject != null) {
                            replaceKeyInJSONObject(modifiedJsonobject, jsonKey, jsonValue, position);
                        }
                    } else {
                        replaceKeyInJSONObject(modifiedJsonobject, jsonKey, jsonValue, position);
                    }
                }
            }
        }
    }

    private static void findKeyInJSONObject(JSONObject jsonObject, String jsonKey, String position) {
        int pos = Integer.parseInt(position);
        for (Object key : jsonObject.keySet()) {
            if (key.equals(jsonKey) && ((jsonObject.get(key) instanceof String) || (jsonObject.get(key) instanceof List) || (jsonObject.get(key) instanceof Boolean) || (jsonObject.get(key) instanceof Number) || jsonObject.get(key) == null)) {
                Count = Count + 1;
                if (pos == Count) {
                    PathAndVariable.flag = "Yes";
                    try {
                        PathAndVariable.response_value = jsonObject.get(key).toString();
                    } catch (Exception e) {
                        PathAndVariable.response_value = "null";
                    }
                    PathAndVariable.response_value = PathAndVariable.response_value.replace("[", "");
                    PathAndVariable.response_value = PathAndVariable.response_value.replace("]", "");
                    PathAndVariable.response_value = PathAndVariable.response_value.replace("\"", "");
                    Browser.generate_logs("INFO", "JSON tag " + key + " found with value " + PathAndVariable.response_value + " at position " + position, "");
                    break;
                }
            } else if (jsonObject.get(key) instanceof JSONObject) {
                JSONObject modifiedJsonobject = (JSONObject) jsonObject.get(key);
                if (modifiedJsonobject != null) {
                    findKeyInJSONObject(modifiedJsonobject, jsonKey, position);
                }
            } else if (jsonObject.get(key) instanceof List) {
                JSONObject modifiedJsonobject = null;
                Number field1 = null;
                String field2 = null;
                List results = null;
                for (int i = 0; i < ((List<?>) jsonObject.get(key)).size(); i++) {
                    if (((List<?>) jsonObject.get(key)).get(i) instanceof Long || ((List<?>) jsonObject.get(key)).get(i) instanceof Number) {
                        field1 = (Number) ((List<?>) jsonObject.get(key)).get(i);
                    } else if (((List<?>) jsonObject.get(key)).get(i) instanceof String) {
                        field2 = (String) ((List<?>) jsonObject.get(key)).get(i);
                    } else if (((List<?>) jsonObject.get(key)).get(i) instanceof List) {
                        results = (List) ((List<?>) jsonObject.get(key)).get(i);
                    } else {
                        modifiedJsonobject = (JSONObject) ((List<?>) jsonObject.get(key)).get(i);
                    }
                    if (modifiedJsonobject instanceof JSONObject || field1 instanceof Number || field2 instanceof String || results instanceof List) {
                        if (modifiedJsonobject != null) {
                            findKeyInJSONObject(modifiedJsonobject, jsonKey, position);
                        }
                    } else {
                        findKeyInJSONObject(modifiedJsonobject, jsonKey, position);
                    }
                }
            }
        }
    }

    private static JSONObject createJSONObject(String jsonString) {
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        if ((jsonString != null) && !(jsonString.isEmpty())) {
            try {
                jsonObject = (JSONObject) jsonParser.parse(jsonString);
            } catch (Exception e) {
                PathAndVariable.error = "Exception while creating json object for api";
                Browser.generate_error(PathAndVariable.error+e);
            }
        }
        return jsonObject;
    }

    public static String getJSONTagValue(String tag, String position) {
        try {
            ptr = "";
            String body = PathAndVariable.message.toString();
            System.out.println(body);
            PathAndVariable.response_value = "No";
            JSONObject json = createJSONObject(body);
            Count = 0;
            findKeyInJSONObject(json, tag, position);
            return PathAndVariable.response_value;
        } catch (Exception ex) {
            PathAndVariable.error = "Exception while getting tag value from json api";
            Browser.generate_error(PathAndVariable.error);
        }
        return "";
    }
}