# PBH-RSAKeyGenerator

PBH Plus 许可证创建工具

## 使用

1. 将私钥和公钥分别命名为 `private_key.pem` 和 `public_key.pem` 放在工作目录下。如果没有，则需要使用 `RSAUtils#genKeyPair` 方法创建一个
2. 调用 `genKey` 方法，传入许可证相关信息，接收返回值，返回的字符串即为许可证密钥

## RSA 证书覆盖

如果需要使用临时的密钥组进行测试，则必须更新 PBH 内置的公钥。一个简单的方法是将公钥文件重命名为 `REPLACEMENT_PBH_PUBLIC_KEY.pem` 放置在 `data` 目录下，PBH 会优先使用此公钥文件中的公钥。
