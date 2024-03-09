package zircuf.util.io.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public enum Encode {

	UTF8(StandardCharsets.UTF_8),
	SJIS("MS932"),
	;

	private Charset charset;

	private Encode(String charset) {
		this.charset = Charset.forName(charset);
	}

	private Encode(Charset charset) {
		this.charset = charset;
	}

	public Charset getCharset() {
		return charset;
	}

	public static byte[] asByte(String text) {
		return text.getBytes();
	}

	public static byte[] asSJisByte(String text) {
		return text.getBytes(SJIS.getCharset());
	}
}
