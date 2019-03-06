package com.huobi.client.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.huobi.client.impl.utils.JsonWrapper;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import okhttp3.Request;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public class MockPostQuerier implements BufferedSink {

  public JsonWrapper jsonWrapper;

  public MockPostQuerier(Request request) {
    assertNotNull(request.body());
    try {
      request.body().writeTo(this);
    } catch (Exception e) {
      fail("MockPostQuerier fail");
    }
  }

  @Override
  public Buffer buffer() {
    return null;
  }

  @Override
  public BufferedSink write(ByteString byteString) throws IOException {
    return null;
  }

  @Override
  public BufferedSink write(byte[] source) throws IOException {
    return null;
  }

  @Override
  public BufferedSink write(byte[] source, int offset, int byteCount) throws IOException {
    jsonWrapper = JsonWrapper.parseFromString(new String(source));
    return null;
  }

  @Override
  public long writeAll(Source source) throws IOException {
    return 0;
  }

  @Override
  public BufferedSink write(Source source, long byteCount) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeUtf8(String string) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeUtf8(String string, int beginIndex, int endIndex) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeUtf8CodePoint(int codePoint) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeString(String string, Charset charset) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeString(String string, int beginIndex, int endIndex, Charset charset)
      throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeByte(int b) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeShort(int s) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeShortLe(int s) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeInt(int i) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeIntLe(int i) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeLong(long v) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeLongLe(long v) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeDecimalLong(long v) throws IOException {
    return null;
  }

  @Override
  public BufferedSink writeHexadecimalUnsignedLong(long v) throws IOException {
    return null;
  }

  @Override
  public void flush() throws IOException {

  }

  @Override
  public BufferedSink emit() throws IOException {
    return null;
  }

  @Override
  public BufferedSink emitCompleteSegments() throws IOException {
    return null;
  }

  @Override
  public OutputStream outputStream() {
    return null;
  }

  @Override
  public int write(ByteBuffer src) throws IOException {
    return 0;
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public void write(Buffer source, long byteCount) throws IOException {

  }

  @Override
  public Timeout timeout() {
    return null;
  }

  @Override
  public void close() throws IOException {

  }
}
