package com.example.controller.auth;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.example.controller.dao.PersonaDao;
import com.example.models.Cuenta;
import com.example.models.enumerator.Rol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JWTManager {

    public static String secretKey = "ElOtorrinoLaringologoDeParangaricutirimicuaroSeQuiereDesotorrinoLaringoparangaricutirimicuarizarYAquelDesotorrinoLaringoparangaricutirimicuadorQueLogreDesottorrinoLaringoParangaricutimiarizarloBuenDesotorrinoLaringoParangaricutirimicuadorSera";

    public static String base64Encode(String src) {
        byte[] bytes = src.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String base64Decode(String src) {
        byte[] bytes = Base64.getDecoder().decode(src);
        return new String(bytes);
    }

    public static String createHeader() {
        String jsonHeader = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        return base64Encode(jsonHeader);
    }

    public static String createPayload(Cuenta cuenta, long expirationMinutes) {
        StringBuilder sb = new StringBuilder();
        Integer sub = cuenta.getId();
        Rol rol;

        try {
            rol = new PersonaDao().getPersonaById(cuenta.getPersonaId()).getRol();
        } catch (Exception e) {
            System.out.println("JWTManager.createPayload() dice:" + e.getMessage());
            rol = Rol.CLIENTE;
        }

        long currentTime = Instant.now().getEpochSecond();
        expirationMinutes = currentTime + (expirationMinutes * 60); 

        sb.append("{").append("\"sub\"").append(":" + sub ).append(",");
        sb.append("\"role\"").append(":\"" + rol +"\"").append(",");
        sb.append("\"iat\"").append(": \"" + currentTime + "\"").append(",");
        sb.append("\"exp\"").append(": \"" + expirationMinutes + "\"").append("}");

        return base64Encode(sb.toString());
    }

    public static String sign(String data) {
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256HMAC.init(secret_key);
            byte[] signedData = sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return base64Encode(new String(signedData));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createToken(Cuenta cuenta, long expirationMinutes) {
        String header = createHeader();
        String payload = createPayload(cuenta, expirationMinutes);
        String data = header + "." + payload;
        String signature = sign(data);
        return data + "." + signature;
    }

    public static HashMap<String,String> decodeTokenHashMap(String jwt) {
        String[] jwtParts = jwt.split("\\.");
        if (jwtParts.length != 3) return null;

        Type mapType = new TypeToken<HashMap<String,String>>(){}.getType();
        
        String header = jwtParts[0];
        HashMap<String,String> h = new Gson().fromJson(base64Decode(header), mapType);
        
        String payload = jwtParts[1];
        HashMap<String,String> p = new Gson().fromJson(base64Decode(payload), mapType);

        HashMap<String,String> tok = new HashMap<>();

        tok.put("alg",h.get("alg"));
        tok.put("typ",h.get("typ"));
        tok.put("sub",p.get("sub"));
        tok.put("role",p.get("role"));
        tok.put("iat",p.get("iat"));
        tok.put("exp",p.get("exp"));

        return tok;
    } 

    public static Boolean isValidToken(String jwt) {
        String[] jwtParts = jwt.split("\\.");
        if (jwtParts.length != 3 || jwtParts == null) return false;

        if (Instant.now().getEpochSecond() > Long.valueOf(decodeTokenHashMap(jwt).get("exp"))) return false;

        return jwtParts[2].equals(sign(jwtParts[0] + "." + jwtParts[1]));
    }

    public static void main(String... args) {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(1);
        cuenta.setPersonaId(4);
        String jwt = createToken(cuenta, 15); 
        System.out.println("token =================================");
        System.out.println(jwt);
        System.out.println(isValidToken(jwt));
    }

}
