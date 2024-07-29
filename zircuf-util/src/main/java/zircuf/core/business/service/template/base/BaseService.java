package zircuf.core.business.service.template.base;

public abstract class BaseService<RQ extends RequestDto, RS extends ResponseDto> {

	abstract public RS proc(RQ request);

}
