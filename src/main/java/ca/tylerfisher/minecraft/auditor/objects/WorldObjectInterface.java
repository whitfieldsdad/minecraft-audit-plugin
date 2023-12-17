package ca.tylerfisher.minecraft.auditor.objects;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface WorldObjectInterface {
  public String getObjectId() throws NoSuchAlgorithmException, IOException;
}
