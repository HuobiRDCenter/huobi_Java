package com.huobi.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

import com.huobi.exception.SDKException;

public abstract class InternalUtils {

  public static byte[] decode(byte[] data) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(data);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    decompress(bais, baos);
    baos.flush();
    baos.close();
    bais.close();
    return baos.toByteArray();
  }

  private static void decompress(InputStream is, OutputStream os) throws IOException {
    GZIPInputStream gis = new GZIPInputStream(is);
    int count;
    byte[] data = new byte[1024];
    while ((count = gis.read(data, 0, 1024)) != -1) {
      os.write(data, 0, count);
    }
    gis.close();
  }

  public static void await(long n) throws SDKException {
    try {
      Thread.sleep(n);
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new SDKException(SDKException.SYS_ERROR, "Error when sleep");
    }
  }
}
