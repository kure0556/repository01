package zircuf.util.io.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Encoding {

	UTF8(StandardCharsets.UTF_8),
	SJIS(Charset.forName("MS932")), // Windows互換
	;

	@Getter
	private final Charset charset;

    public byte[] toBytes(String text) {
        Objects.requireNonNull(text, "text is null");
        return text.getBytes(charset);
    }
}
