package com.nec.jp.tims.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class OutputChannelHandler {

	public String handle(String name) throws IOException {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

		int key = Integer.parseInt(name);

		String files = FileMapping.getMapper().get(key);

		if (files == null) {
			files = "invalid option";

		} else {

			InputStream inputStream = this.getClass().getClassLoader()
					.getResourceAsStream("data/" + files);

			byte buffer[] = new byte[1024];

			int read = -1;
			while ((read = inputStream.read(buffer)) != -1) {
				arrayOutputStream.write(buffer, 0, read);
				resetBuffer(buffer);
			}

		}

		arrayOutputStream.write("\n\r.\n".getBytes());
		arrayOutputStream.flush();

		return new String(arrayOutputStream.toByteArray());
	}

	private void resetBuffer(byte[] buff) {

		for (int i = 0; i < buff.length; i++) {
			buff[i] = 0;
		}

	}

}
