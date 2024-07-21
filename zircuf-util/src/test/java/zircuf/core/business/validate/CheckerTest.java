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
			CheckerTestDto checkerTestDto2 = new CheckerTestDto(null, null, " yy ", false, null, null);

			CheckerTestDto checkerTestDto31 = new CheckerTestDto(null, null, " zz11 ", false, null, null);
			CheckerTestDto checkerTestDto32 = new CheckerTestDto(null, null, " zz12", false, null, null);
			CheckerTestDto checkerTestDto33 = new CheckerTestDto(null, null, "zz13 ", false, null, null);

			CheckerTestDto checkerTestDto3 = new CheckerTestDto(" ", " zz1 ", " zz2 ", false, checkerTestDto2,
					List.of(checkerTestDto31, checkerTestDto32, checkerTestDto33));

			CheckerTestDto checkerTestDto1 = new CheckerTestDto("   aaa  ", null, "    cccc", false,
					checkerTestDto2, Collections.singletonList(checkerTestDto3));

			System.out.println(Validator.checkAndEdit(checkerTestDto1));

			CheckerTestDto checkerTestDto5 = new CheckerTestDto("   555", " 5 ", "    5555", false, null, null);
			CheckerTestDto checkerTestDto6 = new CheckerTestDto("   666", " 6 ", "  6666  ", false, null, null);

			CheckerTestDto checkerTestDto4 = new CheckerTestDto("   444", " 4 ", "4444    ", false, checkerTestDto5,
					Collections.singletonList(checkerTestDto6));

			System.out.println(Validator.checkAndEdit(checkerTestDto4));
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
		private CheckerTestDto myDto;

		@Edit(EType.NON_NULL_LIST)
		@Deep
		private List<CheckerTestDto> myDtos;
	}

}
