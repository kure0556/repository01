package zircuf.core.business.validate;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import zircuf.core.business.validate.annotation.Check;
import zircuf.core.business.validate.annotation.Check.CType;
import zircuf.core.business.validate.annotation.CheckLogic;
import zircuf.core.business.validate.annotation.Deep;
import zircuf.core.business.validate.annotation.Edit;
import zircuf.core.business.validate.annotation.Edit.EType;

class CheckerTest {

	@Test
	void test() {
		try {
			CheckerTestDto checkerTestDto31 = new CheckerTestDto(null, null, " zz11 ", false, null, null);
			CheckerTestDto checkerTestDto32 = new CheckerTestDto(null, null, " zz12", false, null, null);
			CheckerTestDto checkerTestDto33 = new CheckerTestDto(null, null, "zz13 ", false, null, null);

			CheckerTestDto checkerTestDto2 = new CheckerTestDto(null, null, " yy ", false, null, null);
			CheckerTestDto checkerTestDto3 = new CheckerTestDto(null, " zz1 ", " zz2 ", false, checkerTestDto2,
					List.of(checkerTestDto31, checkerTestDto32, checkerTestDto33));
			CheckerTestDto checkerTestDto1 = new CheckerTestDto("   aaa  ", null, "    cccc", false,
					checkerTestDto2, Collections.singletonList(checkerTestDto3));

			CheckerTestDto checkerTestDto4 = new CheckerTestDto("   aaa", " b ", "    cccc", false, null, null);

			boolean check1 = Checker.of(CheckerTestDto.class).check(checkerTestDto1);
			System.out.println("check1=" + check1);
			boolean check2 = Checker.of(CheckerTestDto.class).check(checkerTestDto4);
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
		public String myField0;

		@Check(CType.REQUIERD)
		public String myField1;

		@Check(CType.REQUIERD)
		@CheckLogic(MyCheckLogic.class)
		@Edit(EType.TRIM)
		private String myField2;

		@Check(CType.REQUIERD)
		private boolean myField3;

		@Deep
		private CheckerTestDto myDto1;

		@Edit(EType.NON_NULL_LIST)
		@Deep
		private List<CheckerTestDto> myDtos;
	}

}
