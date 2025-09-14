package zircuf.core.business.validate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import zircuf.core.business.validation.Validator;
import zircuf.core.business.validation.annotation.Check;
import zircuf.core.business.validation.annotation.Check.CType;
import zircuf.core.business.validation.annotation.CheckLength;
import zircuf.core.business.validation.annotation.CheckLogic;
import zircuf.core.business.validation.annotation.Deep;
import zircuf.core.business.validation.annotation.Edit;
import zircuf.core.business.validation.annotation.Edit.EType;

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

	@Test
	void test2() {
		CheckerErrorTestDto errorTestDto = new CheckerErrorTestDto(false, "");

		assertThrows(RuntimeException.class, () -> {
			try {
				Validator.checkAndEdit(errorTestDto);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		});

	}

	@Test
	void test3() {
		CheckerOR2 testDto = new CheckerOR2("", "");

		try {
			Validator.checkAndEdit(testDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Data
	@AllArgsConstructor
	static final class CheckerTestDto {
		@Check({ CType.NOT_BLANK, CType.NON_NULL })
		public String myField0;

		@Check(CType.NOT_BLANK)
		public String myField1;

		@Check(CType.NOT_BLANK)
		@CheckLogic(MyCheckLogic.class)
		@Edit(EType.TRIM)
		private String myField2;

		@CheckLength(min=1, max=2)
		private boolean myField3;

		@Deep
		private CheckerTestDto myDto;

		@Edit(EType.NON_NULL_LIST)
		@Deep
		private List<CheckerTestDto> myDtos;
	}

	@Data
	@AllArgsConstructor
	static final class CheckerErrorTestDto {
		@Check(CType.NOT_BLANK)
		private boolean errorItem1;

		@Check(CType.NOT_EMPTY_COLLECTION)
		private String errorItem2;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class CheckerOR1 {
		@Check(CType.NOT_BLANK)
		protected String myField1;
	}

	@Data
	@EqualsAndHashCode(callSuper=false)
	@AllArgsConstructor
	static class CheckerOR2 extends CheckerOR1 {
		public CheckerOR2(String myField1, String myField2) {
			super(myField1);
			this.myField2 = myField2;
		}

		@Check(CType.NOT_BLANK)
		private String myField2;
	}

}
