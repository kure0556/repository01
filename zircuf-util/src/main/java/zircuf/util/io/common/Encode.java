package zircuf.util.io.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Encode {

	UTF8(StandardCharsets.UTF_8),
	SJIS("MS932"),
	;

	@Getter
	private final Charset charset;

	private Encode(String charset) {
		this.charset = Charset.forName(charset);
	}

	public static byte[] asByte(String text) {
		return text.getBytes();
	}

	public static byte[] asSJisByte(String text) {
		return text.getBytes(SJIS.getCharset());
	}
}
