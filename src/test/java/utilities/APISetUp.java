package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class APISetUp {

    public static void createURL(String url) {
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.prop_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            PathAndVariable.url = PathAndVariable.prop.getProperty(url);
//            PathAndVariable.endpoint = PathAndVariable.url;
            Browser.generate_logs("INFO", "API URL = <<  " + PathAndVariable.url, "");
        } catch (Exception e) {
            PathAndVariable.error = "Exception while getting url for api";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void addService(String service, String value) {
        try {
            if (PathAndVariable.env.toLowerCase().contains("flex")) {
                PathAndVariable.fr = new FileReader(PathAndVariable.path + "/properties/Flex_Service.properties");
            }
            PathAndVariable.prop.load(PathAndVariable.fr);
            if (PathAndVariable.prop.getProperty(service).contains("$") && !value.isEmpty()) {
                int len = value.split(",").length;
                String[] arr = new String[len];
                if (value.contains(",")) {
                    arr = value.split(",");
                } else {
                    arr[0] = value;
                }
                for (String s : arr) {
                    if (s.equals("account")) {
                        String user = System.getProperty("acct-id");
                        if (PathAndVariable.endpoint.isEmpty()) {
                            PathAndVariable.service = PathAndVariable.prop.getProperty(service).replace("$account", user);
                            PathAndVariable.endpoint = PathAndVariable.url + PathAndVariable.prop.getProperty(service).replace("$account", user);
                        } else {
                            PathAndVariable.service = PathAndVariable.service.replace("$account", user);
                            PathAndVariable.endpoint = PathAndVariable.endpoint.replace("$account", user);
                        }
                    } else {
                        String fetch_data = Elements.getVariable(s);
                        if (fetch_data != null) {
                            if (PathAndVariable.endpoint.isEmpty()) {
                                PathAndVariable.service = PathAndVariable.prop.getProperty(service).replace("$" + s, fetch_data);
                                PathAndVariable.endpoint = PathAndVariable.url + PathAndVariable.prop.getProperty(service).replace("$" + s, fetch_data);
                            } else {
                                PathAndVariable.service = PathAndVariable.service.replace("$" + s, fetch_data);
                                PathAndVariable.endpoint = PathAndVariable.endpoint.replace("$" + s, fetch_data);
                            }
                        } else {
                            PathAndVariable.fr = new FileReader(PathAndVariable.prop_dir);
                            PathAndVariable.prop.load(PathAndVariable.fr);
                            if (PathAndVariable.endpoint.isEmpty()) {
                                PathAndVariable.service = PathAndVariable.prop.getProperty(service).replace("$" + s, PathAndVariable.prop.getProperty(s));
                                PathAndVariable.endpoint = PathAndVariable.url + PathAndVariable.prop.getProperty(service).replace("$" + s, PathAndVariable.prop.getProperty(s));
                            } else {
                                PathAndVariable.service = PathAndVariable.service.replace("$" + s, PathAndVariable.prop.getProperty(s));
                                PathAndVariable.endpoint = PathAndVariable.endpoint.replace("$" + s, PathAndVariable.prop.getProperty(s));
                            }
                        }
                    }
                }
            } else {
                PathAndVariable.endpoint = PathAndVariable.endpoint + PathAndVariable.prop.getProperty(service);
            }
            Browser.generate_logs("INFO", "API Service = <<  " + PathAndVariable.service, "");
            Browser.generate_logs("INFO", "Complete endpoint = <<  " + PathAndVariable.endpoint, "");
        } catch (Exception e) {
            PathAndVariable.error = "Exception while getting service for api";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void getCredentials(String user) {
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.prop_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            PathAndVariable.basic_auth = PathAndVariable.prop.getProperty(user);
            Browser.generate_logs("INFO", "API Authentication = <<  " + PathAndVariable.basic_auth, "");
        } catch (Exception e) {
            PathAndVariable.error = "Exception while getting credentials for api";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static RequestSpecification request_spec(String type, String auth) {
        if (type.contains("post") || type.contains("put")) {
            return new RequestSpecBuilder().
                    setBaseUri(PathAndVariable.url).
                    addHeader("Authorization", auth).
                    setContentType(ContentType.fromContentType("application/x-www-form-urlencoded")).
                    log(LogDetail.ALL).
                    build();
        } else if (type.contains("get")) {
            return new RequestSpecBuilder().
                    setBaseUri(PathAndVariable.url).
                    addHeader("Authorization", PathAndVariable.basic_auth).
//                    setContentType(ContentType.fromContentType("application/x-www-form-urlencoded")).
        log(LogDetail.ALL).
                    build();
        } else {
            return null;
        }
    }

    public static void getUserPassCredentials(String user, String pass) {
        try {
            if (user.equals("username") && pass.equals("password")) {
                PathAndVariable.api_username = System.getProperty("acct-id");
                PathAndVariable.api_pwd = System.getProperty("token");
                String encoding = Base64.getEncoder().encodeToString((PathAndVariable.api_username + ":" + PathAndVariable.api_pwd).getBytes());
                PathAndVariable.basic_auth = "Basic " + encoding;
                Browser.generate_logs("INFO", "API Authentication = <<  " + PathAndVariable.basic_auth, "");
            } else {
                PathAndVariable.fr = new FileReader(PathAndVariable.prop_dir);
                PathAndVariable.prop.load(PathAndVariable.fr);
                PathAndVariable.api_username = PathAndVariable.prop.getProperty(user);
                if (PathAndVariable.encryptedPin != null) {
                    PathAndVariable.api_pwd = PathAndVariable.encryptedPin;
                } else {
                    PathAndVariable.api_pwd = PathAndVariable.prop.getProperty(pass);
                }
            }
            Browser.generate_logs("INFO", "API Username and password = <<  " + PathAndVariable.api_username + " and " + PathAndVariable.api_pwd, "");
        } catch (Exception e) {
            PathAndVariable.error = "Exception while getting username and password for api";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void getHeaderValues(String header_name, String header_value) {
        try {
            PathAndVariable.fr = new FileReader(PathAndVariable.prop_dir);
            PathAndVariable.prop.load(PathAndVariable.fr);
            PathAndVariable.api_value = PathAndVariable.prop.getProperty(header_value);
            Browser.generate_logs("INFO", "API Header and Value = <<  " + header_name + " and " + PathAndVariable.api_value, "");
        } catch (Exception e) {
            PathAndVariable.error = "Exception while setting headers for api";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void getBody(String body, String type) {
        try {
            if (type.equalsIgnoreCase("json")) {
                System.out.println("json");
            } else {
                System.out.println("xml");
            }
            PathAndVariable.body = new String(Files.readAllBytes(Paths.get(PathAndVariable.api_path)));
        } catch (Exception e) {
            PathAndVariable.error = "Exception while getting json for api";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void getParams(String param, String var) {
        try {
            if (PathAndVariable.params == null) {
                PathAndVariable.params = "?" + param + "=" + var;
            } else {
                PathAndVariable.params = PathAndVariable.params + "&" + param + "=" + var;
            }
        } catch (Exception e) {
            PathAndVariable.error = "Exception while setting query values for api";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static void getBodyParams(String param, String var) {
        try {
            if (PathAndVariable.params == null) {
                PathAndVariable.params = param + "=" + var;
            } else {
                PathAndVariable.params = PathAndVariable.params + "&" + param + "=" + var;
            }
            PathAndVariable.form_params.put(param, var);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while setting query values for api";
            Browser.generate_error(PathAndVariable.error);
        }
    }

    public static JSONObject getAPISchema(String body, String type) {
        JSONObject jsonSchema = null;
        try {
            if (type.equalsIgnoreCase("json")) {
                if (PathAndVariable.env.toLowerCase().contains("mint")) {
                    PathAndVariable.api_path = PathAndVariable.path + "/API/JSON/JSON_Mint/" + body + ".json";
                }
            }
            File file = new File(PathAndVariable.api_path);
            JSONTokener jsonFile = new JSONTokener(Files.newInputStream(file.toPath()));
            jsonSchema = new JSONObject(jsonFile);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while getting json schema for api";
            Browser.generate_error(PathAndVariable.error);
        }
        return jsonSchema;
    }

    public static JSONObject getAPIResponse(String response) {
        JSONObject jsonResponseSchema = null;
        try {
            JSONTokener jsonResponse = new JSONTokener(response);
            jsonResponseSchema = new JSONObject(jsonResponse);
        } catch (Exception e) {
            PathAndVariable.error = "Exception while getting json response for api";
            Browser.generate_error(PathAndVariable.error);
        }
        return jsonResponseSchema;
    }
}
