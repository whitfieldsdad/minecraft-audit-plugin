package ca.tylerfisher.minecraft.auditor;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import org.erdtman.jcs.JsonCanonicalizer;

import com.google.gson.Gson;

public class UUID5 {
  public static UUID fromString(UUID namespace, String name) throws NoSuchAlgorithmException {
    return fromBytes(namespace, name.getBytes());
  }

  public static UUID fromBytes(UUID namespace, byte[] name) throws NoSuchAlgorithmException {
    MessageDigest md;
    md = MessageDigest.getInstance("SHA-1");
    md.update(uuidToBytes(namespace));
    md.update(name);

    byte[] b = md.digest();
    b[6] &= 0x0f; /* clear version */
    b[6] |= 0x50; /* set to version 5 */
    b[8] &= 0x3f; /* clear variant */
    b[8] |= 0x80; /* set to IETF variant */

    long msb = 0;
    long lsb = 0;
    assert b.length >= 16;
    for (int i = 0; i < 8; i++)
      msb = (msb << 8) | (b[i] & 0xff);
    for (int i = 8; i < 16; i++)
      lsb = (lsb << 8) | (b[i] & 0xff);
    return new UUID(msb, lsb);
  }

  public static UUID fromMap(UUID namespace, Map<String, Object> map) throws NoSuchAlgorithmException, IOException {
    Gson gson = new Gson();
    String json = gson.toJson(map);

    JsonCanonicalizer jc = new JsonCanonicalizer(json);
    String canonicalJson = jc.getEncodedString();

    return fromString(namespace, canonicalJson);
  }

  private static byte[] uuidToBytes(UUID uuid) {
    byte[] out = new byte[16];
    long msb = uuid.getMostSignificantBits();
    long lsb = uuid.getLeastSignificantBits();
    for (int i = 0; i < 8; i++)
      out[i] = (byte) ((msb >> ((7 - i) * 8)) & 0xff);
    for (int i = 8; i < 16; i++)
      out[i] = (byte) ((lsb >> ((15 - i) * 8)) & 0xff);
    return out;
  }
}