package zircuf.util.text.operator;

import zircuf.util.text.operator.annotation.Operator;

public class LogFix {

	@Operator(value1="{stt}Logger.info({message}){end}",
			  value2="${stt}writeLog(${message}, \"INFO\")${end}")
	public void make() {
		return ;
	}
}
