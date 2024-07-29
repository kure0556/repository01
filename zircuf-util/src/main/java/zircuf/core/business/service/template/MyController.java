package zircuf.core.business.service.template;

import zircuf.core.business.service.template.base.BaseController;
import zircuf.core.business.service.template.dto.MyRequest;

public class MyController extends BaseController {

	MyService myService;

	public void init(MyRequest request) {
		call(myService::proc, request);
	}

}
