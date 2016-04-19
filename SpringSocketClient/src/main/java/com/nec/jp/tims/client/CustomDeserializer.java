package com.nec.jp.tims.client;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.serializer.Deserializer;
import org.springframework.integration.ip.tcp.serializer.SoftEndOfStreamException;

public class CustomDeserializer implements Deserializer<byte[]> {

	@Override
	public byte[] deserialize(InputStream inputStream) throws IOException {

		byte[] buffer = new byte[100000];
		int n = this.fillToCrLf(inputStream, buffer);
		return this.copyToSizedArray(buffer, n);

	}

	private byte[] copyToSizedArray(byte[] buffer, int size) {
		if (size == buffer.length) {
			return buffer;
		}
		byte[] assembledData = new byte[size];
		System.arraycopy(buffer, 0, assembledData, 0, size);
		return assembledData;
	}

	public int fillToCrLf(InputStream inputStream, byte[] buffer)
			throws IOException {
		int n = 0;
		int bite;

		try {
			while (true) {
				bite = inputStream.read();

				if (n > 3 && bite == '\n' && buffer[n - 1] == '.'
						&& buffer[n - 2] == '\r' && buffer[n - 3] == '\n') {
					break;
				}
				buffer[n++] = (byte) bite;
			}
			return n - 3; // trim \n\r.\n
		} catch (SoftEndOfStreamException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		}
	}

}
