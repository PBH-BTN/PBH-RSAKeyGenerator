import com.google.gson.Gson;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        String privateKey = Files.readString(new File("private_key.pem").toPath());
        String publicKey = Files.readString(new File("public_key.pem").toPath());
        for (int i = 0; i < 1000; i++) {
            System.out.println(genKey("爱发电", "爱发电用户", null, privateKey));
        }
    }

    /**
     * 生成 Key
     * @param source 许可证来源
     * @param licenseTo 许可人
     * @param description 许可证描述
     * @param privateKey 私钥
     * @return Key
     * @throws Exception 加密异常
     */
    private static String genKey(String source, String licenseTo, String description, String privateKey) throws Exception {
        var key = new KeyData("PeerBanHelper",
                source,
                licenseTo,
                System.currentTimeMillis(),
                LocalDateTime.now().plusYears(100).atOffset(ZoneOffset.UTC).toInstant().toEpochMilli(),
                description, UUID.randomUUID().toString());
        var encrypted = (RSAUtils.encryptByPrivateKey(new Gson().toJson(key).getBytes(StandardCharsets.UTF_8), privateKey));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public record KeyData(
            // verifyMagic 应固定为 PeerBanHelper
            String verifyMagic,
            // source 为来源
            String source,
            // 授权给（用户名），爱发电的话大概都是 “爱发电用户” 固定的
            String licenseTo,
            // Key 创建时间
            Long createAt,
            // Key 过期时间，通常是 100 年以后
            Long expireAt,
            // 许可证描述
            String description,
            // 隐藏字段，主要是为了改变 KEY，PBH 并不关心这个字段
            String hidden
    ) {
    }
}
