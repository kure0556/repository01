package zircuf.core.business.service.template.base;

import lombok.Data;

@Data
public class ResponseDto {

	private ResType resType = ResType.NOMAL;

	public enum ResType {
		NOMAL,
		BIZ_ERROR,
		APP_EXCEPTION
		;
	}

	public void nomal() {
		setResType(ResType.NOMAL);
	}
}
