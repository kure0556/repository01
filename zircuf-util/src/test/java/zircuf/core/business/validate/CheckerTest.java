package zircuf.core.business.validate;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import zircuf.core.business.validate.annotation.Check;
import zircuf.core.business.validate.annotation.Check.CType;
import zircuf.core.business.validate.annotation.CheckLogic;
import zircuf.core.business.validate.annotation.Edit;
import zircuf.core.business.validate.annotation.Edit.EType;

class CheckerTest {

	@Test
	void test() {
		try {
			CheckerTestDto checkerTestDto1 = new CheckerTestDto("   aaa  ", "   bbbb", "    cccc", false);
			CheckerTestDto checkerTestDto2 = new CheckerTestDto(null, null, " xx ", false);

			boolean check1 = Checker.of(CheckerTestDto.class).check(checkerTestDto1);
			System.out.println("check1=" + check1);
			boolean check2 = Checker.of(CheckerTestDto.class).check(checkerTestDto2);
			System.out.println("check2=" + check2);

			Editor.of(CheckerTestDto.class).edit(checkerTestDto1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Data
	@AllArgsConstructor
	static final class CheckerTestDto {
		@Check({ CType.REQUIERD, CType.NON_BLANK })
		@Edit(EType.TRIM)
		public String myField0;

		@Check(CType.REQUIERD)
		public String myField1;

		@Check(CType.REQUIERD)
		@CheckLogic(MyCheckLogic.class)
		@Edit(EType.TRIM)
		private String myField2;

		@Check(CType.REQUIERD)
		private boolean myField3;
	}

}
