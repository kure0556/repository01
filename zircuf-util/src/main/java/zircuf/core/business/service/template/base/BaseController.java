package zircuf.core.business.service.template.base;

import java.util.function.Function;

import zircuf.core.business.error.BizException;

public abstract class BaseController {

	protected <RQ extends RequestDto> ResponseDto call(Function<RQ, ResponseDto> func, RQ request) {
		// TODO 自動生成されたメソッド・スタブ
		try {
			return func.apply(request);
		} catch (BizException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return new ResponseDto();

	}

}
